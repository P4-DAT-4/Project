package afs.nodes.stmt;

import afs.nodes.expr.ExprNode;

import java.util.List;

public final class StmtFunctionCallNode extends StmtNode {
    private final String identifier;
    private final List<ExprNode> arguments;

    public StmtFunctionCallNode(String identifier, List<ExprNode> arguments, int line, int column) {
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
    public String toString() {
        return "x(arrow(e))";
    }
}
