package afs.nodes.stmt;

import afs.nodes.expr.ExprNode;

public final class StmtReturnNode extends StmtNode {
    private final ExprNode expression;

    public StmtReturnNode(ExprNode expression, int line, int column) {
        super(line, column);
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
