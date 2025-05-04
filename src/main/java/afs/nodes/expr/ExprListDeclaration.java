package afs.nodes.expr;

import java.util.List;

public class ExprListDeclaration extends ExprNode {
    private final List<ExprNode> expressions;

    public ExprListDeclaration(List<ExprNode> expressions, int line, int column) {
        super(line, column);
        this.expressions = expressions;
    }

    public List<ExprNode> getExpressions() {
        return expressions;
    }

    @Override
    public String toString() {
        return "[arrow(e)]";
    }
}
