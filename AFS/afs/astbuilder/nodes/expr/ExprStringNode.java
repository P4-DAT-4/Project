package afs.astbuilder.nodes.expr;

public class ExprStringNode extends ExprNode {
    private final String value;

    public ExprStringNode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
