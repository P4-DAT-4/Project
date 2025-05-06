package afs.astbuilder.nodes.expr;

public class ExprDoubleNode extends ExprNode {
    private final double value;

    public ExprDoubleNode(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
