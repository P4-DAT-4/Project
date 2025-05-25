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
        System.out.println("Created new MapVarEnvironment: " + this + " with bindings: " + _environment);

    }

    public MapVarEnvironment(MapVarEnvironment envV) {
        this._environment = new HashMap<>(envV._environment);
        this.parent = envV;
        System.out.println("Created new MapVarEnvironment: " + this + " with bindings: " + _environment + ", parent: " + parent);
    }

    @Override
    public void declare(String ident, int location) {
        System.out.println("Before declare: " + ident + " at location " + location + " in env " + this + ", bindings: " + _environment);
        _environment.put(ident, location);
        System.out.println("After declare: " + ident + " at location " + location + ", bindings: " + _environment);
    }

    @Override
    public int lookup(String ident) {
        System.out.println("Looking up " + ident + " in env " + this + ", bindings: " + _environment);
        // Check if the variable is declared in the current scope
        if (_environment.containsKey(ident)) {
            int location = _environment.get(ident);
            System.out.println("Found " + ident + " at location " + location);

            return location;
        // If not found locally, recursively look up in the parent scope
        } else if (parent != null) {
            System.out.println("Not found in current env, checking parent: " + parent);
            return parent.lookup(ident);
        } else {
            throw new RuntimeException("Variable '" + ident + "' not found in environment");
        }
    }

    @Override
    public VarEnvironment newScope() {
        System.out.println("Creating new scope from env " + this + ", bindings: " + _environment);
        return new MapVarEnvironment(this);
    }
}
