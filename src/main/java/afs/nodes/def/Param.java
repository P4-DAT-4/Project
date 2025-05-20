package afs.nodes.def;

import afs.nodes.AbstractSyntaxNode;
import afs.nodes.type.TypeNode;

public final class Param extends AbstractSyntaxNode {
    private final TypeNode type;
    private final String identifier;

    public Param(TypeNode type, String identifier, int line, int column) {
        super(line, column);
        this.type = type;
        this.identifier = identifier;
    }

    public TypeNode getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public java.lang.String toString() {
        return "T x";
    }
}