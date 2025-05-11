package afs.nodes.stmt;

import afs.nodes.expr.ExprNode;

import java.util.List;

public final class StmtListAssignmentNode extends StmtNode{
    private final String identifier;
    private final List<ExprNode> expressions;
    private final ExprNode expression;

    public StmtListAssignmentNode(String identifier, List<ExprNode> expressions, ExprNode expression, int lineNumber, int columnNumber) {
        super(lineNumber, columnNumber);
        this.identifier = identifier;
        this.expressions = expressions;
        this.expression = expression;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<ExprNode> getExpressions() {
        return expressions;
    }

    public ExprNode getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "x[arrow(e)] = e;";
    }
}
