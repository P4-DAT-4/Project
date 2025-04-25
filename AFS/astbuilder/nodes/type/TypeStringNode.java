package astbuilder.nodes.type;

import astbuilder.visitor.TypeVisitor;

public class TypeStringNode extends TypeNode {
    @Override
    public void acceptVisit(TypeVisitor visitor) {
        visitor.visitTypeStringNode(this);
    }
}
