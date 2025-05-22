package afs.nodes.expr;

public final class ExprStringNode extends ExprNode {
    private final String value;

    public ExprStringNode(String value, int line, int column) {
        super(line, column);
        this.value = value.length() >= 2 ? value.substring(1, value.length() - 1) : value;

    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
