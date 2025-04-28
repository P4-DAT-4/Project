package afs.astbuilder.nodes.expr;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.visitor.ExprVisitor;

public class ExprNotNode extends ExprNode {
    private final ExprNode expression;

    public ExprNotNode(ExprNode expression) {
        this.expression = expression;
    }

    public ExprNode getExpression() {
        return expression;
    }

    @Override
    public AFSType acceptVisit(ExprVisitor visitor) {
        return visitor.visitExprNotNode(this);
    }

    @Override
    public String toString() {
        return "!e";
    }
}
