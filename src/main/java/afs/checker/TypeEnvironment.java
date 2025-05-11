package afs.checker;

import afs.checker.exceptions.TypeCheckException;
import afs.checker.types.AFSType;
import afs.checker.types.FunctionType;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Deque;

public class TypeEnvironment {
    private final static Deque<Map<String, AFSType>> envVar = new ArrayDeque<>();
    private final static Map<String, FunctionType> envFun = new HashMap<>();
    private static String currentFunction = null;

    static {
        envVar.push(new HashMap<>());
    }

    private TypeEnvironment() {}

    private static class Holder {
        private static final TypeEnvironment INSTANCE = new TypeEnvironment();
    }

    public static TypeEnvironment getInstance() {
        return Holder.INSTANCE;
    }

    public static String getCurrentFunction() {
        return currentFunction;
    }

    public static void enterFunction(String identifier) {
        currentFunction = identifier;
        enterScope();
    }

    public static void exitFunction() {
        exitScope();
        currentFunction = null;
    }

    public static void enterScope() {
        var currentEnv = envVar.peek();
        envVar.push(currentEnv); // tilfoej kopi af det oeverste environment
    }

    public static void exitScope() {
        envVar.pop();
    }

    public static AFSType lookupVar(String identifier) {
        for (Map<String, AFSType> env : envVar) {
            if (env.containsKey(identifier)) {
                return env.get(identifier);
            }
        }
        throw new TypeCheckException("Undefined variable '" + identifier + "'");
    }

    public static void declareVar(String identifier, AFSType type) {
        var currentEnv = envVar.peek(); // hent det nuværende environment
        assert currentEnv != null; // Tjek at env findes

        currentEnv.put(identifier, type); // tilføj ny identifier til det nuværende environment
    }

    public static void declareFun(String identifier, FunctionType type) {
        if (envFun.containsKey(identifier)) {
            throw new TypeCheckException(String.format("Function '%s' has already been declared", identifier));
        }
        envFun.put(identifier, type);
    }

    public static FunctionType lookupFun(String identifier) {
        if (!envFun.containsKey(identifier)) {
            throw new TypeCheckException(String.format("Function '%s' has not been declared", identifier));
        } else {
            return envFun.get(identifier);
        }
    }


}
