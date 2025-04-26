package afs.astbuilder.nodes.type;

import afs.astbuilder.visitor.TypeVisitor;

public class TypeListNode extends TypeNode {
    @Override
    public void acceptVisit(TypeVisitor visitor) {
        visitor.visitTypeListNode(this);
    }
}
