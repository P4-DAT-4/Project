package afs.nodes.expr;

public final class ExprPlaceNode extends ExprNode {
    private final ExprNode leftExpression;
    private final ExprNode rightExpression;

    public ExprPlaceNode(ExprNode leftExpression, ExprNode rightExpression, int line, int column) {
        super(line, column);
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    public ExprNode getLeftExpression() {
        return leftExpression;
    }

    public ExprNode getRightExpression() {
        return rightExpression;
    }

    @Override
    public String toString() {
        return "Place e1 at e2";
    }
}
