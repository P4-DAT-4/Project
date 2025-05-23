//package afs.interpreter.implementations;
//
//import afs.interpreter.VarEnvironment;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class ScopedVarEnvironment implements VarEnvironment {
//    // map hold variable binding in the current scope: variable name -> location
//    private final Map<String, Integer> bindings = new HashMap<>();
//    private final VarEnvironment parent;
//
//    // Constructor for the root environment (no parent scope)
//    public ScopedVarEnvironment() {
//        this.parent = null;
//    }
//
//    // Constructor for nested scope with a parent
//    private ScopedVarEnvironment(VarEnvironment parent) {
//        this.parent = parent;
//    }
//
//    // declare a new variable in the current scope
//    @Override
//    public void declare(String ident, int location) {
//        if(bindings.containsKey(ident)) {
//            throw new RuntimeException("Variable" + ident + " already defined in scope");
//
//        }
//        bindings.put(ident, location);
//    }
//
//    // look up the variable's location, by first checking the current scope, then recursively the parent scope
//    @Override
//    public int lookup(String ident) {
//        if(bindings.containsKey(ident)) {
//            return bindings.get(ident);
//        } else if (parent != null) {
//            return parent.lookup(ident);
//        } else {
//            throw new RuntimeException("Variable " + ident + " not defined in any scope");
//
//        }
//    }
//
//    // Check if the variable is defined in the current scope
//    @Override
//    public boolean isLocal(String ident) {
//        return bindings.containsKey(ident);
//    }
//
//    // Updates the value location of an exiting variable
//    @Override
//    public void set(String ident, int location) {
//        if(bindings.containsKey(ident)) {
//            bindings.put(ident, location);
//        }else if (parent != null) {
//            parent.set(ident, location);
//        } else {
//            throw new RuntimeException("Cannot set value for undeclared variable " + ident );
//        }
//    }
//
//    // Creates a new child scope
//    @Override
//    public VarEnvironment newScope() {
//        return new ScopedVarEnvironment(this);
//    }
//
//
//}
