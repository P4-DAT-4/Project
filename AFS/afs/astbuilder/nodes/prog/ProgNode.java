package afs.astbuilder.nodes.prog;

import afs.astbuilder.nodes.AbstractSyntaxNode;
import afs.astbuilder.nodes.def.DefNode;

import java.util.List;

public class ProgNode extends AbstractSyntaxNode {
    private final List<DefNode> definitions;

    public ProgNode(List<DefNode> definitions) {
        this.definitions = definitions;
    }

    public List<DefNode> getDefinitions() {
        return definitions;
    }

    @Override
    public String toString() {
        return "Program";
    }
}
