package afs.astbuilder.nodes.expr;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.visitor.ExprVisitor;

import java.util.List;

public class ExprCurveNode extends ExprNode {
    private final List<ExprNode> expressions;

    public ExprCurveNode(List<ExprNode> expressions) {
        this.expressions = expressions;
    }

    public List<ExprNode> getExpressions() {
        return expressions;
    }

    @Override
    public AFSType acceptVisit(ExprVisitor visitor) {
        return visitor.visitExprCurveNode(this);
    }

    @Override
    public String toString() {
        return "curve [arrow(e)]";
    }
}
