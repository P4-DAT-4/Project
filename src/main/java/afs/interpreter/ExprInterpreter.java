package afs.interpreter;

import afs.interpreter.expressions.*;
import afs.interpreter.interfaces.*;
import afs.nodes.expr.*;
import afs.nodes.stmt.StmtNode;
import afs.runtime.Shape;
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

                Store currentStore = store;
                ImgStore currentImgStore = imgStore;
                Shape curveShape = new Shape();

                // Evaluate start point
                var startR = evalPoint(exprs.get(0), exprs.get(1), envV, envF, envE, location, currentStore, currentImgStore );
                Point start = startR.getValue0();
                currentStore = startR.getValue1().getValue0();
                currentImgStore = startR.getValue1().getValue1();

                // Evaluate control point
                var controlR = evalPoint(exprs.get(2), exprs.get(3), envV, envF, envE, location, currentStore, currentImgStore );
                Point control = controlR.getValue0();
                currentStore = controlR.getValue1().getValue0();
                currentImgStore = controlR.getValue1().getValue1();

                // Evaluate end point
                var endR = evalPoint(exprs.get(4), exprs.get(5), envV, envF, envE, location, currentStore, currentImgStore );
                Point end = endR.getValue0();
                currentStore = endR.getValue1().getValue0();
                currentImgStore = endR.getValue1().getValue1();


                Shape.Segment firstSegment = new Shape.Segment(Shape.Segment.SegmentType.CURVE);
                firstSegment.addPoint(start.getX(), start.getY());
                firstSegment.addPoint(control.getX(), control.getY());
                firstSegment.addPoint(end.getX(), end.getY());

                curveShape.addSegment(firstSegment);


                for(int i = 6; i <exprs.size(); i += 4){
                    // Evaluate next control point
                    var nextControlR = evalPoint(exprs.get(i), exprs.get(i + 1), envV, envF, envE, location, currentStore, currentImgStore );
                    Point nextControl = nextControlR.getValue0();
                    currentStore = nextControlR.getValue1().getValue0();
                    currentImgStore = nextControlR.getValue1().getValue1();


                    // Evaluate next end point
                    var nextEndR = evalPoint(exprs.get(i + 2), exprs.get(i + 3), envV, envF, envE, location, currentStore, currentImgStore );
                    Point nextEnd = nextEndR.getValue0();
                    currentStore = nextEndR.getValue1().getValue0();
                    currentImgStore = nextEndR.getValue1().getValue1();

                    Shape.Segment segment = new Shape.Segment(Shape.Segment.SegmentType.CURVE);
                    segment.addPoint(nextControl.getX(), nextControl.getY());
                    segment.addPoint(nextEnd.getX(), nextEnd.getY());

                }

                yield new Triplet<>(curveShape, currentStore, currentImgStore);

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

                Store currentStore = store;
                ImgStore currentImgStore = imgStore;
                Shape lineShape = new Shape();

                // Evaluate start point
                var startR = evalPoint(exprs.get(0), exprs.get(1), envV, envF, envE, location, currentStore, currentImgStore );
                Point start = startR.getValue0();
                currentStore = startR.getValue1().getValue0();
                currentImgStore = startR.getValue1().getValue1();

                // Evaluate end point
                var endR = evalPoint(exprs.get(2), exprs.get(3), envV, envF, envE, location, currentStore, currentImgStore );
                Point end = endR.getValue0();
                currentStore = endR.getValue1().getValue0();
                currentImgStore = endR.getValue1().getValue1();

                Shape.Segment firstSegment = new Shape.Segment(Shape.Segment.SegmentType.LINE);
                firstSegment.addPoint(start.getX(), start.getY());
                firstSegment.addPoint(end.getX(), end.getY());
                lineShape.addSegment(firstSegment);


                for (int i = 4; i <exprs.size(); i += 2){
                    // Evaluate next end point
                    var nextEndR  = evalPoint(exprs.get(i), exprs.get(i+1), envV, envF, envE, location, currentStore, currentImgStore );
                    Point nextEnd = nextEndR.getValue0();
                    currentStore = nextEndR.getValue1().getValue0();
                    currentImgStore = nextEndR.getValue1().getValue1();

                    Shape.Segment segment = new Shape.Segment(Shape.Segment.SegmentType.LINE);
                    segment.addPoint(nextEnd.getX(), nextEnd.getY());
                    lineShape.addSegment(segment);

                }

                yield new Triplet<>(lineShape, currentStore, currentImgStore);

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
                List<Object> currentList = listVal.getElements();

                // evalaute each index expression and access nestet list
                Object currentValue = null;
                Store currentStore = store;
                ImgStore currentImgStore = imgStore;

                for(int i = 0; i < indexExprs.size(); i++){
                    ExprNode indexExpr = indexExprs.get(i);

                    // Evaluate index expression
                    var evalResult = evalExpr(envV, envF, envE, location, indexExpr, store, imgStore);
                    Object indexVal = evalResult.getValue0();
                    currentStore = evalResult.getValue1();
                    currentImgStore = evalResult.getValue2();

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
                            currentList = ((ListVal) currentList).getElements();
                        } else {
                            throw new RuntimeException("Nested access on non-list element at index" + index);
                        }
                    }
                }

                Object resultValue;

                // return the final value accessed
                if (currentValue instanceof ListVal){
                    resultValue = ((ListVal) currentList).getElements().get(0);
                } else {
                    resultValue = currentValue;
                }
                yield new Triplet<>(resultValue, currentStore, currentImgStore);
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
            case ExprPlaceNode exprPlaceNode -> {
                // Get the shape to place
                var shapeExpr = exprPlaceNode.getFExpression();
                var xExpr = exprPlaceNode.getSExpression();
                var yExpr = exprPlaceNode.getTExpression();


                // Evaluate the shape
                var shapeResult = evalExpr(envV, envF, envE, location, shapeExpr, store, imgStore);
                Object shapeValue = shapeResult.getValue0();
                Store updatedStore = shapeResult.getValue1();
                ImgStore updatedImgStore = shapeResult.getValue2();

                Shape shape = (Shape) shapeValue;

                // Evaluate the x and y coordinate
                var pointPivotResult = evalPoint(xExpr, yExpr, envV, envF, envE, location, updatedStore, updatedImgStore);
                Point pointPivot = pointPivotResult.getValue0();
                updatedStore = pointPivotResult.getValue1().getValue0();
                updatedImgStore = pointPivotResult.getValue1().getValue1();


                // Extract x and y coordinate
                double x = pointPivot.getX();
                double y = pointPivot.getY();

                // Calculate the center of the shape
                double centerX = 0;
                double centerY = 0;
                int pointCount = 0;

                for (Shape.Segment segment : shape.getSegments()) {
                    for (Point point : segment.getCoordinates()) {
                        centerX += point.getX();
                        centerY += point.getY();
                        pointCount++;

                    }
                }

                if (pointCount > 0) {
                    centerX /= pointCount;
                    centerY /= pointCount;
                }

                // Create a new shape by translating all points
                Shape placedShape = new Shape();
                double deltaX = x - centerX;
                double deltaY = y - centerY;

                for (Shape.Segment segment : shape.getSegments()) {
                    Shape.Segment newSegment = new Shape.Segment(segment.getType());

                    // Copy text content if it's a text segment
                    if (segment.getType() == Shape.Segment.SegmentType.TEXT) {
                        newSegment.setTextContent(segment.getTextContent());
                    }

                    // Translate all coordinates
                    for (Point point : segment.getCoordinates()) {
                        double newX = point.getX() + deltaX;
                        double newY = point.getY() + deltaY;
                        newSegment.addPoint(newX, newY);

                    }

                    placedShape.addSegment(newSegment);
                }

                yield new Triplet<>(placedShape, updatedStore, updatedImgStore);
            }
            case ExprRotateNode exprRotateNode -> {
                // Get the shape to place
                var shapeExpr = exprRotateNode.getFirstExpression();
                var xExpr = exprRotateNode.getSecondExpression();
                var yExpr = exprRotateNode.getThirdExpression();
                var angleExpr = exprRotateNode.getLastExpression();

                // Evaluate the shape
                var shapeResult = evalExpr(envV, envF, envE, location, shapeExpr, store, imgStore);
                Object shapeValue = shapeResult.getValue0();
                Store updatedStore = shapeResult.getValue1();
                ImgStore updatedImgStore = shapeResult.getValue2();

                Shape shape = (Shape) shapeValue;

                // Evaluate the x and y coordinate
                var pointPivotResult = evalPoint(xExpr, yExpr, envV, envF, envE, location, updatedStore, updatedImgStore);
                Point pointPivot = pointPivotResult.getValue0();
                updatedStore = pointPivotResult.getValue1().getValue0();
                updatedImgStore = pointPivotResult.getValue1().getValue1();

                // Extract x and y-coordinates
                double pivotX = pointPivot.getX();
                double pivotY = pointPivot.getY();


                // Evaluate the angle
                var angleResult = evalExpr(envV, envF, envE, location, angleExpr, updatedStore, updatedImgStore);
                Object angleValue = angleResult.getValue0();
                updatedStore = angleResult.getValue1();
                updatedImgStore = angleResult.getValue2();

                // Convert the angle from degrees to radians
                double angleDegrees = ((DoubleVal) angleValue).getValue();
                double angleRadians = Math.toRadians(angleDegrees);

                // Create a new shape by rotating all points
                Shape rotatedShape = new Shape();

                // Apply the rotation
                for (Shape.Segment segment : shape.getSegments()) {
                    Shape.Segment newSegment = new Shape.Segment(segment.getType());

                    // Copy text content if it's a text segment
                    if (segment.getType() == Shape.Segment.SegmentType.TEXT) {
                        newSegment.setTextContent(segment.getTextContent());
                    }

                    // Rotate all coordinates
                    for (Point point : segment.getCoordinates()) {
                            double x = point.getX();
                            double y = point.getY();

                            // Apply rotation formula
                            double cosAngle = Math.cos(angleRadians);
                            double sinAngle = Math.sin(angleRadians);
                            double newX = pivotX + (x - pivotX) * cosAngle - (y - pivotY) * sinAngle;
                            double newY = pivotY + (x - pivotX) * sinAngle + (y - pivotY) * cosAngle;

                            newSegment.addPoint(newX, newY);

                    }

                    rotatedShape.addSegment(newSegment);
                }

                yield new Triplet<>(rotatedShape, updatedStore, updatedImgStore);
            }
            case ExprScaleNode exprScaleNode -> {
                var shapeExpr = exprScaleNode.getLeftExpression();
                var scaleXExpr = exprScaleNode.getMiddleExpression();
                var scaleYExpr = exprScaleNode.getRightExpression();

                // Evaluate the shape
                var shapeResult = evalExpr(envV, envF, envE, location, shapeExpr, store, imgStore);
                Object shapeValue = shapeResult.getValue0();
                Store updatedStore = shapeResult.getValue1();
                ImgStore updatedImgStore = shapeResult.getValue2();

                Shape shape = (Shape) shapeValue;


                // Evaluate the x and y coordinate
                var pointPivotResult = evalPoint(scaleXExpr, scaleYExpr, envV, envF, envE, location, updatedStore, updatedImgStore);
                Point pointFactor = pointPivotResult.getValue0();
                updatedStore = pointPivotResult.getValue1().getValue0();
                updatedImgStore = pointPivotResult.getValue1().getValue1();

                // Extract x and y-coordinates
                double scaleFactorX = pointFactor.getX();
                double scaleFactorY = pointFactor.getY();


                // Calculate the center of the shape for scaling around the center
                double centerX = 0;
                double centerY = 0;
                int pointCount = 0;

                for (Shape.Segment segment : shape.getSegments()) {
                    for (Point point : segment.getCoordinates()) {
                        centerX += point.getX();
                        centerY += point.getY();
                        pointCount++;

                    }
                }

                if (pointCount > 0) {
                    centerX /= pointCount;
                    centerY /= pointCount;
                }

                // Create a new shape by scaling all points around the center
                Shape scaledShape = new Shape();

                for (Shape.Segment segment : shape.getSegments()) {
                    Shape.Segment newSegment = new Shape.Segment(segment.getType());

                    // Copy text content if it's a text segment
                    if (segment.getType() == Shape.Segment.SegmentType.TEXT) {
                        newSegment.setTextContent(segment.getTextContent());
                    }

                    // Scale all coordinates
                    for (Point point : segment.getCoordinates()) {
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

                        newSegment.addPoint(newX, newY);

                    }

                    scaledShape.addSegment(newSegment);
                }

                yield new Triplet<>(scaledShape, updatedStore, updatedImgStore);
            }
            case ExprStringNode exprStringNode -> {
                String value = exprStringNode.getValue();
                StringVal result = new StringVal(value);
                yield new Triplet<>(result, store, imgStore);
            }
            case ExprTextNode exprTextNode -> {
                var e = exprTextNode.getExpression();

                var result = evalExpr(envV, envF, envE, location, e, store, imgStore);

                Object value = result.getValue0();
                Store updatedStore = result.getValue1();
                ImgStore updatedImgStore = result.getValue2();

                String textValue = value.toString();

                Shape textShape = Shape.createText(textValue, 10, 20);

                updatedImgStore.push(textShape);

                yield new Triplet<>(value, updatedStore, updatedImgStore);
            }
            case ExprUnopNode exprUnopNode -> {
                var e1 = exprUnopNode.getExpression();
                var r1 = evalExpr(envV, envF, envE, location, e1, store, imgStore);

                var val1 = r1.getValue0();
                var op = exprUnopNode.getUnOp();

                // Evaluate unary operation
                Object result = evalUnopExpr(op, val1);

                yield new Triplet<>(result, store, imgStore);
            }
        };
    }

    private static Object evalUnopExpr(UnOp op, Object val) {
        return switch (op) {
            case NEG -> {
                if (val instanceof IntVal) {
                    yield -(int) val;
                } else if (val instanceof DoubleVal) {
                    yield -(double) val;
                } else {
                    throw new RuntimeException();
                }
            }
            case NOT -> !(boolean) val;
        };
    }

//
//    private static Object evalBinopExpr(Object v1, BinOp op, Object v2) {
//        return switch (op) {
//            case ADD -> {
//                if (v1 instanceof IntVal && v2 instanceof IntVal) {
//                    yield (int) v1 + (int) v2;
//                } else if (v1 instanceof DoubleVal && v2 instanceof DoubleVal) {
//                    yield (double) v1 + (double) v2;
//                } else {
//                    throw new RuntimeException();
//                }
//            }
//            case SUB -> {
//                if (v1 instanceof IntVal && v2 instanceof IntVal) {
//                    yield (int) v1 - (int) v2;
//                } else if (v1 instanceof DoubleVal && v2 instanceof DoubleVal) {
//                    yield (double) v1 - (double) v2;
//                } else {
//                    throw new RuntimeException();
//                }
//            }
//            case MUL -> {
//                if (v1 instanceof IntVal && v2 instanceof IntVal) {
//                    yield (int) v1 * (int) v2;
//                } else if (v1 instanceof DoubleVal && v2 instanceof DoubleVal) {
//                    yield (double) v1 * (double) v2;
//                } else {
//                    throw new RuntimeException();
//                }
//            }
//
//            case DIV -> {
//                if (v1 instanceof IntVal && v2 instanceof IntVal) {
//                    yield (int) v1 / (int) v2;
//                } else if (v1 instanceof DoubleVal && v2 instanceof DoubleVal) {
//                    yield (double) v1 / (double) v2;
//                } else {
//                    throw new RuntimeException();
//                }
//            }
//
//            case LT -> {
//                if (v1 instanceof IntVal && v2 instanceof IntVal) {
//                    yield (int) v1 < (int) v2;
//                } else if (v1 instanceof DoubleVal && v2 instanceof DoubleVal) {
//                    yield (double) v1 < (double) v2;
//                } else {
//                    throw new RuntimeException();
//                }
//            }
//
//            case EQ -> {
//                if (v1 instanceof IntVal && v2 instanceof IntVal) {
//                    yield (int) v1 == (int) v2;
//                } else if (v1 instanceof DoubleVal && v2 instanceof DoubleVal) {
//                    yield (double) v1 == (double) v2;
//                } else if (v1 instanceof BoolVal && v2 instanceof BoolVal) {
//                    yield (boolean) v1 == (boolean) v2;
//                } else if (v1 instanceof StringVal && v2 instanceof StringVal) {
//                    yield ((String) v1).equals((String) v2);
//                } else {
//                    yield false;
//                }
//            }
//            case AND -> {
//                yield (boolean) v1 && (boolean) v2;
//            }
//
//            case CONCAT -> {
//                if (v1 instanceof StringVal && v2 instanceof StringVal) {
//                    yield (String) v1 + (String) v2;
//                } else if (v1 instanceof Shape && v2 instanceof Shape) {
//                    Shape shape1 = (Shape) v1;
//                    Shape shape2 = (Shape) v2;
//                    yield shape1.concat(shape2);
//                } else if (v1 instanceof ListVal && v2 instanceof ListVal) {
//                    List<Object> result = new ArrayList<>((List<?>) v1);
//                    result.addAll((List<?>) v2);
//                    yield result;
//                } else {
//                    throw new RuntimeException();
//                }
//            }
//        };
//    }
//
//    private static Object evalUnopExpr(UnOp op, Object val) {
//        return switch (op) {
//            case NEG -> {
//                if (val instanceof IntVal intVal) {
//                    yield new IntVal(-intVal.getValue());
//                } else if (val instanceof DoubleVal doubleVal) {
//                    yield new DoubleVal(-doubleVal.getValue());
//                } else {
//                    throw new RuntimeException("NEG operator requires IntVal or DoubleVal");
//                }
//            }
//            case NOT -> {
//                if (val instanceof BoolVal boolVal) {
//                    yield new BoolVal(!boolVal.getValue());
//                } else if (val instanceof Boolean) {
//                    yield !(Boolean) val;
//                } else {
//                    throw new RuntimeException("NOT operator requires BoolVal or Boolean");
//                }
//            }
//        };
//    }




    private static Object evalBinopExpr(Object v1, BinOp op, Object v2) {
        return switch (op) {
            case ADD -> {
                if (v1 instanceof IntVal i1 && v2 instanceof IntVal i2) {
                    yield new IntVal(i1.getValue() + i2.getValue());
                } else if (v1 instanceof DoubleVal d1 && v2 instanceof DoubleVal d2) {
                    yield new DoubleVal(d1.getValue() + d2.getValue());
                } else {
                    throw new RuntimeException("Unsupported ADD operand types");
                }
            }
            case SUB -> {
                if (v1 instanceof IntVal i1 && v2 instanceof IntVal i2) {
                    yield new IntVal(i1.getValue() - i2.getValue());
                } else if (v1 instanceof DoubleVal d1 && v2 instanceof DoubleVal d2) {
                    yield new DoubleVal(d1.getValue() - d2.getValue());
                } else {
                    throw new RuntimeException("Unsupported SUB operand types");
                }
            }
            case MUL -> {
                if (v1 instanceof IntVal i1 && v2 instanceof IntVal i2) {
                    yield new IntVal(i1.getValue() * i2.getValue());
                } else if (v1 instanceof DoubleVal d1 && v2 instanceof DoubleVal d2) {
                    yield new DoubleVal(d1.getValue() * d2.getValue());
                } else {
                    throw new RuntimeException("Unsupported MUL operand types");
                }
            }
            case DIV -> {
                if (v1 instanceof IntVal i1 && v2 instanceof IntVal i2) {
                    yield new IntVal(i1.getValue() / i2.getValue());
                } else if (v1 instanceof DoubleVal d1 && v2 instanceof DoubleVal d2) {
                    yield new DoubleVal(d1.getValue() / d2.getValue());
                } else {
                    throw new RuntimeException("Unsupported DIV operand types");
                }
            }
            case LT -> {
                if (v1 instanceof IntVal i1 && v2 instanceof IntVal i2) {
                    yield i1.getValue() < i2.getValue();
                } else if (v1 instanceof DoubleVal d1 && v2 instanceof DoubleVal d2) {
                    yield d1.getValue() < d2.getValue();
                } else {
                    throw new RuntimeException("Unsupported LT operand types");
                }
            }
            case EQ -> {
                if (v1 instanceof IntVal i1 && v2 instanceof IntVal i2) {
                    yield i1.getValue() == i2.getValue();
                } else if (v1 instanceof DoubleVal d1 && v2 instanceof DoubleVal d2) {
                    yield d1.getValue() == d2.getValue();
                } else if (v1 instanceof BoolVal b1 && v2 instanceof BoolVal b2) {
                    yield b1.getValue() == b2.getValue();
                } else if (v1 instanceof StringVal s1 && v2 instanceof StringVal s2) {
                    yield s1.getValue().equals(s2.getValue());
                } else {
                    yield false;
                }
            }
            case AND -> {
                if (v1 instanceof BoolVal b1 && v2 instanceof BoolVal b2) {
                    yield b1.getValue() && b2.getValue();
                } else {
                    throw new RuntimeException("Unsupported AND operand types");
                }
            }
            case CONCAT -> {
                if (v1 instanceof StringVal s1 && v2 instanceof StringVal s2) {
                    yield new StringVal(s1.getValue() + s2.getValue());
                } else if (v1 instanceof Shape shape1 && v2 instanceof Shape shape2) {
                    yield shape1.concat(shape2);
                } else if (v1 instanceof ListVal list1 && v2 instanceof ListVal list2) {
                    List<Object> result = new ArrayList<>(list1.getElements());
                    result.addAll(list2.getElements());
                    yield new ListVal(result);
                } else {
                    throw new RuntimeException("Unsupported CONCAT operand types");
                }
            }
            default -> throw new RuntimeException("Unknown operator: " + op);
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

        Store currentStore = xRes.getValue1();
        ImgStore currentImgStore = xRes.getValue2();


        // Evaluate y expression
        var yRes = evalExpr(envV, envF, envE, location, yExpr, currentStore, currentImgStore );
        if(!(yRes.getValue0() instanceof DoubleVal yVal)) {
            throw new RuntimeException("Expected DoubleVal for y-coordinate, but got: " + yRes.getValue0());
        }

        // Contruct point
        Point point = new Point(xVal.getValue(), yVal.getValue());

        // Return point an update enviroments
        return new Pair<>(point, new Pair<>(yRes.getValue1(), yRes.getValue2()));

    }
}

