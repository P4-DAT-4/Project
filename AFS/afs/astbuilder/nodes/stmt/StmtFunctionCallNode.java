package afs.astbuilder.nodes.stmt;

import afs.astbuilder.nodes.expr.ExprFunctionCallNode;
import afs.astbuilder.visitor.StmtVisitor;

public class StmtFunctionCallNode extends StmtNode {
    private final ExprFunctionCallNode functionCall;

    public StmtFunctionCallNode(ExprFunctionCallNode functionCall) {
        this.functionCall = functionCall;
    }

    public ExprFunctionCallNode getFunctionCall() {
        return functionCall;
    }

    @Override
    public void acceptVisit(StmtVisitor visitor) {
        visitor.visitStmtFunctionCallNode(this);
    }

    @Override
    public String toString() {
        return functionCall.toString();
    }
}
