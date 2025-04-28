package afs.astbuilder.nodes.type;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.visitor.TypeVisitor;

public class TypeVoidNode extends TypeNode {

    @Override
    public AFSType acceptVisit(TypeVisitor visitor) {
        return visitor.visitTypeVoidNode(this);
    }

    @Override
    public String toString() {
        return "Void";
    }
}
