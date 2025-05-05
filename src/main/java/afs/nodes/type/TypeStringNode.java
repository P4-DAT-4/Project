package afs.nodes.type;

public final class TypeStringNode extends TypeNode {
    public TypeStringNode(int lineNumber, int columnNumber) {
        super(lineNumber, columnNumber);
    }

    @Override
    public String toString() {
        return "String";
    }
}
