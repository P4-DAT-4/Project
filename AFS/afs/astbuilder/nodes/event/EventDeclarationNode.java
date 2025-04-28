package afs.astbuilder.nodes.event;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.nodes.expr.ExprFunctionCallNode;
import afs.astbuilder.nodes.expr.ExprNode;
import afs.astbuilder.visitor.EventVisitor;

public class EventDeclarationNode extends EventNode {
    private final ExprNode expression;
    private final ExprFunctionCallNode functionCall;

    public EventDeclarationNode(ExprNode expression, ExprFunctionCallNode functionCall) {
        this.expression = expression;
        this.functionCall = functionCall;
    }

    public ExprNode getExpression() {
        return expression;
    }

    public ExprFunctionCallNode getFunctionCall() {
        return functionCall;
    }

    @Override
    public AFSType acceptVisit(EventVisitor visitor) {
        return visitor.visitEventDeclarationNode(this);
    }

    @Override
    public String toString() {
        return "e do x(arrow(e))";
    }
}
