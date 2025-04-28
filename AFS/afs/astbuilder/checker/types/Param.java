package afs.astbuilder.checker.types;

import afs.astbuilder.checker.exceptions.TypeCheckException;

public class Param {
    public final String name;
    public final AFSType type;

    public Param(String name, AFSType type) {
        if (name == null || type == null) {
            throw new NullPointerException("Trying to create a Param with null name or type");
        }

        if (type.equals(SimpleType.VOID)) {
            throw new TypeCheckException("Cannot create a Param with type void");
        }
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return name + ": " + type;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AFSType)) {
            return false;
        }
        return type.equals(obj);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }
}
