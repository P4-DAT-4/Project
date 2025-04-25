package astbuilder.nodes.prog;

import astbuilder.visitor.ProgVisitor;
import astbuilder.nodes.AbstractSyntaxNode;
import astbuilder.nodes.def.DefNode;

import java.util.List;

public class ProgNode extends AbstractSyntaxNode<ProgVisitor> {
    private final List<DefNode> definitions;

    public ProgNode(List<DefNode> definitions) {
        this.definitions = definitions;
    }

    public List<DefNode> getDefinitions() {
        return definitions;
    }

    @Override
    public void acceptVisit(ProgVisitor visitor) {
        visitor.visitProgNode(this);
    }
}
