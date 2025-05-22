package afs.interpreter.interfaces;

import afs.nodes.expr.ExprFunctionCallNode;
import afs.nodes.expr.ExprNode;

public interface EventEnvironment {
   void declare(ExprNode expr, ExprFunctionCallNode call);
    //void declare(String ident, ExprFunctionCallNode call);
    ExprFunctionCallNode lookup(ExprNode expr);
}
