package afs.astbuilder.nodes.expr;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.visitor.ExprVisitor;

public class ExprNegationNode extends ExprNode {
    private final ExprNode expression;

    public ExprNegationNode(ExprNode expression) {
        this.expression = expression;
    }

    public ExprNode getExpression() {
        return expression;
    }

    @Override
    public AFSType acceptVisit(ExprVisitor visitor) {
        return visitor.visitExprNegationNode(this);
    }

    @Override
    public String toString() {
        return "-e";
    }
}
