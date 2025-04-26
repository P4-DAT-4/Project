package afs.astbuilder.nodes.type;

import afs.astbuilder.visitor.TypeVisitor;

public class TypeIntNode extends TypeNode {
    @Override
    public void acceptVisit(TypeVisitor visitor) {
        visitor.visitTypeIntNode(this);
    }

    @Override
    public String toString() {
        return "TypeInt";
    }
}
