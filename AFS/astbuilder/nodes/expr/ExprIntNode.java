package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprIntNode extends ExprNode{
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprIntNode(this);
    }
}
