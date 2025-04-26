package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

import java.util.List;
import java.util.stream.Collectors;

public class ExprLineNode extends ExprNode {
    private final List<ExprNode> expressions;

    public ExprLineNode(List<ExprNode> expressions) {
        this.expressions = expressions;
    }

    public List<ExprNode> getExpressions() {
        return expressions;
    }

    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprLineNode(this);
    }

    @Override
    public String toString() {
        return String.format("ExprLine (%s)", expressions.stream().map(ExprNode::toString).collect(Collectors.joining(",")));
    }
}
