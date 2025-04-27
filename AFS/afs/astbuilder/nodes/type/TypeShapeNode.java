package afs.astbuilder.nodes.type;

import afs.astbuilder.visitor.TypeVisitor;

public class TypeShapeNode extends TypeNode {
    @Override
    public void acceptVisit(TypeVisitor visitor) {
        visitor.visitTypeShapeNode(this);
    }

    @Override
    public String toString() {
        return "Shape";
    }
}
