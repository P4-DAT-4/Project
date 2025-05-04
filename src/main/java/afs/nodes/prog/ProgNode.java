package afs.nodes.prog;

import afs.nodes.AbstractSyntaxNode;
import afs.nodes.def.DefNode;

import java.util.List;

public class ProgNode extends AbstractSyntaxNode {
    private final List<DefNode> definitions;

    public ProgNode(List<DefNode> definitions) {
        super(0, 0);
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
