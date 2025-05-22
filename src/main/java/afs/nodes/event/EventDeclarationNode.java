package afs.nodes.event;

import afs.nodes.expr.ExprNode;

import java.util.List;

public final class EventDeclarationNode extends EventNode {
    private final String leftIdentifier;
    private final String rightIdentifier;
    private final List<ExprNode> arguments;

    public EventDeclarationNode(String leftIdentifier, String rightIdentifier, List<ExprNode> arguments, int line, int column) {
        super(line, column);
        this.leftIdentifier = leftIdentifier;
        this.rightIdentifier = rightIdentifier;
        this.arguments = arguments;
    }

    public String getLeftIdentifier() {
        return leftIdentifier;
    }

    public String getRightIdentifier() {
        return rightIdentifier;
    }

    public List<ExprNode> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "e do x(arrow(e))";
    }
}
