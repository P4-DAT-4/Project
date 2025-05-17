package afs.nodes.def;

import afs.nodes.expr.ExprNode;
import afs.nodes.type.TypeNode;

public final class DefDeclarationNode extends DefNode {
    private final TypeNode type;
    private final String identifier;
    private final ExprNode expression;
    private final DefNode definition;

    public DefDeclarationNode(TypeNode type, String identifier, ExprNode expression, DefNode definition, int line, int column) {
        super(line, column);
        this.type = type;
        this.identifier = identifier;
        this.expression = expression;
        this.definition = definition;
    }

    public TypeNode getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ExprNode getExpression() {
        return expression;
    }

    public DefNode getDefinition() {
        return definition;
    }

    @Override
    public java.lang.String toString() {
        return "T x = e";
    }
}

