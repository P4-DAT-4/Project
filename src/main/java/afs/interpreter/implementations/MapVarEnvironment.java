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
        if (_environment.containsKey(ident)) {
            throw new RuntimeException("Variable '" + ident + "' already declared");
        }

        _environment.put(ident, location);
    }

//    @Override
//    public int lookup(String ident) {
//        if (!_environment.containsKey(ident)) {
//            throw new RuntimeException("Variable '" + ident + "' not found in VarEnvironment");
//        }
//        return _environment.get(ident);
//
//    }

    @Override
    public int lookup(String ident) {
        if (_environment.containsKey(ident)) {
            System.out.println("Lookup: Variable '" + ident + "' found in current scope with location " + _environment.get(ident));
            return _environment.get(ident);
        } else if (parent != null) {
            System.out.println("Lookup: Variable '" + ident + "' not found in current scope, checking parent scope...");
            return parent.lookup(ident);
        } else {
            System.out.println("Lookup: Variable '" + ident + "' not found in any scope!");
            throw new RuntimeException("Variable '" + ident + "' not found in environment");
        }
    }

    @Override
    public void set(String ident, int location) {
        if (_environment.containsKey(ident)) {
            System.out.println("Set: Variable '" + ident + "' found in current scope. Updating location to " + location);
            _environment.put(ident, location);
        } else if (parent != null) {
            System.out.println("Set: Variable '" + ident + "' not found in current scope, setting in parent scope...");
            parent.set(ident, location);
        } else {
            System.out.println("Set: Cannot set undeclared variable '" + ident + "'");
            throw new RuntimeException("Cannot set undeclared variable '" + ident + "'");
        }
    }


//    @Override
//    public int lookup(String ident) {
//        // Check if the variable is declared in the current scope
//        if (_environment.containsKey(ident)) {
//
//            return _environment.get(ident);
//        // If not found locally, recursively look up in the parent scope
//        } else if (parent != null) {
//            return parent.lookup(ident);
//        } else {
//            throw new RuntimeException("Variable '" + ident + "' not found in environment");
//        }
//    }
//
//    @Override
//    public void set(String ident, int location) {
//    // Check if the variable is declared in the current scope
//        if (_environment.containsKey(ident)) {
//        // Update the variable's location in the current scope
//            _environment.put(ident, location);
//        } else if (parent != null) {
//            // If not found locally, recursively try to set in the parent scope
//            parent.set(ident, location);
//        } else {
//            throw new RuntimeException("Cannot set undeclared variable '" + ident + "'");
//        }
//    }

    @Override
    public boolean isLocal(String ident) {
        return _environment.containsKey(ident);
    }

    @Override
    public VarEnvironment newScope() {
        // Creates a new nested scope with the current scope as its parent
        return new MapVarEnvironment(this);
    }



}
