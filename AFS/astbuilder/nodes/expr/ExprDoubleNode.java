package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprDoubleNode extends ExprNode {
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprDoubleNode(this);
    }
}
