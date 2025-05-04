package afs.nodes.expr;

public class ExprDoubleNode extends ExprNode {
    private final double value;

    public ExprDoubleNode(String value, int line, int column) {
        super(line, column);
        this.value = Double.parseDouble(value);
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
