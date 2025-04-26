package afs.astbuilder.parser;

import afs.AFSBaseVisitor;
import afs.AFSParser;
import afs.astbuilder.nodes.type.*;

public class TypeParser extends AFSBaseVisitor<TypeNode> {
    @Override
    public TypeNode visitBoolType(AFSParser.BoolTypeContext ctx) {
        return new TypeBoolNode();
    }

    @Override
    public TypeNode visitIntType(AFSParser.IntTypeContext ctx) {
        return new TypeIntNode();
    }

    @Override
    public TypeNode visitStringType(AFSParser.StringTypeContext ctx) {
        return new TypeStringNode();
    }

    @Override
    public TypeNode visitDoubleType(AFSParser.DoubleTypeContext ctx) {
        return new TypeDoubleNode();
    }

    @Override
    public TypeNode visitShapeType(AFSParser.ShapeTypeContext ctx) {
        return new TypeShapeNode();
    }

    @Override
    public TypeNode visitListType(AFSParser.ListTypeContext ctx) {
        TypeNode type = visit(ctx.type());
        return new TypeListNode(type);
    }

    @Override
    public TypeNode visitVoidType(AFSParser.VoidTypeContext ctx) {
        return new TypeVoidNode();
    }
}
