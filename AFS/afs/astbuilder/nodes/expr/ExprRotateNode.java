package afs.astbuilder.nodes.expr;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.visitor.ExprVisitor;

public class ExprRotateNode extends ExprNode {
    private final ExprNode leftExpression;
    private final ExprNode middleExpression;
    private final ExprNode rightExpression;

    public ExprRotateNode(ExprNode leftExpression, ExprNode middleExpression, ExprNode rightExpression) {
        this.leftExpression = leftExpression;
        this.middleExpression = middleExpression;
        this.rightExpression = rightExpression;
    }

    public ExprNode getLeftExpression() {
        return leftExpression;
    }

    public ExprNode getMiddleExpression() {
        return middleExpression;
    }

    public ExprNode getRightExpression() {
        return rightExpression;
    }

    @Override
    public AFSType acceptVisit(ExprVisitor visitor) {
        return visitor.visitExprRotateNode(this);
    }

    @Override
    public String toString() {
        return "Rotate e1 by e2 around e3";
    }
}
