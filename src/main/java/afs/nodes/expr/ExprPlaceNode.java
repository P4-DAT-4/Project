package afs.nodes.expr;

public final class ExprPlaceNode extends ExprNode {
    private final ExprNode firstExpression;
    private final ExprNode secondExpression;
    private final ExprNode thirdExpression;


    public ExprPlaceNode(ExprNode firstExpression, ExprNode secondExpression, ExprNode thirdExpression, int line, int column) {
        super(line, column);
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.thirdExpression = thirdExpression;

    }

    public ExprNode getFirstExpression() {
        return firstExpression;
    }

    public ExprNode getSecondExpression() { return secondExpression; }

    public ExprNode getThirdExpression() {
        return thirdExpression;
    }



    @Override
    public String toString() {
        return "Place e1 at e2";
    }
}
