package afs.nodes.def;

import afs.nodes.expr.ExprNode;
import afs.nodes.type.TypeNode;

public final class DefDeclarationNode extends DefNode {
    private final TypeNode type;
    private final String identifier;
    private final ExprNode expression;

    public DefDeclarationNode(TypeNode type, String identifier, ExprNode expression, int line, int column) {
        super(line, column);
        this.type = type;
        this.identifier = identifier;
        this.expression = expression;
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

    @Override
    public java.lang.String toString() {
        return "T x = e";
    }
}

