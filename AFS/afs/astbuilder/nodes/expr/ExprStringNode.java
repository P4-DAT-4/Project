package afs.astbuilder.nodes.expr;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.visitor.ExprVisitor;

public class ExprStringNode extends ExprNode {
    private final String value;

    public ExprStringNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public AFSType acceptVisit(ExprVisitor visitor) {
        return visitor.visitExprStringNode(this);
    }

    @Override
    public String toString() {
        return value;
    }
}
