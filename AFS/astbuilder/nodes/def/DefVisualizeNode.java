package astbuilder.nodes.def;

import astbuilder.nodes.event.EventNode;
import astbuilder.nodes.expr.ExprFunctionCallNode;
import astbuilder.visitor.DefVisitor;

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
    public void acceptVisit(DefVisitor visitor) {
        visitor.visitDefVisualizeNode(this);
    }

    @Override
    public String toString() {
        return String.format("DefVisualize (%s %s)",
                functionCall,
                event
        );
    }
}
