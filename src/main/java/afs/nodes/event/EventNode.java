package afs.nodes.event;

import afs.nodes.AbstractSyntaxNode;

public sealed abstract class EventNode extends AbstractSyntaxNode permits EventCompositionNode, EventDeclarationNode {
    protected EventNode(int lineNumber, int columnNumber) {
        super(lineNumber, columnNumber);
    }
}
