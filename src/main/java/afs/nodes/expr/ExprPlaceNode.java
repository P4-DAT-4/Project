package afs.nodes.expr;

public final class ExprPlaceNode extends ExprNode {
    private final ExprNode fExpression;
    private final ExprNode sExpression;
    private final ExprNode tExpression;


    public ExprPlaceNode(ExprNode fExpression, ExprNode sExpression, ExprNode tExpression,int line, int column) {
        super(line, column);
        this.fExpression = fExpression;
        this.sExpression = sExpression;
        this.tExpression = tExpression;

    }

    public ExprNode getFExpression() {
        return fExpression;
    }

    public ExprNode getSExpression() { return sExpression; }

    public ExprNode getTExpression() {
        return tExpression;
    }



    @Override
    public String toString() {
        return "Place e1 at e2";
    }
}
