package afs.nodes.def;

import afs.nodes.AbstractSyntaxNode;
import afs.nodes.expr.ExprIdentifierNode;
import afs.nodes.type.TypeNode;

public final class Param extends AbstractSyntaxNode {
    private final TypeNode type;
    private final ExprIdentifierNode identifier;

    public Param(TypeNode type, ExprIdentifierNode identifier, int line, int column) {
        super(line, column);
        this.type = type;
        this.identifier = identifier;
    }

    public TypeNode getType() {
        return type;
    }

    public ExprIdentifierNode getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "T x";
    }
}