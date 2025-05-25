package afs.interpreter;

import afs.interpreter.expressions.*;
import afs.interpreter.implementations.MapVarEnvironment;
import afs.interpreter.interfaces.*;
import afs.nodes.expr.ExprIdentifierNode;
import afs.nodes.expr.ExprNode;
import afs.nodes.stmt.*;
import afs.nodes.type.TypeListNode;
import afs.nodes.type.TypeNode;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;

public class StmtInterpreter {

    private final ExprInterpreter exprInterpreter;

    public StmtInterpreter(ExprInterpreter exprInterpreter) {
        this.exprInterpreter = exprInterpreter;
    }



    public Triplet<Val, Store, ImgStore> evalStmt(VarEnvironment envV,
                                                     FunEnvironment envF,
                                                     EventEnvironment envE,
                                                     int location,
                                                     StmtNode stmt,
                                                     Store store,
                                                     ImgStore imgStore) {
        return switch (stmt) {
            case StmtAssignmentNode stmtAssignmentNode -> {
                String varName = stmtAssignmentNode.getIdentifier();
                ExprNode exprNode = stmtAssignmentNode.getExpression();

                // Evaluate the right-hand side expression
                Val newVal = (Val) exprInterpreter.evalExpr(envV, envF, envE, location, exprNode, store, imgStore).getValue0();

                // Get the variable's location in the environment
                int varLocation = envV.lookup(varName);

                // Store a *copy* of the value to simulate pass-by-value
                store.store(varLocation, newVal.copy());

                yield new Triplet<>(null, store, imgStore);
            }
            case StmtCompositionNode stmtCompositionNode -> {
                var s1 = stmtCompositionNode.getLeftStatement();
                var s2 = stmtCompositionNode.getRightStatement();

                // Evaluate the first statement
                var r1 = evalStmt(envV, envF, envE, location, s1, store, imgStore);
                Val value = r1.getValue0();
                var store2 = r1.getValue1();
                var imgStore2 = r1.getValue2();

                // Comp-2: s1 is a non-epsilon value
                if (value != null) {
                    yield new Triplet<>(value, store2, imgStore2);
                }

                // Comp-1: Do s2 as s1 evaluated to epsilon
                yield evalStmt(envV, envF, envE, location, s2, store2, imgStore2);
            }
            case StmtDeclarationNode stmtDeclarationNode -> {
                String varName = stmtDeclarationNode.getIdentifier();
                var exprNode = stmtDeclarationNode.getExpression();
                var nextStmt = stmtDeclarationNode.getStatement();

                // Evaluate the expression
                var exprResult = exprInterpreter.evalExpr(envV, envF, envE, location, exprNode, store, imgStore);
                Val value = (Val) exprResult.getValue0();

                int locationToBind;
                if (value instanceof ListVal && exprNode instanceof ExprIdentifierNode idNode) {
                    // reuse the original location for list
                    locationToBind = envV.lookup(idNode.getIdentifier());
                } else {
                    // Allocate a fresh location for all other cases
                    locationToBind = store.nextLocation();
                    store.store(locationToBind, value.copy());  // Store a copy to simulate call-by-value
                }
                // Bind the variable
                envV.declare(varName, locationToBind);

                // Evaluate the next statement
                int nextLocation = store.nextLocation();
                yield evalStmt(envV, envF, envE, nextLocation, nextStmt, store, imgStore);
            }
            case StmtFunctionCallNode stmtFunctionCallNode -> {
                String funcName = stmtFunctionCallNode.getIdentifier();
                List<ExprNode> args = stmtFunctionCallNode.getArguments();
                System.out.println("[DEBUG] Function call: " + funcName);
                System.out.println("[DEBUG] Arguments count: " + args.size());

                // Look up function definition
                var funcData = envF.lookup(funcName);
                if (funcData == null) {
                    throw new RuntimeException("Undefined function: " + funcName);
                }

                // Extract function components
                StmtNode funcBody = funcData.getValue0();
                List<String> paramNames = funcData.getValue1();
                VarEnvironment funcDeclEnv = funcData.getValue2();

                System.out.println("[DEBUG] Function has " + paramNames.size() + " parameters: " + paramNames);
                System.out.println("[DEBUG] Function declaration environment: " + funcDeclEnv);

                // Argument count check
                if (args.size() != paramNames.size()) {
                    throw new RuntimeException("Argument count mismatch for function " + funcName +
                            ": expected " + paramNames.size() + ", got " + args.size());
                }

                // Create new variable scope
                VarEnvironment newEnvV = funcDeclEnv.newScope();
                System.out.println("[DEBUG] Created new variable environment for function call: " + newEnvV);

                // Bind arguments to parameters
                for (int i = 0; i < args.size(); i++) {
                    ExprNode argExpr = args.get(i);
                    String paramName = paramNames.get(i);

                    System.out.println("[DEBUG] Evaluating argument " + i + " for parameter '" + paramName + "': " + argExpr);

                    var result = exprInterpreter.evalExpr(envV, envF, envE, location, argExpr, store, imgStore);
                    Val val = (Val) result.getValue0();

                    // Determine pass-by-reference for arrays/lists
                    if (val instanceof ListVal) {
                        if (!(argExpr instanceof ExprIdentifierNode)) {
                            throw new RuntimeException("Cannot pass array literal or list access by reference. Use variable name.");
                        }
                        // Bind the parameter name to the same location as the original variable
                        String varName = ((ExprIdentifierNode) argExpr).getIdentifier();
                        int loc = envV.lookup(varName);
                        newEnvV.declare(paramName, loc);
                    } else {
                        // Pass-by-value: create a new location and copy value
                        int newLoc = store.nextLocation();
                        newEnvV.declare(paramName, newLoc);
                        store.store(newLoc, val.copy());
                    }
                }

                // Evaluate function body in new environment
                yield evalStmt(newEnvV, envF, envE, location, funcBody, store, imgStore);

            }
            case StmtIfNode stmtIfNode -> {
                var exprNode = stmtIfNode.getExpression();
                var thenStmt = stmtIfNode.getLeftStatement();
                var elseStmt = stmtIfNode.getRightStatement();

                // Evaluate the expression
                var exprResult = exprInterpreter.evalExpr(envV, envF, envE, location, exprNode, store, imgStore);
                BoolVal value = (BoolVal) exprResult.getValue0();
                var store2 = exprResult.getValue1();
                var imgStore2 = exprResult.getValue2();

                // Check if the expression is true or false
                if (value.getValue()) {
                    // Evaluate the then statement
                    yield evalStmt(envV, envF, envE, location, thenStmt, store2, imgStore2);
                } else {
                    // Evaluate the else statement
                    yield evalStmt(envV, envF, envE, location, elseStmt, store2, imgStore2);
                }
            }
            case StmtListAssignmentNode stmtListAssignmentNode -> {
                String varName = stmtListAssignmentNode.getIdentifier();
                List<ExprNode> indexExprs = stmtListAssignmentNode.getExpressions();
                var valueExpr = stmtListAssignmentNode.getExpression();

                // Lookup list variable directly
                int varLocation = envV.lookup(varName);
                Val storedVal = (Val) store.lookup(varLocation);

                if (!(storedVal instanceof ListVal originalList)) {
                    throw new RuntimeException("Variable " + varName + " is not a list");
                }

                // Evaluate all index expressions
                List<IntVal> indices = new ArrayList<>();
                for (ExprNode indexExpr : indexExprs) {
                    var exprResult = exprInterpreter.evalExpr(envV, envF, envE, location, indexExpr, store, imgStore);
                    Val indexValue = (Val) exprResult.getValue0();
                    if (!(indexValue instanceof IntVal i)) {
                        throw new RuntimeException("Index expression is not an integer");
                    }
                    indices.add(i);

                }

                // Evaluate the value expression
                var valueResult = exprInterpreter.evalExpr(envV, envF, envE, location, valueExpr, store, imgStore);
                Val value2 = (Val) valueResult.getValue0();

                // Perform nested update
                ListVal updatedList = updateNestedList(originalList, indices, value2);

                // Store updated list back
                store.store(varLocation, updatedList);

                yield new Triplet<>(null, store, imgStore);
            }
            case StmtReturnNode stmtReturnNode -> {
                var exprNode = stmtReturnNode.getExpression();

                // Evaluate the expression
                var exprResult = exprInterpreter.evalExpr(envV, envF, envE, location, exprNode, store, imgStore);
                Val value = (Val) exprResult.getValue0();


                // Return the value
                yield new Triplet<>(value, store, imgStore);
            }
            case StmtSkipNode stmtSkipNode -> new Triplet<>(null, store, imgStore);
            case StmtWhileNode stmtWhileNode -> {
                var exprNode = stmtWhileNode.getExpression();
                var bodyStmt = stmtWhileNode.getStatement();

                // Evaluate the expression
                var exprResult = exprInterpreter.evalExpr(envV, envF, envE, location, exprNode, store, imgStore);
                BoolVal value = (BoolVal) exprResult.getValue0();
                var store2 = exprResult.getValue1();
                var imgStore2 = exprResult.getValue2();

                // Check if the expression is true or false
                if ( value.getValue() )  {
                    // Evaluate the body statement
                    var result = evalStmt(envV, envF, envE, location, bodyStmt, store2, imgStore2);
                    var store3 = result.getValue1();
                    var imgStore3 = result.getValue2();

                    // Re-evaluate the while statement with the updated store
                    yield evalStmt(envV, envF, envE, location, stmtWhileNode, store3, imgStore3);
                } else {
                    yield new Triplet<>(null, store2, imgStore2);
                }
            }
        };
    }
    @SuppressWarnings("unchecked")
//    private List<Object> updateNestedList(List<?> list, List<Integer> indices, Object value) {
//        if (indices.isEmpty()) {
//            throw new IllegalArgumentException("Empty index list.");
//        }
//
//        int index = indices.getFirst();
//
//        if (indices.size() == 1) {
//            // Base case: just set the value
//            List<Object> copy = new ArrayList<>(list);
//            copy.set(index, value);
//            return copy;
//        } else {
//            // Recursive case: descend
//            Object sublist = list.get(index);
//            if (!(sublist instanceof List<?> subListCasted)) {
//                throw new RuntimeException("Expected nested list at index " + index);
//            }
//
//            List<Object> updatedSublist = updateNestedList(subListCasted, indices.subList(1, indices.size()), value);
//            List<Object> copy = new ArrayList<>(list);
//            copy.set(index, updatedSublist);
//            return copy;
//        }
//    }


    private ListVal updateNestedList(ListVal listVal, List<IntVal> indices, Val newValue) {
        if (indices.isEmpty()) {
            throw new IllegalArgumentException("Empty index list.");
        }

        int index = indices.get(0).getValue(); // or indices.get(0)

        List<Val> elements = listVal.getElements();
        List<Val> updatedElements = new ArrayList<>(elements);

        if (index < 0 || index >= updatedElements.size()) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds.");
        }

        if (indices.size() == 1) {
            // Base case: set the new value
            updatedElements.set(index, newValue);
        } else {
            Val subVal = updatedElements.get(index);
            if (!(subVal instanceof ListVal nestedList)) {
                throw new RuntimeException("Expected nested list at index " + index);
            }

            // Recursive case
            ListVal updatedSubList = updateNestedList(nestedList, indices.subList(1, indices.size()), newValue);
            updatedElements.set(index, updatedSubList);
        }

        return new ListVal(updatedElements);
    }



}