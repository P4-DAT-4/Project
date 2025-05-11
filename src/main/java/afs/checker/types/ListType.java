package afs.checker.types;

public class ListType extends AFSType {
    private final AFSType type;

    public ListType(AFSType type) {
        if (type.equals(SimpleType.VOID)) {
            throw new IllegalArgumentException("Void type is not allowed in list.");
        }
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ListType)) {
            return false;
        }
        return ((ListType) obj).type.equals(this.type);
    }

    public AFSType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return type.hashCode() * 31;
    }

    @Override
    public String toString() {
        return "List<" + type.toString() + ">";
    }

}
