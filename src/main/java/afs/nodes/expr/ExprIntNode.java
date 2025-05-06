package afs.nodes.expr;

public final class ExprIntNode extends ExprNode {
    private final int value;

    public ExprIntNode(String value, int line, int column) {
        super(line, column);
        this.value = Integer.parseInt(value);
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
