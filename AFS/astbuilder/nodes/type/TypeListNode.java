package astbuilder.nodes.type;

import astbuilder.visitor.TypeVisitor;

public class TypeListNode extends TypeNode {
    @Override
    public void acceptVisit(TypeVisitor visitor) {
        visitor.visitTypeListNode(this);
    }
}
