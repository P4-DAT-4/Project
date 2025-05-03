package afs.astbuilder.nodes.expr;

public class ExprUnopNode extends ExprNode {
    private final ExprNode expr;
    private final UnOp op;

    public ExprUnopNode(ExprNode expr, UnOp op) {
        this.expr = expr;
        this.op = op;
    }

    public ExprNode getExpr() {
        return expr;
    }

    public UnOp getUnOp() {
        return op;
    }

    @Override
    public String toString() {
        return op.toString() + " " + expr.toString();
    }
}
