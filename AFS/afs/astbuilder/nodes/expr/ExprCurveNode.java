package afs.astbuilder.nodes.expr;

import afs.astbuilder.visitor.ExprVisitor;

import java.util.List;
import java.util.stream.Collectors;

public class ExprCurveNode extends ExprNode {
    private final List<ExprNode> expressions;

    public ExprCurveNode(List<ExprNode> expressions) {
        this.expressions = expressions;
    }

    public List<ExprNode> getExpressions() {
        return expressions;
    }

    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprCurveNode(this);
    }

    @Override
    public String toString() {
        return "curve [arrow(e)]";
    }
}
