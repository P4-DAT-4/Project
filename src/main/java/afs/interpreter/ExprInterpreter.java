package afs.interpreter;

import afs.nodes.expr.*;
import org.javatuples.Triplet;

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
            case ExprBoolNode exprBoolNode -> null;
            case ExprCurveNode exprCurveNode -> null;
            case ExprDoubleNode exprDoubleNode -> null;
            case ExprFunctionCallNode exprFunctionCallNode -> null;
            case ExprIdentifierNode exprIdentifierNode -> null;
            case ExprIntNode exprIntNode -> null;
            case ExprLineNode exprLineNode -> null;
            case ExprListAccessNode exprListAccessNode -> null;
            case ExprListDeclaration exprListDeclaration -> null;
            case ExprPlaceNode exprPlaceNode -> null;
            case ExprRotateNode exprRotateNode -> null;
            case ExprScaleNode exprScaleNode -> null;
            case ExprStringNode exprStringNode -> null;
            case ExprTextNode exprTextNode -> null;
            case ExprUnopNode exprUnopNode -> null;
        };
    }

    private Object evalBinopExpr(Object v1, BinOp op, Object v2) {
        return switch(op) {
            case ADD -> (int)v1 + (int)v2;
            case SUB -> (int)v1 - (int)v2;
            case MUL -> null;
            case DIV -> null;
            case LT -> null;
            case EQ -> null;
            case AND -> null;
            case CONCAT -> null;
        };
    }
}