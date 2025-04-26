package afs.astbuilder.nodes.def;

import afs.astbuilder.nodes.expr.ExprNode;
import afs.astbuilder.nodes.type.TypeNode;
import afs.astbuilder.visitor.DefVisitor;

public class DefDeclarationNode extends DefNode {
    private final TypeNode type;
    private final String identifier;
    private final ExprNode expression;

    public DefDeclarationNode(TypeNode type, String identifier, ExprNode expression) {
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
    public void acceptVisit(DefVisitor visitor) {
        visitor.visitDefDeclarationNode(this);
    }

    @Override
    public String toString() {
        return String.format("DefDeclaration (%s %s = %s)", type, identifier, expression);
    }
}
