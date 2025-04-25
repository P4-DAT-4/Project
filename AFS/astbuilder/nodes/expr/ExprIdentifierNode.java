package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprIdentifierNode extends ExprNode {
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprIdentifierNode(this);
    }
}
