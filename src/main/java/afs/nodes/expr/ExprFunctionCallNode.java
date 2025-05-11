package afs.nodes.expr;

import java.util.List;

public final class ExprFunctionCallNode extends ExprNode {
    private final String identifier;
    private final List<ExprNode> arguments;

    public ExprFunctionCallNode(String identifier, List<ExprNode> arguments, int line, int column) {
        super(line, column);
        this.identifier = identifier;
        this.arguments = arguments;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<ExprNode> getArguments() {
        return arguments;
    }

    @Override
    public java.lang.String toString() {
        return "x(arrow(e))";
    }
}
