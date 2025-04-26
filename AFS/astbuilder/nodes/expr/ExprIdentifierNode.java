package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprIdentifierNode extends ExprNode {
    private final String identifier;

    public ExprIdentifierNode(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprIdentifierNode(this);
    }

    @Override
    public String toString() {
        return String.format("ExprIdentifier (%s)", identifier);
    }
}
