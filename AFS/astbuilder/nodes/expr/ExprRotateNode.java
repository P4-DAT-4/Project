package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprRotateNode extends ExprNode {
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprRotateNode(this);
    }
}
