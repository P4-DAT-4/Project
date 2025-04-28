package afs.astbuilder.nodes.expr;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.visitor.ExprVisitor;

public class ExprTextNode extends ExprNode {
    private final ExprNode expression;

    public ExprTextNode(ExprNode expression) {
        this.expression = expression;
    }

    public ExprNode getExpression() {
        return expression;
    }

    @Override
    public AFSType acceptVisit(ExprVisitor visitor) {
        return visitor.visitExprTextNode(this);
    }

    @Override
    public String toString() {
        return "text e";
    }
}
