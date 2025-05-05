package afs.nodes.type;

public final class TypeVoidNode extends TypeNode {
    public TypeVoidNode(int lineNumber, int columnNumber) {
        super(lineNumber, columnNumber);
    }

    @Override
    public String toString() {
        return "Void";
    }
}
