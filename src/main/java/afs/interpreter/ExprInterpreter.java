package afs.interpreter;

import afs.interpreter.expressions.*;
import afs.interpreter.expressions.shape.*;
import afs.interpreter.interfaces.*;
import afs.nodes.expr.*;
import afs.nodes.stmt.StmtNode;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;

public class ExprInterpreter {
    public static Triplet<Val, Store, ImgStore> evalExpr(VarEnvironment envV,
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
                Val val = evalBinopExpr((Val) r1.getValue0(), op, (Val) r2.getValue0());
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
                if (exprs.size() == 6) {
                    var startR = evalPoint(exprs.get(0), exprs.get(1), envV, envF, envE, location, store, imgStore ).getValue0();
                    var controlR = evalPoint(exprs.get(2), exprs.get(3), envV, envF, envE, location, store, imgStore ).getValue0();
                    var endR = evalPoint(exprs.get(4), exprs.get(5), envV, envF, envE, location, store, imgStore ).getValue0();
                    Shape curve = new ShapeCurve(startR, controlR, endR);
                    yield new Triplet<>(new ShapeVal(curve), store, imgStore);
                } else {
                    int line = exprCurveNode.getLineNumber();
                    int col = exprCurveNode.getColumnNumber();
                    ExprCurveNode firstCurveNode = new ExprCurveNode(exprs.subList(0, 6), line, col);
                    var firstCurve = evalExpr(envV, envF, envE, location, firstCurveNode, store, imgStore).getValue0();

                    ExprCurveNode lastCurveNode = new ExprCurveNode(exprs.subList(4, exprs.size()), line, col);
                    var lastCurve = evalExpr(envV, envF, envE, location, lastCurveNode, store, imgStore).getValue0();
                    yield new Triplet<>(new ShapeVal(firstCurve, lastCurve), store, imgStore);
                }
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
                var funcData = envF.lookup(funcName);

                // Base case
                if (args.isEmpty()) {
                    // Check for events
                    EventHandler.check(envV, envF, envE, location, funcName, store, imgStore);
                    // Call function by evaluating the statement in the function
                    StmtNode funcBody = funcData.getValue0();
                    yield StmtInterpreter.evalStmt(funcData.getValue2(), envF, envE, location, funcBody, store, imgStore);
                } else {
                    int line = exprFunctionCallNode.getLineNumber();
                    int col = exprFunctionCallNode.getColumnNumber();

                    // Get parameters
                    List<String> paramNames = funcData.getValue1();

                    VarEnvironment funcEnvV = funcData.getValue2();

                    // Get expression e_n and evaluate it
                    ExprNode exprE_n = args.getLast();
                    Val exprVal = ExprInterpreter.evalExpr(envV, envF, envE, location, exprE_n, store, imgStore).getValue0();

                    List<ExprNode> newArgs = new ArrayList<>(args);
                    // Create a new function call with one less argument
                    ExprNode functionCallNode = new ExprFunctionCallNode(funcName, newArgs.subList(0, newArgs.size() - 1), line, col);

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
                        yield evalExpr(envV, envF, envE, location, functionCallNode, store, imgStore);
                    } else { // If e_n is not a list
                        int paramLocation = store.nextLocation();
                        // Declare a new parameter, assign it the location l
                        funcEnvV.declare(paramNames.get(n), paramLocation);
                        // Store the value of expression e_n at the location
                        store.bind(location, exprVal);

                        // Evaluate the new function call with one less argument and with new location
                        yield evalExpr(envV, envF, envE, store.nextLocation(), functionCallNode, store, imgStore);
                    }
                }
            }
            case ExprIdentifierNode exprIdentifierNode -> {
                // Get variable name
                String varName = exprIdentifierNode.getIdentifier();
                System.out.println("Looking up '" + varName + "' in env: " + envV);
                try {
                    int varLocation = envV.lookup(varName);
                    Val value = store.lookup(varLocation);
                    System.out.println("Found '" + varName + "' at location " + varLocation + " with value " + value);
                    yield new Triplet<>(value, store, imgStore);
                } catch (RuntimeException e) {
                    throw new RuntimeException("Variable '" + varName + "' not found in environment", e);
                }
            }
            case ExprIntNode exprIntNode -> {
                // Extract integer value from the node
                int val = exprIntNode.getValue();
                IntVal result = new IntVal(val);
                yield new Triplet<>(result, store, imgStore);
            }
            case ExprLineNode exprLineNode -> {
                List<ExprNode> exprs = exprLineNode.getExpressions();;
                if (exprs.size() == 4) {
                    var startR = evalPoint(exprs.get(0), exprs.get(1), envV, envF, envE, location, store, imgStore ).getValue0();
                    var endR = evalPoint(exprs.get(2), exprs.get(3), envV, envF, envE, location, store, imgStore ).getValue0();
                    Shape line = new ShapeLine(startR, endR);
                    yield new Triplet<>(new ShapeVal(line), store, imgStore);
                } else {
                    int line = exprLineNode.getLineNumber();
                    int col = exprLineNode.getColumnNumber();
                    ExprNode firstLineNode = new ExprLineNode(exprs.subList(0, 4), line, col);
                    var firstLine = evalExpr(envV, envF, envE, location, firstLineNode, store, imgStore).getValue0();

                    ExprNode lastLineNode = new ExprLineNode(exprs.subList(2, exprs.size()), line, col);
                    var lastLine = evalExpr(envV, envF, envE, location, lastLineNode, store, imgStore).getValue0();
                    yield new Triplet<>(new ShapeVal(firstLine, lastLine), store, imgStore);
                }
            }
            case ExprListAccessNode exprListAccessNode -> {
                List<ExprNode> indexExprs = exprListAccessNode.getExpressions();
                String varName = exprListAccessNode.getIdentifier();
                if (indexExprs.size() == 1) {
                    // Lookup identifier in environment and store
                    Val identVal = store.lookup(envV.lookup(varName));
                    // Get value of expression
                    Val exprVal = evalExpr(envV, envF, envE, location, indexExprs.getFirst(), store, imgStore).getValue0();
                    // Get value at index exprVal
                    Val val = identVal.asList().get(exprVal.asInt());
                    yield new Triplet<>(val, store, imgStore);
                } else {
                    int line = exprListAccessNode.getLineNumber();
                    int col = exprListAccessNode.getColumnNumber();
                    // Create a new ExprListAccessNode with one less element in
                    List<ExprNode> restExprs = indexExprs.subList(0, indexExprs.size() - 1);
                    ExprListAccessNode restAccess = new ExprListAccessNode(varName, restExprs, line, col);
                    // Evaluate the new ExprListAccessNode
                    Val restVal = evalExpr(envV, envF, envE, location, restAccess, store, imgStore).getValue0();
                    // Evaluate the last expression (e_n)
                    Val exprVal = evalExpr(envV, envF, envE, location, indexExprs.getLast(), store, imgStore).getValue0();
                    // Get the value at index exprVal
                    Val val = restVal.asList().get(exprVal.asInt());
                    yield new Triplet<>(val, store, imgStore);
                }
            }
            case ExprListDeclaration exprListDeclaration -> {
                List<ExprNode> exprs = exprListDeclaration.getExpressions();
                List<Val> elements = new ArrayList<>();

                // Evaluate all expressions (handling nested lists)
                for (ExprNode e : exprs) {
                    var res = evalExpr(envV, envF, envE, location, e, store, imgStore);
                    Val val = res.getValue0();
                    elements.add(val);
                }

                ListVal result = new ListVal(elements);

                yield new Triplet<>(result, store, imgStore);
            }
            case ExprPlaceNode exprPlaceNode -> {
                // Get the shape to place
                var shapeExpr = exprPlaceNode.getFirstExpression();
                var xExpr = exprPlaceNode.getSecondExpression();
                var yExpr = exprPlaceNode.getThirdExpression();

                // Evaluate the shape
                Val shapeResult = evalExpr(envV, envF, envE, location, shapeExpr, store, imgStore).getValue0();
                Point pointPivot = evalPoint(xExpr, yExpr, envV, envF, envE, location, store, imgStore).getValue0();

                // Extract x and y coordinate
                double x = pointPivot.getX();
                double y = pointPivot.getY();

                // Calculate the center of the shape
                Point center = ShapeHandler.getCenter(shapeResult.asShape());

                // Create a new shape by translating all points
                double deltaX = x - center.getX();
                double deltaY = y - center.getY();
                List<Shape> shapes = new ArrayList<>();
                for (Shape shape : shapeResult.asShape()) {
                    Shape newShape = shape.addToPoints(deltaX, deltaY);
                    shapes.add(newShape);
                }
                yield new Triplet<>(new ShapeVal(shapes), store, imgStore);
            }
            case ExprRotateNode exprRotateNode -> {
                // Get the shape to place
                var shapeExpr = exprRotateNode.getFirstExpression();
                var xExpr = exprRotateNode.getSecondExpression();
                var yExpr = exprRotateNode.getThirdExpression();
                var angleExpr = exprRotateNode.getLastExpression();

                // Evaluate the shape
                Val shapeResult = evalExpr(envV, envF, envE, location, shapeExpr, store, imgStore).getValue0();

                // Evaluate the x and y coordinate
                Point pointPivot = evalPoint(xExpr, yExpr, envV, envF, envE, location, store, imgStore).getValue0();

                // Extract x and y-coordinates
                double pivotX = pointPivot.getX();
                double pivotY = pointPivot.getY();

                // Evaluate the angle
                Val angleResult = evalExpr(envV, envF, envE, location, angleExpr, store, imgStore).getValue0();

                // Convert the angle from degrees to radians
                double angleDegrees = angleResult.asDouble();
                double angleRadians = Math.toRadians(angleDegrees);

                // Create new shapes with rotated points
                List<Shape> shapes = new ArrayList<>();
                for (Shape shape : shapeResult.asShape()) {
                    List<Point> points = new ArrayList<>();
                    for (Point point : shape.getPoints()) {
                        double x = point.getX();
                        double y = point.getY();

                        // Apply rotation formula
                        double cosAngle = Math.cos(angleRadians);
                        double sinAngle = Math.sin(angleRadians);
                        double newX = pivotX + (x - pivotX) * cosAngle - (y - pivotY) * sinAngle;
                        double newY = pivotY + (x - pivotX) * sinAngle + (y - pivotY) * cosAngle;
                        points.add(new Point(newX, newY));
                    }
                    shapes.add(shape.createShape(points));
                }
                yield new Triplet<>(new ShapeVal(shapes), store, imgStore);
            }
            case ExprScaleNode exprScaleNode -> {
                var shapeExpr = exprScaleNode.getLeftExpression();
                var scaleXExpr = exprScaleNode.getMiddleExpression();
                var scaleYExpr = exprScaleNode.getRightExpression();

                // Evaluate the shape
                Val shapeResult = evalExpr(envV, envF, envE, location, shapeExpr, store, imgStore).getValue0();

                // Evaluate the x and y coordinate
                Point pointFactor = evalPoint(scaleXExpr, scaleYExpr, envV, envF, envE, location, store, imgStore).getValue0();

                // Extract x and y-coordinates
                double scaleFactorX = pointFactor.getX();
                double scaleFactorY = pointFactor.getY();

                // Calculate the center of the shape for scaling around the center
                Point center = ShapeHandler.getCenter(shapeResult.asShape());
                double centerX = center.getX();
                double centerY = center.getY();

                List<Shape> shapes = new ArrayList<>();
                for (Shape shape : shapeResult.asShape()) {
                    List<Point> points = new ArrayList<>();
                    for (Point point : shape.getPoints()) {
                        double x = point.getX();
                        double y = point.getY();

                        // Calculate the distance from center
                        double deltaX = x - centerX;
                        double deltaY = y - centerY;

                        // Scale the distance
                        double scaledDeltaX = deltaX * scaleFactorX;
                        double scaledDeltaY = deltaY * scaleFactorY;

                        // Calculate the new point
                        double newX = centerX + scaledDeltaX;
                        double newY = centerY + scaledDeltaY;
                        points.add(new Point(newX, newY));
                    }
                    shapes.add(shape.createShape(points));
                }
                yield new Triplet<>(new ShapeVal(shapes), store, imgStore);
            }
            case ExprStringNode exprStringNode -> {
                String value = exprStringNode.getValue();
                StringVal result = new StringVal(value);
                yield new Triplet<>(result, store, imgStore);
            }
            case ExprTextNode exprTextNode -> {
                var e = exprTextNode.getExpression();
                Val result = evalExpr(envV, envF, envE, location, e, store, imgStore).getValue0();

                String textValue = result.asString();

                Val shapeVal = new ShapeVal(new ShapeText(textValue));

                yield new Triplet<>(shapeVal, store, imgStore);
            }
            case ExprUnopNode exprUnopNode -> {
                var e1 = exprUnopNode.getExpression();
                var r1 = evalExpr(envV, envF, envE, location, e1, store, imgStore);

                Val val1 = (Val) r1.getValue0();
                var op = exprUnopNode.getUnOp();

                // Evaluate unary operation
                Val result = evalUnopExpr(op, val1);

                yield new Triplet<>(result, store, imgStore);
            }
        };
    }

    private static Val evalUnopExpr(UnOp op, Val val) {
        return switch (op) {
            case NEG -> {
                if (val instanceof IntVal intVal) {
                    yield new IntVal(-intVal.getValue());
                } else if (val instanceof DoubleVal doubleVal) {
                    yield new DoubleVal(-doubleVal.getValue());
                } else {
                    throw new RuntimeException("NEG operator requires IntVal or DoubleVal");
                }
            }
            case NOT -> {
                if (val instanceof BoolVal boolVal) {
                    yield new BoolVal(!boolVal.getValue());
                } else {
                    throw new RuntimeException("NOT operator requires BoolVal");
                }
            }
        };
    }

    private static Val evalBinopExpr(Val v1, BinOp op, Val v2) {
        return switch (op) {
            case ADD -> {
                if (v1 instanceof IntVal) {
                    yield new IntVal(v1.asInt() + v2.asInt());
                } else {
                    yield new DoubleVal(v1.asDouble() + v2.asDouble());
                }
            }
            case SUB -> {
                if (v1 instanceof IntVal) {
                    yield new IntVal(v1.asInt() - v2.asInt());
                } else {
                    yield new DoubleVal(v1.asDouble() - v2.asDouble());
                }
            }
            case MUL -> {
                if (v1 instanceof IntVal) {
                    yield new IntVal(v1.asInt() * v2.asInt());
                } else {
                    yield new DoubleVal(v1.asDouble() * v2.asDouble());
                }
            }
            case DIV -> {
                if (v1 instanceof IntVal) {
                    yield new IntVal(v1.asInt() / v2.asInt());
                } else {
                    yield new DoubleVal(v1.asDouble() / v2.asDouble());
                }
            }
            case LT -> {
                if (v1 instanceof IntVal) {
                    yield new BoolVal(v1.asInt() < v2.asInt());
                } else {
                    yield new BoolVal(v1.asDouble() < v2.asDouble());
                }
            }
            case EQ -> {
                switch (v1) {
                    case IntVal ignored -> {
                        yield new BoolVal(v1.asInt() == v2.asInt());
                    }
                    case DoubleVal ignored -> {
                        yield new BoolVal(v1.asDouble() == v2.asDouble());
                    }
                    case BoolVal ignored -> {
                        yield new BoolVal(v1.asBool() == v2.asBool());
                    }
                    case StringVal ignored -> {
                        yield new BoolVal(v1.asString().equals(v2.asString()));
                    }
                    default -> throw new RuntimeException("No");
                }
            }
            case AND -> new BoolVal(v1.asBool() && v2.asBool());
            case CONCAT -> {
                if (v1 instanceof StringVal) {
                    yield new StringVal(v1.asString() + v2.asString());
                } else if (v1 instanceof ListVal) {
                    List<Val> elements = new ArrayList<>(v1.asList());
                    elements.addAll(v2.asList());
                    yield new ListVal(elements);
                } else {
                    List<Shape> elements = new ArrayList<>(v1.asShape());
                    elements.addAll(v2.asShape());
                    yield new ShapeVal(elements);
                }
            }
        };
    }

    private static Triplet<Point, Store, ImgStore> evalPoint(
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
        return new Triplet<>(point, yRes.getValue1(), yRes.getValue2());

    }
}