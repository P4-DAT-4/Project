package afs.interpreter;

import afs.interpreter.expressions.BoolVal;
import afs.interpreter.expressions.DoubleVal;
import afs.interpreter.expressions.IntVal;
import afs.nodes.expr.*;
import org.javatuples.Triplet;

import java.util.List;

public class ExprInterpreter {
    public Triplet<Object, Store, ImgStore> evalExpr(VarEnvironment envV,
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
                var store2 = r1.getValue1();
                var imgStore2 = r1.getValue2();

                var r2 = evalExpr(envV, envF, envE, location, e2, store2, imgStore2);

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
            case ExprCurveNode exprCurveNode -> null;
            case ExprDoubleNode exprDoubleNode -> {
                // Extract double value from the node
                double val = exprDoubleNode.getValue();
                DoubleVal result = new DoubleVal(val);
                yield new Triplet<>(result, store, imgStore);
            }
            case ExprFunctionCallNode exprFunctionCallNode -> {
                String funName = exprFunctionCallNode.getIdentifier();
                List<ExprNode> args = exprFunctionCallNode.getArguments();

                //Look up function definition

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
            case ExprLineNode exprLineNode -> null;
            case ExprListAccessNode exprListAccessNode -> null;
            case ExprListDeclaration exprListDeclaration -> null;
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

    private Object evalBinopExpr(Object v1, BinOp op, Object v2) {
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

    private Object evalUnopExpr(UnOp op, Object val) {
    return switch (op) {
        case NEG -> -(double) val;
    };
}
}

