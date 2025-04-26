package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprStringNode extends ExprNode {
    private final String value;

    public ExprStringNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprStringNode(this);
    }

    @Override
    public String toString() {
        return String.format("ExprString (%s)", value);
    }
}
