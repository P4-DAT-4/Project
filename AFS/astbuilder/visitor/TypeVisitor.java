package astbuilder.visitor;

import astbuilder.nodes.type.*;

public interface TypeVisitor extends ASTVisitor {
    void visitTypeBoolNode(TypeBoolNode node);
    void visitTypeDoubleNode(TypeDoubleNode node);
    void visitTypeIntNode(TypeIntNode node);
    void visitTypeListNode(TypeListNode node);
    void visitTypeShapeNode(TypeShapeNode node);
    void visitTypeStringNode(TypeStringNode node);
}
