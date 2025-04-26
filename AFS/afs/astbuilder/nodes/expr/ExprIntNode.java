package afs.astbuilder.nodes.expr;

import afs.astbuilder.visitor.ExprVisitor;

public class ExprIntNode extends ExprNode{
    private final int value;

    public ExprIntNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprIntNode(this);
    }

    @Override
    public String toString() {
        return String.format("ExprInt (%s)", value);
    }
}
