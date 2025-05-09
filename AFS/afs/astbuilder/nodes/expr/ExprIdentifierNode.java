package afs.astbuilder.nodes.expr;

public class ExprIdentifierNode extends ExprNode {
    private final String identifier;

    public ExprIdentifierNode(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
