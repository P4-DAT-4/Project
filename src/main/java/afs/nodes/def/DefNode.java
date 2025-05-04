package afs.nodes.def;

import afs.nodes.AbstractSyntaxNode;

public abstract class DefNode extends AbstractSyntaxNode {
    protected DefNode(int lineNumber, int columnNumber) {
        super(lineNumber, columnNumber);
    }
}
