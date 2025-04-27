package afs.astbuilder.nodes.type;

import afs.astbuilder.visitor.TypeVisitor;

public class TypeDoubleNode extends TypeNode {
    @Override
    public void acceptVisit(TypeVisitor visitor) {
        visitor.visitTypeDoubleNode(this);
    }

    @Override
    public String toString() {
        return "Double";
    }
}
