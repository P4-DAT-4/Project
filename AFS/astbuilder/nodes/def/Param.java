package astbuilder.nodes.def;

import astbuilder.nodes.type.TypeNode;

public class Param {
    private final TypeNode type;
    private final String identifier;

    public Param(TypeNode type, String identifier) {
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
    public String toString() {
        return String.format("(%s %s)", type, identifier);
    }
}