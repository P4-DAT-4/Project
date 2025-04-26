package afs.astbuilder.nodes.stmt;

import afs.astbuilder.nodes.expr.ExprNode;
import afs.astbuilder.visitor.StmtVisitor;

public class StmtAssignmentNode extends StmtNode {
    private final String identifier;
    private final ExprNode expression;

    public StmtAssignmentNode(String identifier, ExprNode expression)
    {
        this.identifier = identifier;
        this.expression = expression;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ExprNode getExpression() {
        return expression;
    }

    @Override
    public void acceptVisit(StmtVisitor visitor) {
        visitor.visitStmtAssignmentNode(this);
    }

    @Override
    public String toString() {
        return String.format("StmtAssignment (%s = %s)", identifier, expression);
    }
}
