package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprListAccessNode extends ExprNode{
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprListAccessNode(this);
    }
}
