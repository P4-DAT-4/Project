package afs.nodes.type;

public class TypeBoolNode extends TypeNode {
    public TypeBoolNode(int lineNumber, int columnNumber) {
        super(lineNumber, columnNumber);
    }

    @Override
    public String toString() {
        return "Bool";
    }
}
