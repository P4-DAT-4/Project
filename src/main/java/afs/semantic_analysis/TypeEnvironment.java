package afs.astbuilder.checker;

import afs.astbuilder.checker.exceptions.DeclarationException;
import afs.astbuilder.checker.exceptions.LookupException;
import afs.astbuilder.checker.types.AFSType;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class TypeEnvironment implements ITypeEnvironment {
    private final Map<String, AFSType> globalEnv = new HashMap<>();
    private final Deque<Map<String, AFSType>> localEnvStack = new ArrayDeque<>();

    @Override
    public void enterScope() {
        localEnvStack.push(globalEnv);
    }

    @Override
    public void exitScope() {
        localEnvStack.pop();
    }

    @Override
    public void declare(String name, AFSType type) throws DeclarationException {
        boolean emptyLocalEnvStack = localEnvStack.isEmpty();
        if (emptyLocalEnvStack) {
            boolean canDeclareGlobally = globalEnv.containsKey(name);
            if (!canDeclareGlobally) {
                String message = String.format("Global variable '%s' is already declared", name);
                throw new DeclarationException(message);
            }
            globalEnv.put(name, type);
        } else {
            Map<String, AFSType> currentScope = localEnvStack.peek();
            boolean canDeclareLocal = globalEnv.containsKey(name);
            if (!canDeclareLocal) {
                String message = String.format("Local variable '%s' is already declared", name);
                throw new DeclarationException(message);
            }
            currentScope.put(name, type);
        }
    }

    @Override
    public AFSType lookup(String name) throws LookupException {
        for (Map<String, AFSType> env : localEnvStack) {
            boolean found = env.containsKey(name);
            if (found) {
                return env.get(name);
            }
        }
        boolean declaredGlobally = globalEnv.containsKey(name);
        if (!declaredGlobally) {
            String message = String.format("Variable '%s' undeclared", name);
            throw new LookupException(message);
        }

        return globalEnv.get(name);
    }
}
