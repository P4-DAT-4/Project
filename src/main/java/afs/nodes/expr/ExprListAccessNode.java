package afs.nodes.expr;

import java.util.List;

public final class ExprListAccessNode extends ExprNode {
    private final String identifier;
    private final List<ExprNode> expressions;

    public ExprListAccessNode(String identifier, List<ExprNode> expressions, int line, int column) {
        super(line, column);
        this.identifier = identifier;
        this.expressions = expressions;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<ExprNode> getExpressions() {
        return expressions;
    }

    @Override
    public String toString() {
        return "x[arrow(e)]";
    }
}
