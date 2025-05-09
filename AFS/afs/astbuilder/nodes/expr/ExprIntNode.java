package afs.astbuilder.nodes.expr;

public class ExprIntNode extends ExprNode {
    private final int value;

    public ExprIntNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
