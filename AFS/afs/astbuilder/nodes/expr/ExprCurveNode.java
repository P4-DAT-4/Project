package afs.astbuilder.nodes.expr;

import java.util.List;

public class ExprCurveNode extends ExprNode {
    private final List<ExprNode> expressions;

    public ExprCurveNode(List<ExprNode> expressions) {
        this.expressions = expressions;
    }

    public List<ExprNode> getExpressions() {
        return expressions;
    }

    @Override
    public String toString() {
        return "curve [arrow(e)]";
    }
}