package afs.astbuilder.nodes.expr;

import afs.astbuilder.visitor.ExprVisitor;

public class ExprDoubleNode extends ExprNode {
    private final double value;

    public ExprDoubleNode(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprDoubleNode(this);
    }

    @Override
    public String toString() {
        return String.format("ExprDouble (%s)", value);
    }
}
