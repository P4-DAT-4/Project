package afs.astbuilder.nodes.type;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.visitor.TypeVisitor;

public class TypeIntNode extends TypeNode {
    @Override
    public AFSType acceptVisit(TypeVisitor visitor) {
        return visitor.visitTypeIntNode(this);
    }

    @Override
    public String toString() {
        return "Int";
    }
}
