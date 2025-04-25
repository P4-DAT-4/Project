package astbuilder.nodes.type;

import astbuilder.visitor.TypeVisitor;

public class TypeDoubleNode extends TypeNode {
    @Override
    public void acceptVisit(TypeVisitor visitor) {
        visitor.visitTypeDoubleNode(this);
    }
}
