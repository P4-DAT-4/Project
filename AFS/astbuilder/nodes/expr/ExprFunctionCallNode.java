package astbuilder.nodes.expr;

import astbuilder.visitor.ExprVisitor;

public class ExprFunctionCallNode extends ExprNode {
    @Override
    public void acceptVisit(ExprVisitor visitor) {
        visitor.visitExprFunctionCallNode(this);
    }
}
