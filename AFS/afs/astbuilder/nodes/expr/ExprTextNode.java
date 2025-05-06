package afs.astbuilder.nodes.expr;

public class ExprTextNode extends ExprNode {
    private final ExprNode expression;

    public ExprTextNode(ExprNode expression) {
        this.expression = expression;
    }

    public ExprNode getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "text e";
    }
}
