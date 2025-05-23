package afs.nodes.expr;

public final class ExprRotateNode extends ExprNode {
    private final ExprNode firstExpression;
    private final ExprNode secondExpression;
    private final ExprNode thirdExpression;
    private final ExprNode lastExpression;


    public ExprRotateNode(ExprNode firstExpression, ExprNode secondExpression, ExprNode thirdExpression, ExprNode lastExpression, int line, int column) {
        super(line, column);
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.thirdExpression = thirdExpression;
        this.lastExpression = lastExpression;
    }

    public ExprNode getFirstExpression() {
        return firstExpression;
    }

    public ExprNode getSecondExpression() {
        return secondExpression;
    }

    public ExprNode getThirdExpression() {
        return thirdExpression;
    }
    public ExprNode getLastExpression() {
        return lastExpression;
    }

    @Override
    public String toString() {
        return "Rotate e1 by e2 around e3";
    }
}
