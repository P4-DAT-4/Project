package afs.astbuilder.nodes.stmt;

import afs.astbuilder.nodes.expr.ExprNode;

public class StmtWhileNode extends StmtNode {
    private final ExprNode expression;
    private final StmtNode statement;

    public StmtWhileNode(ExprNode expression, StmtNode statement) {
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
