package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprEqualsNode extends ExprNode {
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprEqualNode(this);
    }
}
