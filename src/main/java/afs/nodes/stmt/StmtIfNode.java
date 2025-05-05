package afs.nodes.stmt;

import afs.nodes.expr.ExprNode;

public final class StmtIfNode extends StmtNode {
    private final ExprNode expression;
    private final StmtNode leftStatement;
    private final StmtNode rightStatement;

    public StmtIfNode(ExprNode expression, StmtNode leftStatement, StmtNode rightStatement, int line, int column) {
        super(line, column);
        this.expression = expression;
        this.leftStatement = leftStatement;
        this.rightStatement = rightStatement;
    }

    public ExprNode getExpression() {
        return expression;
    }

    public StmtNode getLeftStatement() {
        return leftStatement;
    }

    public StmtNode getRightStatement() {
        return rightStatement;
    }

    @Override
    public String toString() {
        return "If e then S1 else S2";
    }
}
