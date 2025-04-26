package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

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
        return String.format("ExprCurve (%s)", expressions.stream().map(ExprNode::toString).collect(Collectors.joining(",")));
    }
}
