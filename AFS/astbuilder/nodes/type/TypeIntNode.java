package astbuilder.nodes.type;

import astbuilder.visitor.TypeVisitor;

public class TypeIntNode extends TypeNode {
    @Override
    public void acceptVisit(TypeVisitor visitor) {
        visitor.visitTypeIntNode(this);
    }
}
