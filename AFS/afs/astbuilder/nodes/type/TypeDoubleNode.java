package afs.astbuilder.nodes.type;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.visitor.TypeVisitor;

public class TypeDoubleNode extends TypeNode {
    @Override
    public AFSType acceptVisit(TypeVisitor visitor) {
        return visitor.visitTypeDoubleNode(this);
    }

    @Override
    public String toString() {
        return "Double";
    }
}
