package afs.astbuilder.nodes.type;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.visitor.TypeVisitor;

public class TypeBoolNode extends TypeNode {

    @Override
    public AFSType acceptVisit(TypeVisitor visitor) {
        return visitor.visitTypeBoolNode(this);
    }

    @Override
    public String toString() {
        return "Bool";
    }
}
