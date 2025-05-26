package afs.interpreter;

import afs.interpreter.expressions.IntVal;
import afs.interpreter.expressions.ListVal;
import afs.interpreter.expressions.Val;
import afs.interpreter.interfaces.*;
import afs.nodes.expr.ExprIdentifierNode;
import afs.nodes.expr.ExprNode;
import afs.nodes.stmt.*;
import org.javatuples.Triplet;

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

                // Check for events
                EventHandler.check(envV, envF, envE, location, varName, store, imgStore);
                // Evaluate the expression
                Val value = ExprInterpreter.evalExpr(envV, envF, envE, location, exprNode, store, imgStore).getValue0();

                // Update store
                store.bind(envV.lookup(varName), value);

                // Return
                yield new Triplet<>(null, store, imgStore);
            }
            case StmtCompositionNode stmtCompositionNode -> {
                var s1 = stmtCompositionNode.getLeftStatement();

                // Evaluate the first statement
                Val value = evalStmt(envV, envF, envE, location, s1, store, imgStore).getValue0();

                // Comp-2: s1 is a non-epsilon value
                if (value != null) {
                    yield new Triplet<>(value, store, imgStore);
                }

                var s2 = stmtCompositionNode.getRightStatement();

                // Comp-1: Evaluate s2 as s1 evaluated to epsilon
                yield evalStmt(envV, envF, envE, location, s2, store, imgStore);
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
                store.bind(location, value);

                // Evaluate the statement and return
                yield evalStmt(newEnvV, envF, envE, ++location, nextStmt, store, imgStore);
            }
            case StmtFunctionCallNode stmtFunctionCallNode -> {
                String funcName = stmtFunctionCallNode.getIdentifier();
                List<ExprNode> args = stmtFunctionCallNode.getArguments();
                var funcData = envF.lookup(funcName);
                
                // Get function environment
                VarEnvironment funcEnvV = funcData.getValue2();

                // Base case
                if (args.isEmpty()) {
                    // Check for events
                    EventHandler.check(envV, envF, envE, location, funcName, store, imgStore);
                    // Call function by evaluating the statement in the function
                    StmtNode funcBody = funcData.getValue0();
                    yield StmtInterpreter.evalStmt(funcEnvV, envF, envE, location, funcBody, store, imgStore);
                } else {
                    int line = stmtFunctionCallNode.getLineNumber();
                    int col = stmtFunctionCallNode.getColumnNumber();

                    // Get parameters
                    List<String> paramNames = funcData.getValue1();

                    // Get expression e_n and evaluate it
                    ExprNode exprE_n = args.getLast();
                    Val exprVal = ExprInterpreter.evalExpr(envV, envF, envE, location, exprE_n, store, imgStore).getValue0();

                    List<ExprNode> newArgs = new ArrayList<>(args);
                    // Create a new function call with one less argument
                    StmtNode functionCallNode = new StmtFunctionCallNode(funcName, newArgs.subList(0, newArgs.size() - 1), line, col);

                    // Index of the last argument
                    int n = args.size() - 1;

                    // Check if e_n evaluates to a list
                    if (exprVal instanceof ListVal) {
                        // If e_n is not an identifier, throw an error as we cannot pass an array literal, for example
                        if (!(exprE_n instanceof ExprIdentifierNode ident)) {
                            throw new RuntimeException("Arrays are call-by-reference - cannot pass an array literal or an index of an array");
                        }
                        // Declare a new variable in the environment, using the name of the parameter, and make it point to the location of the identifier
                        funcEnvV.declare(paramNames.get(n), envV.lookup(ident.getIdentifier()));

                        // Evaluate the new function call with one less argument
                        yield evalStmt(envV, envF, envE, location, functionCallNode, store, imgStore);
                    } else { // If e_n is not a list
                        // Declare a new parameter, assign it the location l
                        funcEnvV.declare(paramNames.get(n), location);

                        // Store the value of expression e_n at the location
                        store.bind(location, exprVal);

                        // Evaluate the new function call with one less argument and with new location
                        yield evalStmt(envV, envF, envE, ++location, functionCallNode, store, imgStore);
                    }
                }
            }
            case StmtIfNode stmtIfNode -> {
                var exprNode = stmtIfNode.getExpression();
                var thenStmt = stmtIfNode.getLeftStatement();
                var elseStmt = stmtIfNode.getRightStatement();

                // Evaluate the expression
                Val exprVal = ExprInterpreter.evalExpr(envV, envF, envE, location, exprNode, store, imgStore).getValue0();

                // Check if the expression is true or false
                if (exprVal.asBool()) {
                    // Evaluate the then statement
                    yield evalStmt(envV.newScope(), envF, envE, location, thenStmt, store, imgStore);
                } else {
                    // Evaluate the else statement
                    yield evalStmt(envV.newScope(), envF, envE, location, elseStmt, store, imgStore);
                }
            }
            case StmtListAssignmentNode stmtListAssignmentNode -> {
                String varName = stmtListAssignmentNode.getIdentifier();
                // Evaluate x
                Val varVal = store.lookup(envV.lookup(varName));

                List<ExprNode> indexExprs = stmtListAssignmentNode.getExpressions();
                var valueExpr = stmtListAssignmentNode.getExpression();

                // Evaluate index e_1
                Val e1 = ExprInterpreter.evalExpr(envV, envF, envE, location, indexExprs.getFirst(), store, imgStore).getValue0();

                if (indexExprs.size() == 1) {
                    // Check for events
                    EventHandler.check(envV, envF, envE, location, varName, store, imgStore);
                    // Evaluate left hand side expression
                    Val exprVal = ExprInterpreter.evalExpr(envV, envF, envE, location, valueExpr, store, imgStore).getValue0();

                    // Set index e1 to the left hand side
                    varVal.asList().set(e1.asInt(), exprVal);

                    // Create a new listVal and store it at the location
                    ListVal listVal = new ListVal(varVal.asList());
                    store.bind(envV.lookup(varName), listVal);
                    yield new Triplet<>(null, store, imgStore);
                } else {
                    // Get value at index e_1 in x
                    Val val = varVal.asList().get(e1.asInt());

                    // Construct new stmt list assignment node
                    int line = stmtListAssignmentNode.getLineNumber();
                    int col = stmtListAssignmentNode.getColumnNumber();
                    StmtNode node = new StmtListAssignmentNode(varName, indexExprs.subList(1, indexExprs.size()), valueExpr, line, col);

                    // Set x to point to val
                    store.bind(envV.lookup(varName), val);

                    // Evaluate the new stmt list assignment node
                    evalStmt(envV, envF, envE, location, node, store, imgStore);

                    // Create a new ListVal from x, set index e_1 to point to value of x
                    ListVal listVal = new ListVal(varVal.asList());
                    listVal.asList().set(e1.asInt(), store.lookup(envV.lookup(varName)));
                    // Set x to listVal
                    store.bind(envV.lookup(varName), listVal);
                    yield new Triplet<>(null, store, imgStore);
                }
            }
            case StmtReturnNode stmtReturnNode -> {
                var exprNode = stmtReturnNode.getExpression();
                System.out.println("Evaluating return expression in env: " + envV);
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
                Val exprVal = ExprInterpreter.evalExpr(envV, envF, envE, location, exprNode, store, imgStore).getValue0();

                // Check if the expression is true or false
                if (exprVal.asBool()) {
                    // Evaluate the body statement using a new scope
                    evalStmt(envV.newScope(), envF, envE, location, bodyStmt, store, imgStore);

                    // Re-evaluate the while statement with the updated store
                    yield evalStmt(envV, envF, envE, location, stmtWhileNode, store, imgStore);
                } else {
                    yield new Triplet<>(null, store, imgStore);
                }
            }
        };
    }
}
