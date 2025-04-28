package afs.astbuilder.checker.types;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FunctionType extends AFSType {
    private final List<Param> parameters;
    private final AFSType returnType;

    public FunctionType(List<Param> parameters, AFSType returnType) {
        this.parameters = parameters;
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        String parameterstring = parameters.stream().map(Param::toString).collect(Collectors.joining(", "));
        return String.format("(%s) -> %s", returnType.toString(), parameterstring);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FunctionType other)) {
            return false;
        }

        boolean parametersSameSize = parameters.size() == other.parameters.size();
        boolean sameReturnType = returnType.equals(other.returnType);
        if (!parametersSameSize || !sameReturnType) {
            return false;
        }

        for (int i = 0; i < parameters.size(); i++) {
            boolean parametersEqual = parameters.get(i).equals(other.parameters.get(i));
            if (!parametersEqual) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters, returnType);
    }
}