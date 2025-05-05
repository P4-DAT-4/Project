package afs.nodes.def;

import afs.nodes.event.EventNode;
import afs.nodes.expr.ExprFunctionCallNode;

public final class DefVisualizeNode extends DefNode {
    private final ExprFunctionCallNode functionCall;
    private final EventNode event;

    public DefVisualizeNode(ExprFunctionCallNode functionCall, EventNode event, int line, int column) {
        super(line, column);
        this.functionCall = functionCall;
       this.event = event;
    }

    public ExprFunctionCallNode getFunctionCall() {
        return functionCall;
    }

    public EventNode getEvent() {
        return event;
    }

    @Override
    public String toString() {
        return "visualize x(arrow(e))E";
    }
}
