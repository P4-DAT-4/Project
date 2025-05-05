package afs.nodes.stmt;

import afs.nodes.expr.ExprFunctionCallNode;

public final class StmtFunctionCallNode extends StmtNode {
    private final ExprFunctionCallNode functionCall;

    public StmtFunctionCallNode(ExprFunctionCallNode functionCall, int line, int column) {
        super(line, column);
        this.functionCall = functionCall;
    }

    public ExprFunctionCallNode getFunctionCall() {
        return functionCall;
    }

    @Override
    public String toString() {
        return functionCall.toString();
    }
}
