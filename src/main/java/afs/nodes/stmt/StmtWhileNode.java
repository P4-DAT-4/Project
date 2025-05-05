package afs.nodes.stmt;

import afs.nodes.expr.ExprNode;

public final class StmtWhileNode extends StmtNode {
    private final ExprNode expression;
    private final StmtNode statement;

    public StmtWhileNode(ExprNode expression, StmtNode statement, int line, int column) {
        super(line, column);
        this.expression = expression;
        this.statement = statement;
    }

    public ExprNode getExpression() {
        return expression;
    }

    public StmtNode getStatement() {
        return statement;
    }

    @Override
    public String toString() {
        return "While e do S";
    }
}
