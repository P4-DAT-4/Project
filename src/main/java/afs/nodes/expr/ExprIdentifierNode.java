package afs.nodes.expr;

public class ExprIdentifierNode extends ExprNode {
    private final String identifier;

    public ExprIdentifierNode(String identifier, int line, int column) {
        super(line, column);
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return identifier;
    }
}
