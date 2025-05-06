package afs.astbuilder.nodes.expr;

import java.util.List;

public class ExprLineNode extends ExprNode {
    private final List<ExprNode> expressions;

    public ExprLineNode(List<ExprNode> expressions) {
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
