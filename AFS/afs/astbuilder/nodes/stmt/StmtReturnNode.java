package afs.astbuilder.nodes.stmt;

import afs.astbuilder.nodes.expr.ExprNode;

public class StmtReturnNode extends StmtNode {
    private final ExprNode expression;

    public StmtReturnNode(ExprNode expression) {
        this.expression = expression;
    }

    public ExprNode getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "Return e";
    }
}
