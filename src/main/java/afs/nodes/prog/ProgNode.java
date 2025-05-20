package afs.nodes.prog;

import afs.nodes.AbstractSyntaxNode;
import afs.nodes.def.DefNode;

public final class ProgNode extends AbstractSyntaxNode {
    private final DefNode definition;

    public ProgNode(DefNode definition) {
        super(0, 0);
        this.definition = definition;
    }

    public DefNode getDefinition() {
        return definition;
    }

    @Override
    public String toString() {
        return "Program";
    }
}
