package afs.nodes.expr;

public class ExprBinopNode extends ExprNode {
    private final ExprNode leftExpression;
    private final ExprNode rightExpression;
    private final BinOp op;

    public ExprBinopNode(ExprNode leftExpression, BinOp op, ExprNode rightExpression, int line, int column) {
        super(line, column);
        this.leftExpression = leftExpression;
        this.op = op;
        this.rightExpression = rightExpression;
    }

    public ExprNode getLeftExpression() {
        return leftExpression;
    }

    public BinOp getOp() {
        return op;
    }

    public ExprNode getRightExpression() {
        return rightExpression;
    }

    @Override
    public String toString() {
        return op.toString();
    }
}
