package afs.astbuilder.checker;

import afs.astbuilder.checker.exceptions.TypeCheckException;
import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.checker.types.SimpleType;
import afs.astbuilder.nodes.expr.*;
import afs.astbuilder.visitor.ExprVisitor;

public class ExprTypeChecker implements ExprVisitor {

    @Override
    public AFSType visitExprAdditionNode(ExprAdditionNode node) {
        AFSType leftType = node.getLeftExpression().acceptVisit(this);
        AFSType rightType = node.getRightExpression().acceptVisit(this);

        boolean validLeftType = leftType.equals(SimpleType.INT) || leftType.equals(SimpleType.DOUBLE);
        boolean validRightType = rightType.equals(leftType);

        if (validLeftType && validRightType) {
            return leftType;
        } else {
            throw new TypeCheckException("Invalid types for addition: " + leftType + " and " + rightType);
        }
    }

    @Override
    public AFSType visitExprAndNode(ExprAndNode node) {
        return null;
    }

    @Override
    public AFSType visitExprBoolNode(ExprBoolNode node) {
        return null;
    }

    @Override
    public AFSType visitExprConcatenationNode(ExprConcatenationNode node) {
        // Valid types: list, string, shape
        return null;
    }

    @Override
    public AFSType visitExprCurveNode(ExprCurveNode node) {
        return null;
    }

    @Override
    public AFSType visitExprDivisionNode(ExprDivisionNode node) {
        return null;
    }

    @Override
    public AFSType visitExprDoubleNode(ExprDoubleNode node) {
        return null;
    }

    // e1 == e2
    @Override
    public AFSType visitExprEqualNode(ExprEqualsNode node) {
        // Possible Types: int, double, string, boolean, list
        return null;
    }

    @Override
    public AFSType visitExprFunctionCallNode(ExprFunctionCallNode node) {
        // Return type: return type of the function
        return null;
    }

    @Override
    public AFSType visitExprIdentifierNode(ExprIdentifierNode node) {
        return null;
    }

    @Override
    public AFSType visitExprIntNode(ExprIntNode node) {
        return null;
    }

    @Override
    public AFSType visitExprLessThanNode(ExprLessThanNode node) {
        return null;
    }

    @Override
    public AFSType visitExprLineNode(ExprLineNode node) {
        // expressions must be type int or double?
        // expressions must be a multiple of 2
        return null;
    }

    @Override
    public AFSType visitExprListAccessNode(ExprListAccessNode node) {
        // Valid Type: List and int as accessor
        // Return type: the type of the list
        return null;
    }

    @Override
    public AFSType visitExprListNode(ExprListNode node) {
        return null;
    }

    @Override
    public AFSType visitExprMultiplicationNode(ExprMultiplicationNode node) {
        return null;
    }

    @Override
    public AFSType visitExprNegationNode(ExprNegationNode node) {
        return null;
    }

    @Override
    public AFSType visitExprNotNode(ExprNotNode node) {
        //
        return null;
    }

    @Override
    public AFSType visitExprPlaceNode(ExprPlaceNode node) {
        // place e1 at e2: e1 = shape, e2 = double/int
        return null;
    }

    @Override
    public AFSType visitExprRotateNode(ExprRotateNode node) {
        // rotate e1 around e2 by e3: e1, e2 = shape, e3: double/int?
        return null;
    }

    @Override
    public AFSType visitExprScaleNode(ExprScaleNode node) {
        // scale e1 by e2: e1 = shape, e2 = int/double
        return null;
    }

    @Override
    public AFSType visitExprStringNode(ExprStringNode node) {
        // types: string
        return SimpleType.STRING;
    }

    @Override
    public AFSType visitExprSubtractionNode(ExprSubtractionNode node) {
        return null;
    }

    @Override
    public AFSType visitExprTextNode(ExprTextNode node) {
        // types: e: string
        var expression = node.getExpression().acceptVisit(this);

        if(expression.equals(SimpleType.STRING)) {
            return SimpleType.SHAPE;

        } else {
            throw new TypeCheckException("Invalid type for text node: " + expression);
        }
    }
}
