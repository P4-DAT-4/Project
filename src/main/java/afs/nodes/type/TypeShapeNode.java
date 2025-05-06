package afs.nodes.type;

public final class TypeShapeNode extends TypeNode {

    public TypeShapeNode(int lineNumber, int columnNumber) {
        super(lineNumber, columnNumber);
    }

    @Override
    public String toString() {
        return "Shape";
    }
}
