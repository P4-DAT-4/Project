package afs.astbuilder.nodes.prog;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.nodes.AbstractSyntaxNode;
import afs.astbuilder.nodes.def.DefNode;
import afs.astbuilder.visitor.ProgVisitor;

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
    public AFSType acceptVisit(ProgVisitor visitor) {
        return visitor.visitProgNode(this);
    }

    @Override
    public String toString() {
        return "Program";
    }
}
