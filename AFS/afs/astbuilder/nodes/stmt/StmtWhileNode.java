package afs.astbuilder.nodes.stmt;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.nodes.expr.ExprNode;
import afs.astbuilder.visitor.StmtVisitor;

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
    public AFSType acceptVisit(StmtVisitor visitor) {
        return visitor.visitStmtWhileNode(this);
    }

    @Override
    public String toString() {
        return "While e do S";
    }
}
