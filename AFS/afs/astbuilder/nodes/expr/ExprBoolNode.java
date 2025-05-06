package afs.astbuilder.nodes.expr;

public class ExprBoolNode extends ExprNode {
    private final boolean value;

    public ExprBoolNode(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
