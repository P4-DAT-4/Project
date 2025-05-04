package afs.nodes.type;

import afs.nodes.AbstractSyntaxNode;

public abstract class TypeNode extends AbstractSyntaxNode {
    protected TypeNode(int lineNumber, int columnNumber) {
        super(lineNumber, columnNumber);
    }
}
