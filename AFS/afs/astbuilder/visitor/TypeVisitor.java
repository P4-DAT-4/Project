package afs.astbuilder.visitor;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.nodes.type.*;

public interface TypeVisitor extends ASTVisitor {
    AFSType visitTypeBoolNode(TypeBoolNode node);
    AFSType visitTypeDoubleNode(TypeDoubleNode node);
    AFSType visitTypeIntNode(TypeIntNode node);
    AFSType visitTypeListNode(TypeListNode node);
    AFSType visitTypeShapeNode(TypeShapeNode node);
    AFSType visitTypeStringNode(TypeStringNode node);
    AFSType visitTypeVoidNode(TypeVoidNode node);
}
