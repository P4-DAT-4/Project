package afs.nodes.def;

import afs.nodes.event.EventNode;
import afs.nodes.expr.ExprNode;

import java.util.List;
import java.util.stream.Collectors;

public final class DefVisualizeNode extends DefNode {
    private final String identifier;
    private final List<ExprNode> arguments;
    private final EventNode event;

    public DefVisualizeNode(String identifier, List<ExprNode> arguments, EventNode event, int line, int column) {
        super(line, column);
        this.identifier = identifier;
        this.arguments = arguments;
        this.event = event;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<ExprNode> getArguments() {
        return arguments;
    }

    public EventNode getEvent() {
        return event;
    }

    @Override
    public String toString() {
        String args = arguments.stream().map(ExprNode::toString).collect(Collectors.joining(", "));
        return String.format("visualize %s(%s)E", identifier, args);
    }
}
