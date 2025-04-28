package afs.astbuilder.nodes.expr;

import afs.astbuilder.checker.types.AFSType;
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
    public AFSType acceptVisit(ExprVisitor visitor) {
        return visitor.visitExprIntNode(this);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
