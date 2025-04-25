package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprPlaceNode extends ExprNode{
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprPlaceNode(this);
    }
}
