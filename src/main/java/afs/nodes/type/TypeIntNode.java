package afs.nodes.type;

public final class TypeIntNode extends TypeNode {

    public TypeIntNode(int lineNumber, int columnNumber) {
        super(lineNumber, columnNumber);
    }

    @Override
    public String toString() {
        return "Int";
    }
}
