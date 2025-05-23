package afs.interpreter;

import afs.interpreter.expressions.BoolVal;
import afs.interpreter.expressions.IntVal;
import afs.interpreter.expressions.ListVal;
import afs.interpreter.expressions.Val;
import afs.interpreter.interfaces.*;
import afs.nodes.expr.ExprNode;
import afs.nodes.stmt.*;
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
                var store2 = exprResult.getValue1();
                var imgStore2 = exprResult.getValue2();

                // Get location of variable
                int varLocation = envV.lookup(varName);

                // Update store
                store2.store(varLocation, value);

                // Return
                yield new Triplet<>(null, store2, imgStore2);
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

                // Evaluate the expression
                var exprResult = exprInterpreter.evalExpr(envV, envF, envE, location, exprNode, store, imgStore);
                Object value = exprResult.getValue0();
                var store2 = exprResult.getValue1();
                var imgStore2 = exprResult.getValue2();

                // Update the environment
                envV.declare(varName, location);

                // Update the store
                store2.store(location, value);

                // Get next location
                int nextLocation = store2.nextLocation();

                // Evaluate the statement and return
                yield evalStmt(envV, envF, envE, nextLocation, nextStmt, store2, imgStore2);
            }
            case StmtFunctionCallNode stmtFunctionCallNode -> {
                String funcName = stmtFunctionCallNode.getIdentifier();
                List<ExprNode> args = stmtFunctionCallNode.getArguments();

                //Look up function definition
                var funcData = envF.lookup(funcName);
                if (funcData == null) {
                    throw new RuntimeException("Undefined function: " + funcName);
                }

                // Extract function components
                StmtNode funcBody = funcData.getValue0();
                List<String> paramNames = funcData.getValue1();
                VarEnvironment funcDeclEnv = funcData.getValue2();

                // Evaluate each argument
                List<Object> evaluatedArgs = new ArrayList<>();
                Store currentStore = store;
                ImgStore currentImgStore = imgStore;

                for (ExprNode arg : args) {
                    var argResult = exprInterpreter.evalExpr(funcDeclEnv, envF, envE, location, arg, currentStore, currentImgStore);
                    evaluatedArgs.add(argResult.getValue0());
                    currentStore = argResult.getValue1();
                    currentImgStore = argResult.getValue2();
                }

                 //Create a new scope from function declaration environment
                VarEnvironment newEnvV = funcDeclEnv.newScope();

                // Bind parameter to new location
                for (int i = 0; i < paramNames.size(); i++) {
                    String param = paramNames.get(i);
                    Object argVal = evaluatedArgs.get(i);

                    int newLoc = currentStore.nextLocation();
                    newEnvV.declare(param, newLoc); // declare variable in environment
                    currentStore.store(newLoc, argVal);
                }

                 //Interpret the function body
                yield evalStmt(newEnvV, envF, envE, location, funcBody, currentStore, currentImgStore);
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
                Val listValue = (ListVal) store.lookup(varLocation);

                if (!(listValue instanceof ListVal originalList)) {
                    throw new RuntimeException("Variable " + varName + " is not a list");
                }

                // Evaluate all index expressions
                List<IntVal> indices = new ArrayList<>();
                var currentStore = store;
                var currentImgStore = imgStore;

                for (ExprNode indexExpr : indexExprs) {
                    var exprResult = exprInterpreter.evalExpr(envV, envF, envE, location, indexExpr, currentStore, currentImgStore);
                    Object indexValue = exprResult.getValue0();
                    if (!(indexValue instanceof IntVal i)) {
                        throw new RuntimeException("Index expression is not an integer");
                    }
                    indices.add(i);
                    currentStore = exprResult.getValue1();
                    currentImgStore = exprResult.getValue2();
                }

                // Evaluate the value expression
                var valueResult = exprInterpreter.evalExpr(envV, envF, envE, location, valueExpr, currentStore, currentImgStore);
                Val value2 = (Val) valueResult.getValue0();
                var store2 = valueResult.getValue1();
                var imgStore2 = valueResult.getValue2();

                // Perform nested update
                ListVal updatedList = updateNestedList(originalList, indices, value2);

                // Store updated list back
                store2.store(varLocation, updatedList);

                yield new Triplet<>(null, store2, imgStore2);
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