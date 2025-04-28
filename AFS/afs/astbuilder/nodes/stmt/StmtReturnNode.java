package afs.astbuilder.nodes.stmt;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.nodes.expr.ExprNode;
import afs.astbuilder.visitor.StmtVisitor;

public class StmtReturnNode extends StmtNode {
    private final ExprNode expression;

    public StmtReturnNode(ExprNode expression) {
        this.expression = expression;
    }

    public ExprNode getExpression() {
        return expression;
    }

    @Override
    public AFSType acceptVisit(StmtVisitor visitor) {
        return visitor.visitStmtReturnNode(this);
    }

    @Override
    public String toString() {
        return "Return e";
    }
}
