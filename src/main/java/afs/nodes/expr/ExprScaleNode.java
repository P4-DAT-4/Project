package afs.nodes.expr;

public final class ExprScaleNode extends ExprNode {
    private final ExprNode leftExpression;
    private final ExprNode middleExpression;
    private final ExprNode rightExpression;

    public ExprScaleNode(ExprNode leftExpression, ExprNode middleExpression, ExprNode rightExpression, int line, int column) {
        super(line, column);
        this.leftExpression = leftExpression;
        this.middleExpression = middleExpression;
        this.rightExpression = rightExpression;
    }

    public ExprNode getLeftExpression() {
        return leftExpression;
    }
    public ExprNode getMiddleExpression() {
        return middleExpression;
    }

    public ExprNode getRightExpression() {
        return rightExpression;
    }

    @Override
    public String toString() {
        return "Scale e1 by e2, e3";
    }
}
