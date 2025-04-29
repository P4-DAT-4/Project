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
        AFSType leftType = node.getLeftExpression().acceptVisit(this);
        AFSType rightType = node.getRightExpression().acceptVisit(this);

        if (leftType.equals(SimpleType.BOOL) && rightType.equals(SimpleType.BOOL)) {
            return SimpleType.BOOL;
        } else {
            throw new TypeCheckException("Invalid types for and: " + leftType + " and " + rightType);
        }
    }

    @Override
    public AFSType visitExprBoolNode(ExprBoolNode node) {
        return SimpleType.BOOL;
    }

    @Override
    public AFSType visitExprConcatenationNode(ExprConcatenationNode node) {
        // Valid types: list, string, shape
        AFSType leftType = node.getLeftExpression().acceptVisit(this);
        AFSType rightType = node.getRightExpression().acceptVisit(this);


        if (leftType.equals(SimpleType.STRING) && rightType.equals(SimpleType.STRING)) {
            return SimpleType.STRING;
        } else if (leftType.equals(SimpleType.SHAPE) && rightType.equals(SimpleType.SHAPE)) {
            return SimpleType.SHAPE;
        } else if (false) { // lists
            return null;
        } else {
            throw new TypeCheckException("Invalid types for concatenation: " + leftType + " and " + rightType);
        }
    }

    @Override
    public AFSType visitExprCurveNode(ExprCurveNode node) {
        // AFSType expressions = node.getExpressions().acceptVisit(this);

        return null;
    }

    @Override
    public AFSType visitExprDivisionNode(ExprDivisionNode node) {
        AFSType leftType = node.getLeftExpression().acceptVisit(this);
        AFSType rightType = node.getRightExpression().acceptVisit(this);

        boolean validLeftType = leftType.equals(SimpleType.INT) || leftType.equals(SimpleType.DOUBLE);
        boolean validRightType = rightType.equals(SimpleType.INT) || rightType.equals(SimpleType.DOUBLE);

        if (validLeftType && validRightType) {
            return leftType;
        } else {
            throw new TypeCheckException("Invalid types for division: " + leftType + " and " + rightType);
        }
    }

    @Override
    public AFSType visitExprDoubleNode(ExprDoubleNode node) {
        return SimpleType.DOUBLE;
    }

    // e1 == e2
    @Override
    public AFSType visitExprEqualNode(ExprEqualsNode node) {
        // Possible Types: int, double, string, boolean, list
        AFSType leftType = node.getLeftExpression().acceptVisit(this);
        AFSType rightType = node.getRightExpression().acceptVisit(this);
        // Mangler for lister
        boolean validLeftType = leftType.equals(SimpleType.INT) || leftType.equals(SimpleType.DOUBLE) || leftType.equals(SimpleType.STRING) || leftType.equals(SimpleType.BOOL);
        boolean validRightType = rightType.equals(leftType);

        if (validLeftType && validRightType) {
            return SimpleType.BOOL;
        } else {
            throw new TypeCheckException("Invalid types for equality: " + leftType + " and " + rightType);
        }

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
        return SimpleType.INT;
    }

    @Override
    public AFSType visitExprLessThanNode(ExprLessThanNode node) {
        // Valid types: int, double
        AFSType leftType = node.getLeftExpression().acceptVisit(this);
        AFSType rightType = node.getRightExpression().acceptVisit(this);

        boolean validLeftType = leftType.equals(SimpleType.INT) || leftType.equals(SimpleType.DOUBLE);
        boolean validRightType = rightType.equals(leftType);

        if (validLeftType && validRightType) {
            return SimpleType.BOOL;
        } else {
            throw new TypeCheckException("Invalid types for less than: " + leftType + " and " + rightType);
        }
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
        AFSType leftType = node.getLeftExpression().acceptVisit(this);
        AFSType rightType = node.getRightExpression().acceptVisit(this);

        boolean validLeftType = leftType.equals(SimpleType.INT) || leftType.equals(SimpleType.DOUBLE);
        boolean validRightType = rightType.equals(leftType);

        if (validLeftType && validRightType) {
            return leftType;
        } else {
            throw new TypeCheckException("Invalid types for multiplication: " + leftType + " and " + rightType);
        }
    }

    @Override
    public AFSType visitExprNegationNode(ExprNegationNode node) {
        // Valid types: int, double
        AFSType expression = node.getExpression().acceptVisit(this);

        if (expression.equals(SimpleType.INT) || expression.equals(SimpleType.DOUBLE)) {
            return expression;
        } else {
            throw new TypeCheckException("Invalid type for negation: " + expression);
        }
    }

    @Override
    public AFSType visitExprNotNode(ExprNotNode node) {
        // Valid types: boolean
        AFSType expression = node.getExpression().acceptVisit(this);

        if (expression.equals(SimpleType.BOOL)) {
            return expression;
        } else {
            throw new TypeCheckException("Invalid type for negation: " + expression);
        }
    }

    @Override
    public AFSType visitExprPlaceNode(ExprPlaceNode node) {
        // place e1 at e2: e1 = shape, e2 = double/int
        AFSType expression1 = node.getLeftExpression().acceptVisit(this);
        AFSType type = node.getRightExpression().acceptVisit(this);

        if (expression1.equals(SimpleType.SHAPE) && (type.equals(SimpleType.INT) || type.equals(SimpleType.DOUBLE))) {
            return expression1;
        } else {
            throw new TypeCheckException("Invalid types for place: " + expression1 + " and " + type);
        }

    }

    @Override
    public AFSType visitExprRotateNode(ExprRotateNode node) {
        // rotate e1 around e2 by e3: e1, e2 = shape, e3: double/int?
        AFSType expression1 = node.getLeftExpression().acceptVisit(this);
        AFSType expression2 = node.getMiddleExpression().acceptVisit(this);
        AFSType type = node.getRightExpression().acceptVisit(this);

        if (expression1.equals(SimpleType.SHAPE) && expression2.equals(SimpleType.SHAPE) && (type.equals(SimpleType.INT) || type.equals(SimpleType.DOUBLE))) {
            return expression1;
        } else {
            throw new TypeCheckException("Invalid types for rotate: " + expression1 + " and " + expression2);
        }
    }

    @Override
    public AFSType visitExprScaleNode(ExprScaleNode node) {
        // scale e1 by e2: e1 = shape, e2 = int/double
        AFSType expression1 = node.getLeftExpression().acceptVisit(this);
        AFSType type = node.getRightExpression().acceptVisit(this);

        if (expression1.equals(SimpleType.SHAPE) && (type.equals(SimpleType.INT) || type.equals(SimpleType.DOUBLE))) {
            return expression1;
        } else {
            throw new TypeCheckException("Invalid types for scale: " + expression1 + " and " + type);
        }
    }

    @Override
    public AFSType visitExprStringNode(ExprStringNode node) {
        // types: string
        return SimpleType.STRING;
    }

    @Override
    public AFSType visitExprSubtractionNode(ExprSubtractionNode node) {
        AFSType leftType = node.getLeftExpression().acceptVisit(this);
        AFSType rightType = node.getRightExpression().acceptVisit(this);

        boolean validLeftType = leftType.equals(SimpleType.INT) || leftType.equals(SimpleType.DOUBLE);
        boolean validRightType = rightType.equals(leftType);

        if (validLeftType && validRightType) {
            return leftType;
        } else {
            throw new TypeCheckException("Invalid types for subtraction: " + leftType + " and " + rightType);
        }
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
