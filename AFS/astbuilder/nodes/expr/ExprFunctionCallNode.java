package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

import java.util.List;

public class ExprFunctionCallNode extends ExprNode {
    private final String identifier;
    private final List<ExprNode> expressions;

    public ExprFunctionCallNode(String identifier, List<ExprNode> expressions) {
        this.identifier = identifier;
        this.expressions = expressions;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<ExprNode> getExpressions() {
        return expressions;
    }

    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprFunctionCallNode(this);
    }
}
