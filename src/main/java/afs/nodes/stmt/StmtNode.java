package afs.nodes.stmt;

import afs.nodes.AbstractSyntaxNode;

public abstract class StmtNode extends AbstractSyntaxNode {
    protected StmtNode(int lineNumber, int columnNumber) {
        super(lineNumber, columnNumber);
    }
}
