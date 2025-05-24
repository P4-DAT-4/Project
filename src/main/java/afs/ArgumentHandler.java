package afs;

import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class ArgumentHandler {
    public enum ArgType {
        STRING,
        INT,
        BOOLEAN
    }
    private final List<Pair<String, ArgType>> argDefinitions = new ArrayList<>();
    private final Map<String, List<Pair<String, ArgType>>> flagDefinitions = new HashMap<>();
    private final Map<String, Object> parsedArgValues = new HashMap<>();
    private final Map<String, Map<String, Object>> parsedFlagValues = new HashMap<>();


    public void addRequiredArg(String name, ArgType type) {
        argDefinitions.add(new Pair<>(name, type));
    }

    public void addFlag(String flagName, List<Pair<String, ArgType>> params) {
        flagDefinitions.put(flagName, params);
    }

    public <T> T getArg(String name, Class<T> clazz) {
        return clazz.cast(parsedArgValues.get(name));
    }

    public <T> T getFlagValue(String flag, String param, Class<T> clazz) {
        Map<String, Object> values = parsedFlagValues.get(flag);
        if (values == null) return null;
        return clazz.cast(values.get(param));
    }

    public boolean parse(String[] args) {
        int expectedArgs = argDefinitions.size();

        if (args.length < expectedArgs) {
            System.out.println("Missing required arguments.");
            return false;
        }

        // Parse required arguments
        for (int i = 0; i < expectedArgs; i++) {
            Pair<String, ArgType> def = argDefinitions.get(i);
            String value = args[i];
            Object parsed = parseValue(def.getValue1(), value);
            if (parsed == null) {
                System.out.printf("Invalid value for argument <%s>: %s%n", def.getValue0(), value);
                return false;
            }
            parsedArgValues.put(def.getValue0(), parsed);
        }

        // Parse optional flags
        int i = expectedArgs;
        while (i < args.length) {
            String flag = args[i];

            List<Pair<String, ArgType>> paramDefs = flagDefinitions.get(flag);
            if (paramDefs == null) {
                System.out.println("Unknown flag: " + flag);
                return false;
            }

            if (i + paramDefs.size() >= args.length) {
                System.out.println("Missing value(s) for flag: " + flag);
                return false;
            }

            Map<String, Object> values = new HashMap<>();
            for (int j = 0; j < paramDefs.size(); j++) {
                String val = args[i + 1 + j];
                Pair<String, ArgType> param = paramDefs.get(j);
                Object parsed = parseValue(param.getValue1(), val);
                if (parsed == null) {
                    System.out.printf("Invalid value for flag %s parameter <%s>: %s%n - should be", flag, param.getValue0(), val);
                    return false;
                }
                values.put(param.getValue0(), parsed);
            }

            parsedFlagValues.put(flag, values);
            i += 1 + paramDefs.size();
        }

        return true;
    }

    private Object parseValue(ArgType type, String value) {
        try {
            return switch (type) {
                case STRING -> value;
                case INT -> Integer.parseInt(value);
                case BOOLEAN -> {
                    if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
                        yield Boolean.parseBoolean(value);
                    } else {
                        yield null;
                    }
                }
            };
        } catch (Exception e) {
            return null;
        }
    }

    public void printUsage() {
        String args = argDefinitions.stream()
                .map(pair -> "<" + pair.getValue0() + ">")
                .collect(Collectors.joining(" "));

        String flags = flagDefinitions.entrySet().stream()
                .map(entry -> {
                    String flag = entry.getKey();
                    List<Pair<String, ArgType>> values = entry.getValue();
                    String valueStr = values.stream()
                            .map(p -> "<" + p.getValue0() + ">")
                            .collect(Collectors.joining(" "));
                    return "[" + flag + (valueStr.isEmpty() ? "" : " " + valueStr) + "]";
                })
                .collect(Collectors.joining(" "));

        System.out.println("Usage: AFS " + args + (flags.isEmpty() ? "" : " " + flags));
    }
}
