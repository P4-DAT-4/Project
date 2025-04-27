package afs.astbuilder.nodes.stmt;

import afs.astbuilder.nodes.expr.ExprNode;
import afs.astbuilder.nodes.type.TypeNode;
import afs.astbuilder.visitor.StmtVisitor;

public class StmtDeclarationNode extends StmtNode {
    private final TypeNode type;
    private final String identifier;
    private final ExprNode expression;

    public StmtDeclarationNode(TypeNode type, String identifier, ExprNode expression) {
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
    public void acceptVisit(StmtVisitor visitor) {
        visitor.visitStmtDeclarationNode(this);
    }

    @Override
    public String toString() {
        return String.format("StmtDeclaration (%s %s = %s)", type, identifier, expression);
    }
}
