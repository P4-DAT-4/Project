package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprBoolNode extends ExprNode {
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprBoolNode(this);
    }
}
