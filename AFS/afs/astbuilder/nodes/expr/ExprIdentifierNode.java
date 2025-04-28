package afs.astbuilder.nodes.expr;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.visitor.ExprVisitor;

public class ExprIdentifierNode extends ExprNode {
    private final String identifier;

    public ExprIdentifierNode(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public AFSType acceptVisit(ExprVisitor visitor) {
        return visitor.visitExprIdentifierNode(this);
    }

    @Override
    public String toString() {
        return identifier;
    }
}
