package afs.nodes.type;

public class TypeStringNode extends TypeNode {
    public TypeStringNode(int lineNumber, int columnNumber) {
        super(lineNumber, columnNumber);
    }

    @Override
    public String toString() {
        return "String";
    }
}
