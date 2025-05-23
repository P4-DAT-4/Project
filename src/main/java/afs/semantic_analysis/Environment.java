package afs.semantic_analysis;

import afs.semantic_analysis.exceptions.TypeCheckException;
import afs.semantic_analysis.types.AFSType;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final Map<String, AFSType> environment;

    public Environment() {
        environment = new HashMap<>();
    }

    public Environment(Environment env) {
        environment = new HashMap<>(env.environment);
    }

    public AFSType lookup(String identifier) {
        if (environment.containsKey(identifier)) {
            return environment.get(identifier);
        }
        throw new TypeCheckException("Variable '" + identifier + "' not found");
    }

    public void declare(String identifier, AFSType type) {
        environment.put(identifier, type); // tilføj ny identifier til det nuværende environment
    }

    public Boolean check(String identifier) {
        return environment.containsKey(identifier);
    }
}