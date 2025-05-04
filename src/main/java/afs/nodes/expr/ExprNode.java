package afs.nodes.expr;

import afs.nodes.AbstractSyntaxNode;

public abstract class ExprNode extends AbstractSyntaxNode {
    protected ExprNode(int lineNumber, int columnNumber) {
        super(lineNumber, columnNumber);
    }
}
