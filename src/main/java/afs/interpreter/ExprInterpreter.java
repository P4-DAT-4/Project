package afs.interpreter;

import afs.interpreter.expressions.*;
import afs.nodes.expr.*;
import afs.nodes.stmt.StmtNode;
import com.sun.jdi.DoubleValue;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;

public class ExprInterpreter {
    public static Triplet<Object, Store, ImgStore> evalExpr(VarEnvironment envV,
                                                            FunEnvironment envF,
                                                            EventEnvironment envE,
                                                            int location,
                                                            ExprNode expr,
                                                            Store store,
                                                            ImgStore imgStore) {
        return switch (expr) {
            case ExprBinopNode exprBinopNode -> {
                var e1 = exprBinopNode.getLeftExpression();
                var e2 = exprBinopNode.getRightExpression();

                var r1 = evalExpr(envV, envF, envE, location, e1, store, imgStore);
                var r2 = evalExpr(envV, envF, envE, location, e2, store, imgStore);

                var op = exprBinopNode.getOp();
                var val = evalBinopExpr(r1.getValue0(), op, r2.getValue0());
                yield new Triplet<>(val, r2.getValue1(), r2.getValue2());
            }
            case ExprBoolNode exprBoolNode -> {
                // Extract boolean value from the node
                boolean val = exprBoolNode.getValue();
                BoolVal result = new BoolVal(val);
                yield new Triplet<>(result, store, imgStore);
            }
            case ExprCurveNode exprCurveNode -> {
                List<ExprNode> exprs = exprCurveNode.getExpressions();
                if(exprs.size() % 4 != 2 || exprs.size() < 6) {
                    throw new RuntimeException("Invalid number of expressions in cruve. Found" + exprs.size());
                }

                List<BezierSegment> segments = new ArrayList<>();
                Store currentStore = store;
                ImgStore currentImgStore = imgStore;

                // Evaluate start point
                Point start = (Point) evalPoint(
                        exprs.get(0), exprs.get(1),
                        envV, envF, envE, location,
                        currentStore, currentImgStore ).getValue0();

                // Evaluate control point
                Point control = (Point) evalPoint(
                        exprs.get(2), exprs.get(3),
                        envV, envF, envE, location,
                        currentStore, currentImgStore ).getValue0();

                // Evaluate end point
                Point end = (Point) evalPoint(
                        exprs.get(4), exprs.get(5),
                        envV, envF, envE, location,
                        currentStore, currentImgStore ).getValue0();


                segments.add(new BezierSegment(start, control, end));

                // Handle extended segment
                Point prevEnd = end;

                for(int i = 6; i <exprs.size(); i += 4){
                    // Evaluate next control point
                    Point nextControl = (Point) evalPoint(
                            exprs.get(i), exprs.get(i + 1),
                            envV, envF, envE, location,
                            currentStore, currentImgStore ).getValue0();


                    // Evaluate next end point
                    Point nextEnd = (Point) evalPoint(
                            exprs.get(i + 2), exprs.get(i + 3),
                            envV, envF, envE, location,
                            currentStore, currentImgStore ).getValue0();


                   segments.add(new BezierSegment(prevEnd, nextControl, nextEnd));
                   prevEnd = nextEnd;
                }

                CurveVal result = new CurveVal(segments);
                yield new Triplet<>(result, currentStore, currentImgStore);


            }
            case ExprDoubleNode exprDoubleNode -> {
                // Extract double value from the node
                double val = exprDoubleNode.getValue();
                DoubleVal result = new DoubleVal(val);
                yield new Triplet<>(result, store, imgStore);
            }
            case ExprFunctionCallNode exprFunctionCallNode -> {
                String funcName = exprFunctionCallNode.getIdentifier();
                List<ExprNode> args = exprFunctionCallNode.getArguments();

                //Look up function definition
                Triplet<StmtNode, List<String>, VarEnvironment> funcData = envF.lookup(funcName);
                if (funcData == null) {
                    throw new RuntimeException("Undefined function: " + funcName);
                }
                // Extract function components
                StmtNode funcBody = funcData.getValue0();
                List<String> paramName = funcData.getValue1();
                VarEnvironment funcDeclEnv = funcData.getValue2();

                // Evaluate each argument
                List<Object> evaluatedArgs = new ArrayList<>();
                Store currentStore = store;
                ImgStore currentImgStore = imgStore;

                for (ExprNode argExpr : args) {
                    var argResult = evalExpr(funcDeclEnv, envF, envE, location, argExpr, currentStore, currentImgStore);
                    evaluatedArgs.add(argResult.getValue0());
                    currentStore = argResult.getValue1();
                    currentImgStore = argResult.getValue2();
                }

                // Create a new scope from function declaration enviroment
                VarEnvironment newEnvV = funcDeclEnv.newScope();

                // Bind parameterr to new location
                for (int i = 0; i < paramName.size(); i++) {
                    String param = paramName.get(i);
                    Object argVal = evaluatedArgs.get(i);

                    int newLoc = location + i + 1;
                    newEnvV.declare(param, newLoc); // declare variable in evironnment
                    currentStore.store(newLoc, argVal);

                }
                // Interpret the function body
                //evalStmt(funcBody, newEnvV, envF, envE, location, currentStore, currentImgStore);

                // lige nu returnere funktionen null - skal fikses
                yield new Triplet<>(null, currentStore, currentImgStore);


            }
            case ExprIdentifierNode exprIdentifierNode -> {
                // Get variable name
                String varName = exprIdentifierNode.getIdentifier();
                // Get memory location
                int varLocation = envV.lookup(varName);
                // Look up the actual
                Object value = store.lookup(varLocation);
                yield new Triplet<>(value, store, imgStore);
            }
            case ExprIntNode exprIntNode -> {
                // Extract integer value from the node
                int val = exprIntNode.getValue();
                IntVal result = new IntVal(val);
                yield new Triplet<>(result, store, imgStore);
            }
            case ExprLineNode exprLineNode -> {
                List<ExprNode> exprs = exprLineNode.getExpressions();
                if (exprs.size() % 2 != 0 || exprs.size() < 4) {
                    throw new RuntimeException("Invalid number of expressions in line. Found" + exprs.size());
                }

                List<LineSegment> segments = new ArrayList<>();
                Store currentStore = store;
                ImgStore currentImgStore = imgStore;


                // Evaluate start point
                Point start = (Point) evalPoint(
                        exprs.get(0), exprs.get(1),
                        envV, envF, envE, location,
                        currentStore, currentImgStore ).getValue0();

                // Evaluate end point
                Point end = (Point) evalPoint(
                        exprs.get(2), exprs.get(3),
                        envV, envF, envE, location,
                        currentStore, currentImgStore ).getValue0();


                segments.add(new LineSegment(start, end));

                // Handle extended segment
                Point prevEnd = end;

                for (int i = 4; i <exprs.size(); i += 2){
                    // Evaluate next end point
                    Point nextEnd = (Point) evalPoint(
                            exprs.get(i), exprs.get(i+1),
                            envV, envF, envE, location,
                            currentStore, currentImgStore ).getValue0();

                    segments.add(new LineSegment(prevEnd, nextEnd));
                    prevEnd = nextEnd;
                }

                LineVal result = new LineVal(segments);
                yield new Triplet<>(result, currentStore, currentImgStore);

            }
            case ExprListAccessNode exprListAccessNode -> {
                String varName = exprListAccessNode.getIdentifier();
                List<ExprNode> indexExprs = exprListAccessNode.getExpressions();

                // Look up identifer memory location
                int arrlocation = envV.lookup(varName);

                // fecth actual list from store
                Object listObj = store.lookup(arrlocation);

                if(!(listObj instanceof ListVal)) {
                    throw new RuntimeException("Variable " + varName + " is not a list");
                }

                ListVal listVal = (ListVal) listObj;
                List<Object> currentList = listVal.getElments();

                // evalaute each index expression and access nestet list
                Object currentValue = null;

                for(int i = 0; i < indexExprs.size(); i++){
                    ExprNode indexExpr = indexExprs.get(i);

                    // Evaluate index expression
                    var evalResult = evalExpr(
                            envV, envF, envE,
                            location, indexExpr,
                            store, imgStore);
                    Object indexVal = evalResult.getValue0();

                    if (!(indexVal instanceof IntVal)){
                        throw new RuntimeException("List index must be a number, got " + indexVal);
                    }

                    int index = ((IntVal) indexVal).getValue();

                    if (index < 0 || index >= currentList.size()){
                        throw new RuntimeException("List index out of bounds. Got" + index + "For" + varName);
                    }

                    currentValue = currentList.get(index);

                    // if not on the last index, the value must be another list
                    if(i < indexExprs.size() - 1){
                        if(currentValue instanceof ListVal){
                            currentList = ((ListVal) currentList).getElments();
                        } else {
                            throw new RuntimeException("Nested access on non-list element at index" + index);
                        }
                    }
                }

                Object resultValue;

                // return the final value accessed
                if (currentValue instanceof ListVal){
                    resultValue = ((ListVal) currentList).getElments().get(0);
                } else {
                    resultValue = currentValue;
                }
                yield new Triplet<>(resultValue, store, imgStore);
            }
            case ExprListDeclaration exprListDeclaration -> {
                List<ExprNode> exprs = exprListDeclaration.getExpressions();
                List<Object> results = new ArrayList<>();
                Store currentStore = store;
                ImgStore currentImgStore = imgStore;

                for(ExprNode e: exprs){
                    var res = evalExpr(envV, envF, envE, location, e, currentStore, currentImgStore);
                    results.add(res.getValue0());
                    currentStore = res.getValue1();
                    currentImgStore = res.getValue2();
                }

                ListVal result = new ListVal(results);

                yield new Triplet<>(result, currentStore, currentImgStore);

            }
            case ExprPlaceNode exprPlaceNode -> null;
            case ExprRotateNode exprRotateNode -> null;
            case ExprScaleNode exprScaleNode -> null;
            case ExprStringNode exprStringNode -> null;
            case ExprTextNode exprTextNode -> null;
            case ExprUnopNode exprUnopNode -> {
                var e1 = exprUnopNode.getExpression();
                var r1 = evalExpr(envV, envF, envE, location, e1, store, imgStore);

                var val1 = r1.getValue0();
                var store2 = r1.getValue1();
                var imgStore2 = r1.getValue2();

                var op = exprUnopNode.getUnOp();

                // Evaluate unary operation
                Object result = evalUnopExpr(op, val1);  // <- you will implement this function

                yield new Triplet<>(result, store2, imgStore2);
            }
        };
    }

    private static Object evalBinopExpr(Object v1, BinOp op, Object v2) {
        return switch(op) {
            case ADD -> (int)v1 + (int)v2;
            case SUB -> (int)v1 - (int)v2;
            case MUL -> (int)v1 * (int)v2;
            case DIV -> (int)v1 / (int)v2;
            case LT -> (int)v1 < (int)v2;
            case EQ -> (int)v1 == (int)v2;
            case AND -> (int)v1 & (int)v2;
            case CONCAT -> null;
        };
    }

    private static Object evalUnopExpr(UnOp op, Object val) {
    return switch (op) {
        case NEG -> -(double) val;
    };

    }

    private static Pair<Point, Pair<Store, ImgStore>> evalPoint(
            ExprNode xExpr, ExprNode yExpr,
            VarEnvironment envV, FunEnvironment envF, EventEnvironment envE,
            int location, Store store, ImgStore imgStore
    ){
        // Evaluate x expression
        var xRes = evalExpr(envV, envF, envE,location, xExpr, store, imgStore );
        if (!(xRes.getValue0() instanceof DoubleVal xVal)) {
            throw new RuntimeException("Expected DoubleVal for x-coordinate, but got: " + xRes.getValue0());
        }


        // Evaluate y expression
        var yRes = evalExpr(envV, envF, envE, location, yExpr, store, imgStore );
        if(!(yRes.getValue0() instanceof DoubleVal yVal)) {
            throw new RuntimeException("Expected DoubleVal for y-coordinate, but got: " + yRes.getValue0());
        }

        // Contruct point
        Point point = new Point(xVal.getValue(), yVal.getValue());

        // Return point an update enviroments
        return new Pair<>(point, new Pair<>(yRes.getValue1(), yRes.getValue2()));

    }
}

