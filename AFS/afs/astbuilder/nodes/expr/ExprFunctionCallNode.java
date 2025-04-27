package afs.astbuilder.nodes.expr;

import afs.astbuilder.visitor.ExprVisitor;

import java.util.List;
import java.util.stream.Collectors;

public class ExprFunctionCallNode extends ExprNode {
    private final ExprIdentifierNode identifier;
    private final List<ExprNode> expressions;

    public ExprFunctionCallNode(ExprIdentifierNode identifier, List<ExprNode> expressions) {
        this.identifier = identifier;
        this.expressions = expressions;
    }

    public ExprIdentifierNode getIdentifier() {
        return identifier;
    }

    public List<ExprNode> getExpressions() {
        return expressions;
    }

    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprFunctionCallNode(this);
    }

    @Override
    public String toString() {
        return "x(arrow(e))";
    }
}
