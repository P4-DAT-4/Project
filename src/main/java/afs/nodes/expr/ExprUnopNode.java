package afs.nodes.expr;

public final class ExprUnopNode extends ExprNode {
    private final ExprNode expr;
    private final UnOp op;

    public ExprUnopNode(ExprNode expr, UnOp op, int line, int column) {
        super(line, column);
        this.expr = expr;
        this.op = op;
    }

    public ExprNode getExpression() {
        return expr;
    }

    public UnOp getUnOp() {
        return op;
    }

    @Override
    public String toString() {
        return op.toString();
    }
}
