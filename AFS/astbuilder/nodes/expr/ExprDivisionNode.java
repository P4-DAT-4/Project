package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprDivisionNode extends ExprNode{
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprDivisionNode(this);
    }
}
