package afs.semantic_analysis.types;

import java.util.ArrayList;
import java.util.List;

public class FunctionType extends AFSType {
    private final List<AFSType> paramTypes = new ArrayList<>();
    private final AFSType returnType;

    public FunctionType(AFSType returnType) {
        this.returnType = returnType;
    }

    public void addParamType(AFSType paramType) {
        paramTypes.add(paramType);
    }

    public List<AFSType> getParamTypes() {
        return paramTypes;
    }

    public AFSType getReturnType() {
        return returnType;
    }

    @Override
    public String toString() {
        return returnType.toString();
    }
}
