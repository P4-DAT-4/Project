package afs.nodes.event;

import afs.nodes.expr.ExprNode;

import java.util.List;

public final class EventDeclarationNode extends EventNode {
    private final String ident;
    private final String funIdent;
    private final List<ExprNode> arguments;

    public EventDeclarationNode(String ident, String identifier, List<ExprNode> arguments, int line, int column) {
        super(line, column);
        this.ident = ident;
        this.funIdent = identifier;
        this.arguments = arguments;
    }

    public String getIdent() {
        return ident;
    }

    public String getFunIdent() {
        return funIdent;
    }

    public List<ExprNode> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "e do x(arrow(e))";
    }
}
