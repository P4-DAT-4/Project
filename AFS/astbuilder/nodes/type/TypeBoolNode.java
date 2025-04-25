package astbuilder.nodes.type;

import astbuilder.visitor.ASTVisitor;
import astbuilder.visitor.TypeVisitor;

public class TypeBoolNode extends TypeNode {

    @Override
    public void acceptVisit(TypeVisitor visitor) {
        visitor.visitTypeBoolNode(this);
    }
}
