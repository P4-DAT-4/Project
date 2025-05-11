<<<<<<<< HEAD:src/main/java/afs/semantic_analysis/types/SimpleType.java
package afs.semantic_analysis.types;
========
package afs.checker.types;
>>>>>>>> origin/DPP-142-Type-checker:src/main/java/afs/checker/types/SimpleType.java

public class SimpleType extends AFSType {
    public static final SimpleType STRING = new SimpleType("STRING");
    public static final SimpleType INT = new SimpleType("INT");
    public static final SimpleType BOOL = new SimpleType("BOOL");
    public static final SimpleType DOUBLE = new SimpleType("DOUBLE");
    public static final SimpleType VOID = new SimpleType("VOID");
    public static final SimpleType SHAPE = new SimpleType("SHAPE");

    public final String name;

    public SimpleType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SimpleType)) {
            return false;
        }
        return ((SimpleType) obj).name.equals(this.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
