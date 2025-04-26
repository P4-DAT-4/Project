package afs.astbuilder.nodes.type;

import afs.astbuilder.visitor.TypeVisitor;

public class TypeVoidNode extends TypeNode {

    @Override
    public void acceptVisit(TypeVisitor visitor) {
        visitor.visitTypeVoidNode(this);
    }

    @Override
    public String toString() {
        return "TypeVoid";
    }
}
