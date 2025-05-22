package afs.interpreter.implementations;

import afs.interpreter.interfaces.EventEnvironment;
import afs.nodes.expr.ExprFunctionCallNode;
import afs.nodes.expr.ExprNode;

import java.util.HashMap;
import java.util.Map;

public class MapEventEnvironment implements EventEnvironment {
    private final Map<ExprNode, ExprFunctionCallNode> _environment;

    public MapEventEnvironment() {
        _environment = new HashMap<>();
    }

    @Override
    public void declare(ExprNode expr, ExprFunctionCallNode call) {
        _environment.put(expr, call);
    }




    @Override
    public ExprFunctionCallNode lookup(ExprNode expr) {
        boolean found = _environment.containsKey(expr);
        if (!found) {
            throw new RuntimeException(String.format("Event '%s' not found in EventEnvironment", expr));
        }
        return _environment.get(expr);
    }
}
