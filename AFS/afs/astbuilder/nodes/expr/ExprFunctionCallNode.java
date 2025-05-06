package afs.astbuilder.nodes.expr;

import java.util.List;

public class ExprFunctionCallNode extends ExprNode {
    private final String identifier;
    private final List<ExprNode> arguments;

    public ExprFunctionCallNode(String identifier, List<ExprNode> arguments) {
        this.identifier = identifier;
        this.arguments = arguments;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<ExprNode> getArguments() {
        return arguments;
    }
}
