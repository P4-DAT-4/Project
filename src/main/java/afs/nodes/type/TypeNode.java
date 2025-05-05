package afs.nodes.type;

import afs.nodes.AbstractSyntaxNode;

public sealed abstract class TypeNode extends AbstractSyntaxNode permits TypeBoolNode, TypeDoubleNode, TypeIntNode, TypeListNode, TypeShapeNode, TypeStringNode, TypeVoidNode {
    protected TypeNode(int lineNumber, int columnNumber) {
        super(lineNumber, columnNumber);
    }
}
