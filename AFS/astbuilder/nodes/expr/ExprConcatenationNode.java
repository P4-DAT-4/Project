package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprConcatenationNode extends ExprNode {
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprConcatenationNode(this);
    }
}
