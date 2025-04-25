package astbuilder.nodes.type;

import astbuilder.visitor.TypeVisitor;

public class TypeShapeNode extends TypeNode {
    @Override
    public void acceptVisit(TypeVisitor visitor) {
        visitor.visitTypeShapeNode(this);
    }
}
