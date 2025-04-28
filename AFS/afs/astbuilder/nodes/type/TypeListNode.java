package afs.astbuilder.nodes.type;

import afs.astbuilder.checker.types.AFSType;
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
    public AFSType acceptVisit(TypeVisitor visitor) {
        return visitor.visitTypeListNode(this);
    }

    @Override
    public String toString() {
        return "List";
    }
}
