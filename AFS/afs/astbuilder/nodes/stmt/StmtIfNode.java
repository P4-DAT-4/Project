package afs.astbuilder.nodes.stmt;

import afs.astbuilder.nodes.expr.ExprNode;

public class StmtIfNode extends StmtNode {
    private final ExprNode expression;
    private final StmtNode leftStatement;
    private final StmtNode rightStatement;

    public StmtIfNode(ExprNode expression, StmtNode leftStatement, StmtNode rightStatement) {
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
