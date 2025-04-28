package afs.astbuilder.nodes.def;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.nodes.AbstractSyntaxNode;
import afs.astbuilder.nodes.expr.ExprIdentifierNode;
import afs.astbuilder.nodes.type.TypeNode;
import afs.astbuilder.visitor.ParamVisitor;

public class Param extends AbstractSyntaxNode<ParamVisitor> {
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
    public AFSType acceptVisit(ParamVisitor visitor) {
        return visitor.visitParam(this);
    }

    @Override
    public String toString() {
        return "T x";
    }
}