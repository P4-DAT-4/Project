package afs.astbuilder.nodes.def;

import afs.astbuilder.nodes.event.EventNode;
import afs.astbuilder.nodes.expr.ExprFunctionCallNode;

public class DefVisualizeNode extends DefNode {
    private final ExprFunctionCallNode functionCall;
    private final EventNode event;

    public DefVisualizeNode(ExprFunctionCallNode functionCall, EventNode event) {
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
