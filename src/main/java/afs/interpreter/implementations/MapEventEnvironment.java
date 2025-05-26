package afs.interpreter.implementations;

import afs.interpreter.interfaces.EventEnvironment;
import afs.nodes.expr.ExprFunctionCallNode;

import java.util.HashMap;
import java.util.Map;

public class MapEventEnvironment implements EventEnvironment {
    private final Map<String, ExprFunctionCallNode> _environment;

    public MapEventEnvironment() {
        _environment = new HashMap<>();
    }

    @Override
    public void declare(String ident, ExprFunctionCallNode call) {
        _environment.put(ident, call);
    }

    @Override
    public ExprFunctionCallNode lookup(String ident) {
        boolean found = _environment.containsKey(ident);
        if (!found) {
            throw new RuntimeException(String.format("Event '%s' not found in EventEnvironment", ident));
        }
        return _environment.get(ident);
    }

    @Override
    public boolean check(String ident) {
        return _environment.containsKey(ident);
    }
}
