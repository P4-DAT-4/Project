package afs.astbuilder.nodes.stmt;

import afs.astbuilder.nodes.expr.ExprIdentifierNode;
import afs.astbuilder.nodes.expr.ExprNode;
import afs.astbuilder.nodes.type.TypeNode;
import afs.astbuilder.visitor.StmtVisitor;

public class StmtDeclarationNode extends StmtNode {
    private final TypeNode type;
    private final ExprIdentifierNode identifier;
    private final ExprNode expression;

    public StmtDeclarationNode(TypeNode type, ExprIdentifierNode identifier, ExprNode expression) {
        this.type = type;
        this.identifier = identifier;
        this.expression = expression;
    }

    public TypeNode getType() {
        return type;
    }

    public ExprIdentifierNode getIdentifier() {
        return identifier;
    }

    public ExprNode getExpression() {
        if (expression == null) {
            System.out.println("Expression is null");
        }
        return expression;
    }

    @Override
    public void acceptVisit(StmtVisitor visitor) {
        visitor.visitStmtDeclarationNode(this);
    }

    @Override
    public String toString() {
        return "T x = e";
    }
}
