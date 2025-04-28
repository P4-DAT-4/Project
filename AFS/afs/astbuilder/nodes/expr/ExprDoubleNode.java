package afs.astbuilder.nodes.expr;

import afs.astbuilder.checker.types.AFSType;
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
    public AFSType acceptVisit(ExprVisitor visitor) {
        return visitor.visitExprDoubleNode(this);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
