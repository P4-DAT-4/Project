package afs.nodes.def;

import afs.nodes.AbstractSyntaxNode;

public sealed abstract class DefNode extends AbstractSyntaxNode permits DefDeclarationNode, DefFunctionNode, DefVisualizeNode {
    protected DefNode(int lineNumber, int columnNumber) {
        super(lineNumber, columnNumber);
    }
}
