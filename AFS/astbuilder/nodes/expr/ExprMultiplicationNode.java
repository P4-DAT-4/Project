package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprMultiplicationNode extends ExprNode{
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprMultiplicationNode(this);
    }
}
