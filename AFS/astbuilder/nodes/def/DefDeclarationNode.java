package astbuilder.nodes.def;

import astbuilder.visitor.ASTVisitor;
import astbuilder.visitor.DefVisitor;

public class DefDeclarationNode extends DefNode{

    @Override
    public void acceptVisit(DefVisitor visitor) {
        visitor.visitDefDeclarationNode(this);
    }
}
