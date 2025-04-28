package afs.astbuilder.nodes.expr;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.visitor.ExprVisitor;

import java.util.List;

public class ExprListNode extends ExprNode {
    private final List<ExprNode> expressions;

    public ExprListNode(List<ExprNode> expressions) {
        this.expressions = expressions;
    }

    public List<ExprNode> getExpressions() {
        return expressions;
    }

    @Override
    public AFSType acceptVisit(ExprVisitor visitor) {
        return visitor.visitExprListNode(this);
    }

    @Override
    public String toString() {
        return "[arrow(e)]";
    }
}
