package afs.astbuilder.nodes.type;

import afs.astbuilder.visitor.TypeVisitor;

public class TypeListNode extends TypeNode {
    private final TypeNode type;

    public TypeListNode(TypeNode type) {
        this.type = type;
    }

    public TypeNode getType() {
        return type;
    }

    @Override
    public void acceptVisit(TypeVisitor visitor) {
        visitor.visitTypeListNode(this);
    }
}
