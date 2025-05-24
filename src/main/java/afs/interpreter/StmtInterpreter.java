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



    public Triplet<Object, Store, ImgStore> evalStmt(VarEnvironment envV,
                                                     FunEnvironment envF,
                                                     EventEnvironment envE,
                                                     int location,
                                                     StmtNode stmt,
                                                     Store store,
                                                     ImgStore imgStore) {
        return switch (stmt) {
            case StmtAssignmentNode stmtAssignmentNode -> {
                String varName = stmtAssignmentNode.getIdentifier();
                var exprNode = stmtAssignmentNode.getExpression();

                // Evaluate the expression
                var exprResult = exprInterpreter.evalExpr(envV, envF, envE, location, exprNode, store, imgStore);
                Val value = (Val) exprResult.getValue0();

                // Get location of variable
                int varLocation = envV.lookup(varName);

                // Check if the variable stores a RefVal, and if so, update the referenced location
                Val currentVal = (Val) store.lookup(varLocation);
                if (currentVal instanceof RefVal refVal) {
                    // updating the variable's own location
                    int refLocation = refVal.getLocation();
                    store.store(refLocation, value);
                } else {
                    // Otherwise, update the variable's own location
                    store.store(varLocation, value);
                }

                // Return
                yield new Triplet<>(null, store, imgStore);
            }
            case StmtCompositionNode stmtCompositionNode -> {
                var s1 = stmtCompositionNode.getLeftStatement();
                var s2 = stmtCompositionNode.getRightStatement();

                // Evaluate the first statement
                var r1 = evalStmt(envV, envF, envE, location, s1, store, imgStore);
                Object value = r1.getValue0();
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
                // allocate a fresh location for the new variable you're declaring
                int nextLocation = store.nextLocation();

                // Evaluate the expression
                var exprResult = exprInterpreter.evalExpr(envV, envF, envE, location, exprNode, store, imgStore);
                Val rawValue = (Val) exprResult.getValue0();

                // Dereference RefVal if needed
                Val value;
                if (rawValue instanceof RefVal refVal) {
                    int refLoc = refVal.getLocation();
                    Val derefValue = (Val)store.lookup(refLoc);
                    value = derefValue;  // Use the original list directly (no copy)
                } else {
                    value = rawValue;
                }

                // Update the environment
                envV.declare(varName, nextLocation);

                // Update the store
                store.store(nextLocation, value);

                // get the next free location *after* this one
                int afterLocation = store.nextLocation();


                // Evaluate the statement and return
                yield evalStmt(envV, envF, envE, afterLocation, nextStmt, store, imgStore);
            }
            case StmtFunctionCallNode stmtFunctionCallNode -> {
                String funcName = stmtFunctionCallNode.getIdentifier();
                List<ExprNode> args = stmtFunctionCallNode.getArguments();

                // Look up function definition
                var funcData = envF.lookup(funcName);
                if (funcData == null) {
                    throw new RuntimeException("Undefined function: " + funcName);
                }

                // Extract function components
                StmtNode funcBody = funcData.getValue0();
                List<String> paramNames = funcData.getValue1();
                List<TypeNode> paramTypes = funcData.getValue2();
                VarEnvironment funcDeclEnv = funcData.getValue3();

                // Evaluate arguments
                List<Object> evaluatedArgs = new ArrayList<>();

                for (int i = 0; i < args.size(); i++) {
                    ExprNode arg = args.get(i);
                    TypeNode paramType = paramTypes.get(i);
                    boolean isList = paramType instanceof TypeListNode;

                    var argResult = exprInterpreter.evalExpr(envV, envF, envE, location, arg, store, imgStore);
                    Object argVal = argResult.getValue0();

                    if (isList) {
                        if (arg instanceof ExprIdentifierNode) {
                            String varName = ((ExprIdentifierNode) arg).getIdentifier();
                            int argLoc = envV.lookup(varName);
                            evaluatedArgs.add(new RefVal(argLoc));  // Pass-by-reference
                        } else {
                            throw new RuntimeException("List parameters must be passed as variable references");
                        }
                    } else {
                        if (!(argVal instanceof Val)) {
                            throw new RuntimeException("Expected Val for non-list parameter: " + paramNames.get(i));
                        }
                        evaluatedArgs.add(argVal);  // Pass-by-value
                    }
                }

                // Argument count check
                if (evaluatedArgs.size() != paramNames.size()) {
                    throw new RuntimeException("Argument count mismatch for function " + funcName +
                            ": expected " + paramNames.size() + ", got " + evaluatedArgs.size());
                }

                // Create new variable scope
                VarEnvironment newEnvV = funcDeclEnv.newScope();

                    // Bind parameters
                for (int i = 0; i < paramNames.size(); i++) {
                    String param = paramNames.get(i);
                    TypeNode paramType = paramTypes.get(i);
                    Object arg = evaluatedArgs.get(i);
                    boolean isListParam = paramType instanceof TypeListNode;

                    if (isListParam) {
                        if (!(arg instanceof RefVal)) {
                            throw new RuntimeException("Expected RefVal for list parameter: " + param);
                        }
                        int loc = ((RefVal)arg).getLocation();
                        newEnvV.declare(param, loc);  //  Reuse original location
                    } else {
                        int newLoc = store.nextLocation();
                        newEnvV.declare(param, newLoc);
                        store.store(newLoc, ((Val)arg).copy());  //  Store a fresh copy
                    }
                }

                // Evaluate function body
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

                // Evaluate the variable
                int varLocation = envV.lookup(varName);
                Val storedVal = (Val) store.lookup(varLocation);

                // Dereference if RefVal
                ListVal originalList;
                if (storedVal instanceof RefVal refVal) {
                    int refLocation = refVal.getLocation();
                    Val derefVal = (Val) store.lookup(refLocation);
                    if (!(derefVal instanceof ListVal listVal)) {
                        throw new RuntimeException("Variable " + varName + " (reference) does not point to a list");
                    }
                    originalList = listVal;
                    varLocation = refLocation; // Update varLocation to referenced location
                } else if (storedVal instanceof ListVal listVal) {
                    originalList = listVal;
                } else {
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
                    store = exprResult.getValue1();
                    imgStore = exprResult.getValue2();
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
                var store2 = exprResult.getValue1();
                var imgStore2 = exprResult.getValue2();

                // Return the value
                yield new Triplet<>(value, store2, imgStore2);
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