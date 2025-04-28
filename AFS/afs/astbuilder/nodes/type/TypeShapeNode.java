package afs.astbuilder.nodes.type;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.visitor.TypeVisitor;

public class TypeShapeNode extends TypeNode {
    @Override
    public AFSType acceptVisit(TypeVisitor visitor) {
        return visitor.visitTypeShapeNode(this);
    }

    @Override
    public String toString() {
        return "Shape";
    }
}
