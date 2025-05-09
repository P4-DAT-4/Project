package afs.semantic_analysis;

import afs.semantic_analysis.exceptions.DeclarationException;
import afs.semantic_analysis.exceptions.LookupException;
import afs.semantic_analysis.types.AFSType;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class TypeEnvironment implements ITypeEnvironment {
    private final Map<String, AFSType> globalEnv = new HashMap<>();
    private final Deque<Map<String, AFSType>> localEnvStack = new ArrayDeque<>();
    private final Map<String, AFSType> functionEnv = new HashMap<>();

    @Override
    public void enterScope() {
        localEnvStack.push(globalEnv);
    }

    @Override
    public void exitScope() {
        localEnvStack.pop();
    }

    @Override
    public void declareVariable(String identifier, AFSType type) throws DeclarationException {
        boolean emptyLocalEnvStack = localEnvStack.isEmpty();
        if (emptyLocalEnvStack) {
            boolean canDeclareGlobal = globalEnv.containsKey(identifier);
            if (!canDeclareGlobal) {
                String message = String.format("Global variable '%s' is already declared", identifier);
                throw new DeclarationException(message);
            }
            globalEnv.put(identifier, type);
        } else {
            Map<String, AFSType> currentScope = localEnvStack.peek();
            boolean canDeclareLocal = globalEnv.containsKey(identifier);
            if (!canDeclareLocal) {
                String message = String.format("Local variable '%s' is already declared", identifier);
                throw new DeclarationException(message);
            }
            currentScope.put(identifier, type);
        }
    }

    @Override
    public AFSType lookupVariable(String identifier) throws LookupException {
        for (Map<String, AFSType> env : localEnvStack) {
            boolean found = env.containsKey(identifier);
            if (found) {
                return env.get(identifier);
            }
        }
        boolean declaredInGlobal = globalEnv.containsKey(identifier);
        if (!declaredInGlobal) {
            String message = String.format("Variable '%s' undeclared", identifier);
            throw new LookupException(message);
        }

        return globalEnv.get(identifier);
    }

    @Override
    public void declareFunction(String identifier, AFSType type) throws DeclarationException {
        boolean canDeclare = functionEnv.containsKey(identifier);
        if (!canDeclare) {
            String message = String.format("Function '%s' is already declared", identifier);
            throw new DeclarationException(message);
        }
        functionEnv.put(identifier, type);
    }

    @Override
    public AFSType lookupFunction(String identifier) throws LookupException {
        boolean found = functionEnv.containsKey(identifier);
        if (!found) {
            String message = String.format("Function '%s' undeclared", identifier);
            throw new LookupException(message);
        }
        return functionEnv.get(identifier);
    }
}
