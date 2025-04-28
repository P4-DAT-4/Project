package afs.astbuilder.nodes.expr;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.visitor.ExprVisitor;

public class ExprBoolNode extends ExprNode {
    private final boolean value;

    public ExprBoolNode(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public AFSType acceptVisit(ExprVisitor visitor) {
        return visitor.visitExprBoolNode(this);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
