package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprAdditionNode extends ExprNode{
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprAdditionNode(this);
    }
}
