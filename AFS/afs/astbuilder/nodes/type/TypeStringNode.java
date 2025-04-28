package afs.astbuilder.nodes.type;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.visitor.TypeVisitor;

public class TypeStringNode extends TypeNode {
    @Override
    public AFSType acceptVisit(TypeVisitor visitor) {
        return visitor.visitTypeStringNode(this);
    }

    @Override
    public String toString() {
        return "String";
    }
}
