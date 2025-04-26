package afs.astbuilder.nodes.type;

import afs.astbuilder.visitor.TypeVisitor;

public class TypeBoolNode extends TypeNode {

    @Override
    public void acceptVisit(TypeVisitor visitor) {
        visitor.visitTypeBoolNode(this);
    }

    @Override
    public String toString() {
        return "TypeBool";
    }
}
