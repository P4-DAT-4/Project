package afs.nodes.event;

import afs.nodes.expr.ExprNode;

import java.util.List;

public final class EventDeclarationNode extends EventNode {
    private final ExprNode expression;
    private final String identifier;
    private final List<ExprNode> arguments;

    public EventDeclarationNode(ExprNode expression, String identifier, List<ExprNode> arguments, int line, int column) {
        super(line, column);
        this.expression = expression;
        this.identifier = identifier;
        this.arguments = arguments;
    }

    public ExprNode getExpression() {
        return expression;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<ExprNode> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "e do x(arrow(e))";
    }

}
