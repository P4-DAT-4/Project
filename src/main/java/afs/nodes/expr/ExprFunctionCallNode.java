package afs.nodes.expr;

import java.util.List;

public final class ExprFunctionCallNode extends ExprNode {
    private final ExprIdentifierNode identifier;
    private final List<ExprNode> arguments;

    public ExprFunctionCallNode(ExprIdentifierNode identifier, List<ExprNode> arguments, int line, int column) {
        super(line, column);
        this.identifier = identifier;
        this.arguments = arguments;
    }

    public ExprIdentifierNode getIdentifier() {
        return identifier;
    }

    public List<ExprNode> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "x(arrow(e))";
    }
}
