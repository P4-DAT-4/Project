package afs.interpreter;

import afs.nodes.expr.ExprFunctionCallNode;
import afs.nodes.expr.ExprNode;

public interface EventEnvironment {
    void declare(ExprNode expr, ExprFunctionCallNode call);
    ExprFunctionCallNode lookup(ExprNode expr);
}
