package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprAndNode extends ExprNode {
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprAndNode(this);
    }
}
