package astbuilder.nodes.stmt;

import astbuilder.nodes.expr.ExprNode;
import astbuilder.visitor.StmtVisitor;

public class StmtReturnNode extends StmtNode {
    private final ExprNode expression;

    public StmtReturnNode(ExprNode expression) {
        this.expression = expression;
    }

    public ExprNode getExpression() {
        return expression;
    }

    @Override
    public void acceptVisit(StmtVisitor visitor) {
        visitor.visitStmtReturnNode(this);
    }

    @Override
    public String toString() {
        return String.format("StmtReturn (%s)", expression);
    }
}
