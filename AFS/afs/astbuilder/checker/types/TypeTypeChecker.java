package afs.astbuilder.checker.types;

import afs.astbuilder.nodes.type.*;
import afs.astbuilder.visitor.TypeVisitor;

public class TypeTypeChecker implements TypeVisitor {

    @Override
    public AFSType visitTypeBoolNode(TypeBoolNode node) {
        return SimpleType.BOOL;
    }

    @Override
    public AFSType visitTypeDoubleNode(TypeDoubleNode node) {
        return SimpleType.DOUBLE;
    }

    @Override
    public AFSType visitTypeIntNode(TypeIntNode node) {
        return SimpleType.INT;
    }

    @Override
    public AFSType visitTypeListNode(TypeListNode node) {
        AFSType listType = node.getType().acceptVisit(this);
        return new ListType(listType);
    }

    @Override
    public AFSType visitTypeShapeNode(TypeShapeNode node) {
        return SimpleType.SHAPE;
    }

    @Override
    public AFSType visitTypeStringNode(TypeStringNode node) {
        return SimpleType.STRING;
    }

    @Override
    public AFSType visitTypeVoidNode(TypeVoidNode node) {
        return SimpleType.VOID;
    }
}
