package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprNotNode extends ExprNode {
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprNotNode(this);
    }
}
