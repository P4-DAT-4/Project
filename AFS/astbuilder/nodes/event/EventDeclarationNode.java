package astbuilder.nodes.event;

import astbuilder.nodes.expr.ExprFunctionCallNode;
import astbuilder.nodes.expr.ExprNode;
import astbuilder.visitor.EventVisitor;

import java.util.List;

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
    public void acceptVisit(EventVisitor visitor) {
        visitor.visitEventDeclarationNode(this);
    }
}
