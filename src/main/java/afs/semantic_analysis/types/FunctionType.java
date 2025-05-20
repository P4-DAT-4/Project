package afs.semantic_analysis.types;

import afs.nodes.def.Param;

import java.util.ArrayList;
import java.util.List;

public class FunctionType extends AFSType {
    private final AFSType returnType;
    private final List<Param> params = new ArrayList<>();

    public FunctionType(AFSType returnType) {
        this.returnType = returnType;
    }

    public void addParam(Param param) {
        boolean paramIdentifierExists = params.stream().anyMatch(p -> p.getIdentifier() == param.getIdentifier());
        if (paramIdentifierExists) {
            throw new IllegalArgumentException("Duplicate parameter identifier'" + param.getIdentifier() + "'");
        }
        params.add(param);
    }

    public List<Param> getParams() {
        return params;
    }

    public AFSType getReturnType() {
        return returnType;
    }

    @Override
    public String toString() {
        return returnType.toString();
    }
}
