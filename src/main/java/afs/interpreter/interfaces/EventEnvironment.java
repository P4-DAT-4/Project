package afs.interpreter.interfaces;

import afs.nodes.expr.ExprFunctionCallNode;

public interface EventEnvironment {
    void declare(String ident, ExprFunctionCallNode call);
    ExprFunctionCallNode lookup(String ident);
    boolean check(String ident);
}
