package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprScaleNode extends ExprNode {
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprScaleNode(this);
    }
}
