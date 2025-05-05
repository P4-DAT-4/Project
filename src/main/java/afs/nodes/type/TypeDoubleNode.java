package afs.nodes.type;

public final class TypeDoubleNode extends TypeNode {
    public TypeDoubleNode(int lineNumber, int columnNumber) {
        super(lineNumber, columnNumber);
    }

    @Override
    public String toString() {
        return "Double";
    }
}
