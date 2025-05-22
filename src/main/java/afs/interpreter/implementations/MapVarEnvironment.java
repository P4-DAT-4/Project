package afs.interpreter.implementations;
import afs.interpreter.interfaces.VarEnvironment;

import java.util.HashMap;
import java.util.Map;

public class MapVarEnvironment implements VarEnvironment {
    private final Map<String, Integer> _environment;

    public MapVarEnvironment() {
        _environment = new HashMap<>();
    }

    public MapVarEnvironment(MapVarEnvironment envV) {
        _environment = new HashMap<>(envV._environment);
    }

    @Override
    public void declare(String ident, int location) {
        _environment.put(ident, location);
    }

    @Override
    public int lookup(String ident) {
        boolean found = _environment.containsKey(ident);
        if (!found) {
            throw new RuntimeException(String.format("Variable '%s' not found in VarEnvironment", ident));
        }
        return _environment.get(ident);
    }



}
