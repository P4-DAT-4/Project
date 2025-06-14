package afs.nodes.expr;

import java.util.List;

public final class ExprLineNode extends ExprNode {
    private final List<ExprNode> expressions;

    public ExprLineNode(List<ExprNode> expressions, int line, int column) {
        super(line, column);
        this.expressions = expressions;
    }

    public List<ExprNode> getExpressions() {
        return expressions;
    }

    @Override
    public String toString() {
        return "line [arrow(e)]";
    }
}
