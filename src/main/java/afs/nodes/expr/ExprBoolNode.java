package afs.nodes.expr;

public class ExprBoolNode extends ExprNode {
    private final boolean value;

    public ExprBoolNode(String value, int line, int column) {
        super(line, column);
        this.value = Boolean.parseBoolean(value);
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
