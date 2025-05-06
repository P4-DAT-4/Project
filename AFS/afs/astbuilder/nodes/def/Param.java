package afs.astbuilder.nodes.def;

import afs.astbuilder.nodes.AbstractSyntaxNode;
import afs.astbuilder.nodes.expr.ExprIdentifierNode;
import afs.astbuilder.nodes.type.TypeNode;

public class Param extends AbstractSyntaxNode {
    private final TypeNode type;
    private final ExprIdentifierNode identifier;

    public Param(TypeNode type, ExprIdentifierNode identifier) {
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