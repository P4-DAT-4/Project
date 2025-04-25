package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprSubtractionNode extends ExprNode{
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprSubtractionNode(this);
    }
}
