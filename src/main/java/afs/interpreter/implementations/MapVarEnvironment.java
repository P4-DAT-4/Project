package afs.interpreter.implementations;
import afs.interpreter.interfaces.VarEnvironment;

import java.util.HashMap;
import java.util.Map;

public class MapVarEnvironment implements VarEnvironment {
    private final Map<String, Integer> _environment;
    private final MapVarEnvironment parent;

    public MapVarEnvironment() {
        this._environment = new HashMap<>();
        this.parent = null;
    }

    public MapVarEnvironment(MapVarEnvironment envV) {
        this._environment = new HashMap<>(envV._environment);
        this.parent = envV;
    }

    @Override
    public void declare(String ident, int location) {
        _environment.put(ident, location);
    }

    @Override
    public int lookup(String ident) {
        // Check if the variable is declared in the current scope
        if (_environment.containsKey(ident)) {
            return _environment.get(ident);
        // If not found locally, recursively look up in the parent scope
        } else if (parent != null) {
            return parent.lookup(ident);
        } else {
            throw new RuntimeException("Variable '" + ident + "' not found in environment");
        }
    }

    @Override
    public VarEnvironment newScope() {
        return new MapVarEnvironment(this);
    }
}
