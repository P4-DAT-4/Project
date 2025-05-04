package afs.nodes.expr;

public class ExprTextNode extends ExprNode {
    private final ExprNode expression;

    public ExprTextNode(ExprNode expression, int line, int column) {
        super(line, column);
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
