package afs.nodes.event;

import afs.nodes.expr.ExprFunctionCallNode;
import afs.nodes.expr.ExprNode;

public class EventDeclarationNode extends EventNode {
    private final ExprNode expression;
    private final ExprFunctionCallNode functionCall;

    public EventDeclarationNode(ExprNode expression, ExprFunctionCallNode functionCall, int line, int column) {
        super(line, column);
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
    public String toString() {
        return "e do x(arrow(e))";
    }
}
