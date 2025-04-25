package astbuilder.nodes.def;

import astbuilder.nodes.event.EventNode;
import astbuilder.nodes.expr.ExprNode;
import astbuilder.visitor.DefVisitor;

import java.util.List;
import java.util.stream.Collectors;

public class DefVisualizeNode extends DefNode {
    private final String identifier;
    private final List<ExprNode> expressions;
    private final EventNode event;

    public DefVisualizeNode(String identifier, List<ExprNode> expressions, EventNode event) {
        this.identifier = identifier;
        this.expressions = expressions;
        this.event = event;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<ExprNode> getExpressions() {
        return expressions;
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
        return String.format("DefVisualize (%s (%s) %s)",
                identifier,
                expressions.stream().map(ExprNode::toString).collect(Collectors.joining(", ")),
                event
        );
    }
}
