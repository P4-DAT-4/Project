package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprLineNode extends ExprNode{
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprLineNode(this);
    }
}
