package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprNegationNode extends ExprNode {
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprNegationNode(this);
    }
}
