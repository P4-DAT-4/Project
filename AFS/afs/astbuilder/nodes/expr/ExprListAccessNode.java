package afs.astbuilder.nodes.expr;

public class ExprListAccessNode extends ExprNode {
    private final ExprNode leftExpression;
    private final ExprNode rightExpression;

    public ExprListAccessNode(ExprNode leftExpression, ExprNode rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    public ExprNode leftExpression() {
        return leftExpression;
    }

    public ExprNode rightExpression() {
        return rightExpression;
    }
}
