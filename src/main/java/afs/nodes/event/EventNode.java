package afs.nodes.event;

import afs.nodes.AbstractSyntaxNode;

public abstract class EventNode extends AbstractSyntaxNode {
    protected EventNode(int lineNumber, int columnNumber) {
        super(lineNumber, columnNumber);
    }
}
