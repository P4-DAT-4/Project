package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprLessThanNode extends ExprNode{
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprLessThanNode(this);
    }
}
