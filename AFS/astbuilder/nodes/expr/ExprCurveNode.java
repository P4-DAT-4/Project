package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprCurveNode extends ExprNode {
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprCurveNode(this);
    }
}
