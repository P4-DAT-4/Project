package afs.nodes.type;

public final class TypeListNode extends TypeNode {
    private final TypeNode type;

    public TypeListNode(TypeNode type, int line, int column) {
        super(line, column);
        this.type = type;
    }

    public TypeNode getType() {
        return type;
    }
    @Override
    public String toString() {
        return "List";
    }
}
