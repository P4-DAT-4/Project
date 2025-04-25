package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprListNode extends ExprNode{
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprListNode(this);
    }
}
