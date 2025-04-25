package astbuilder.nodes.prog;

import astbuilder.builder.ASTVisitor;
import astbuilder.builder.ProgVisitor;
import astbuilder.nodes.AbstractSyntaxNode;
import astbuilder.nodes.def.DefNode;

import java.util.List;

public class ProgNode extends AbstractSyntaxNode<ProgVisitor> {
    List<DefNode> definitions;

    @Override
    public void acceptVisit(ProgVisitor visitor) {
        visitor.visitProgNode(this);
    }
}
