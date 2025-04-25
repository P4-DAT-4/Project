package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprStringNode extends ExprNode {
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprStringNode(this);
    }
}
