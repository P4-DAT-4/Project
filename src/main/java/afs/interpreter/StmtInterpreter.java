package afs.interpreter;

import afs.interpreter.expressions.Val;
import afs.interpreter.interfaces.*;
import afs.nodes.expr.ExprNode;
import afs.nodes.stmt.*;
import org.javatuples.Triplet;

import afs.interpreter.interfaces.*;

import java.util.ArrayList;
import java.util.List;

public class StmtInterpreter {
    public static Triplet<Val, Store, ImgStore> evalStmt(VarEnvironment envV,
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
                Val value = ExprInterpreter.evalExpr(envV, envF, envE, location, exprNode, store, imgStore).getValue0();

                // Update store
                store.store(envV.lookup(varName), value);

                // Return
                yield new Triplet<>(null, store, imgStore);
            }
            case StmtCompositionNode stmtCompositionNode -> {
                var s1 = stmtCompositionNode.getLeftStatement();

                // Evaluate the first statement
                Val value = evalStmt(envV.newScope(), envF, envE, location, s1, store, imgStore).getValue0();

                // Comp-2: s1 is a non-epsilon value
                if (value != null) {
                    yield new Triplet<>(value, store, imgStore);
                }

                var s2 = stmtCompositionNode.getRightStatement();

                // Comp-1: Evaluate s2 as s1 evaluated to epsilon
                yield evalStmt(envV.newScope(), envF, envE, location, s2, store, imgStore);
            }
            case StmtDeclarationNode stmtDeclarationNode -> {
                String varName = stmtDeclarationNode.getIdentifier();
                var exprNode = stmtDeclarationNode.getExpression();
                var nextStmt = stmtDeclarationNode.getStatement();

                // Evaluate the expression
                Val value = ExprInterpreter.evalExpr(envV, envF, envE, location, exprNode, store, imgStore).getValue0();

                // Create a copy of the current var env and declare a new variable in that
                VarEnvironment newEnvV = envV.newScope();
                newEnvV.declare(varName, location);

                // Update the store
                store.store(location, value);

                // Evaluate the statement and return
                yield evalStmt(envV, envF, envE, ++location, nextStmt, store, imgStore);
            }
            case StmtFunctionCallNode stmtFunctionCallNode -> {
                String funcName = stmtFunctionCallNode.getIdentifier();
                List<ExprNode> args = stmtFunctionCallNode.getArguments();
                var funcData = envF.lookup(funcName);

                if (args.size() == 0) {
                    StmtNode funcBody = funcData.getValue0();
                    yield evalStmt(envV.newScope(), envF, envE, location, funcBody, store, imgStore);
                } else {
                    int line = stmtFunctionCallNode.getLineNumber();
                    int col = stmt.getColumnNumber();

                    List<String> paramNames = funcData.getValue1();
                    Val exprVal = ExprInterpreter.evalExpr(envV, envF, envE, location, args.getLast(), store, imgStore).getValue0();
                    VarEnvironment newEnvV = envV.newScope();
                    envV.declare(paramNames.get(args.size() - 1), location);
                    store.store(location, exprVal);

                    StmtFunctionCallNode functionCallNode = new StmtFunctionCallNode(funcName, args.subList(1, args.size()), line, col);
                }

                //Look up function definition

                // Extract function components
                VarEnvironment funcDeclEnv = funcData.getValue2();

                // Evaluate each argument
                List<Val> evaluatedArgs = new ArrayList<>();

                for (ExprNode arg : args) {
                    var argResult = ExprInterpreter.evalExpr(funcDeclEnv, envF, envE, location, arg, store, imgStore);
                    evaluatedArgs.add(argResult.getValue0());
                }

                 //Create a new scope from function declaration environment
                VarEnvironment newEnvV = funcDeclEnv.newScope();

                // Bind parameter to new location
                for (int i = 0; i < paramNames.size(); i++) {
                    String param = paramNames.get(i);
                    Val argVal = evaluatedArgs.get(i);
                    newEnvV.declare(param, ++location); // declare variable in environment
                    store.store(location, argVal);
                }

                 //Interpret the function body
            }
            case StmtIfNode stmtIfNode -> {
                var exprNode = stmtIfNode.getExpression();
                var thenStmt = stmtIfNode.getLeftStatement();
                var elseStmt = stmtIfNode.getRightStatement();

                // Evaluate the expression
                var exprResult = ExprInterpreter.evalExpr(envV, envF, envE, location, exprNode, store, imgStore);
                Object value = exprResult.getValue0();
                var store2 = exprResult.getValue1();
                var imgStore2 = exprResult.getValue2();

                // Check if the expression is true or false
                if ((boolean) value) {
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
                Object listValue = store.lookup(varLocation);

                if (!(listValue instanceof List<?> originalList)) {
                    throw new RuntimeException("Variable " + varName + " is not a list");
                }

                // Evaluate all index expressions
                List<Integer> indices = new ArrayList<>();
                var currentStore = store;
                var currentImgStore = imgStore;

                for (ExprNode indexExpr : indexExprs) {
                    var exprResult = ExprInterpreter.evalExpr(envV, envF, envE, location, indexExpr, currentStore, currentImgStore);
                    Object indexValue = exprResult.getValue0();
                    if (!(indexValue instanceof Integer i)) {
                        throw new RuntimeException("Index expression is not an integer");
                    }
                    indices.add(i);
                    currentStore = exprResult.getValue1();
                    currentImgStore = exprResult.getValue2();
                }

                // Evaluate the value expression
                var valueResult = ExprInterpreter.evalExpr(envV, envF, envE, location, valueExpr, currentStore, currentImgStore);
                Object value2 = valueResult.getValue0();
                var store2 = valueResult.getValue1();
                var imgStore2 = valueResult.getValue2();

                // Perform nested update
                List<Object> updatedList = updateNestedList(originalList, indices, value2);

                // Store updated list back
                store2.store(varLocation, updatedList);

                yield new Triplet<>(null, store2, imgStore2);
            }
            case StmtReturnNode stmtReturnNode -> {
                var exprNode = stmtReturnNode.getExpression();

                // Evaluate the expression
                var value = ExprInterpreter.evalExpr(envV, envF, envE, location, exprNode, store, imgStore).getValue0();

                // Return the value
                yield new Triplet<>(value, store, imgStore);
            }
            case StmtSkipNode stmtSkipNode -> new Triplet<>(null, store, imgStore);
            case StmtWhileNode stmtWhileNode -> {
                var exprNode = stmtWhileNode.getExpression();
                var bodyStmt = stmtWhileNode.getStatement();

                // Evaluate the expression
                var exprResult = ExprInterpreter.evalExpr(envV, envF, envE, location, exprNode, store, imgStore);
                Object value = exprResult.getValue0();
                var store2 = exprResult.getValue1();
                var imgStore2 = exprResult.getValue2();

                // Check if the expression is true or false
                if ((boolean) value) {
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
    private static List<Object> updateNestedList(List<?> list, List<Integer> indices, Object value) {
        if (indices.isEmpty()) {
            throw new IllegalArgumentException("Empty index list.");
        }

        int index = indices.getFirst();

        if (indices.size() == 1) {
            // Base case: just set the value
            List<Object> copy = new ArrayList<>(list);
            copy.set(index, value);
            return copy;
        } else {
            // Recursive case: descend
            Object sublist = list.get(index);
            if (!(sublist instanceof List<?> subListCasted)) {
                throw new RuntimeException("Expected nested list at index " + index);
            }

            List<Object> updatedSublist = updateNestedList(subListCasted, indices.subList(1, indices.size()), value);
            List<Object> copy = new ArrayList<>(list);
            copy.set(index, updatedSublist);
            return copy;
        }
    }
}