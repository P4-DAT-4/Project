package afs.astbuilder.parser;

import afs.AFSBaseVisitor;
import afs.AFSParser;
import afs.astbuilder.nodes.def.DefDeclarationNode;
import afs.astbuilder.nodes.def.DefFunctionNode;
import afs.astbuilder.nodes.def.DefNode;
import afs.astbuilder.nodes.def.Param;
import afs.astbuilder.nodes.expr.ExprNode;
import afs.astbuilder.nodes.stmt.StmtNode;
import afs.astbuilder.nodes.type.TypeNode;
import afs.astbuilder.nodes.type.TypeShapeNode;

import java.util.ArrayList;
import java.util.List;

public class DefParser extends AFSBaseVisitor<DefNode> {

    @Override
    public DefNode visitFnDef(AFSParser.FnDefContext ctx) {
        TypeParser typeParser = new TypeParser();
        TypeNode type = ctx.type().accept(typeParser);

        String identifier = ctx.ID().getText();

        List<Param> params = new ArrayList<>();
        if (ctx.params() != null) {
            ParamsParser paramsParser = new ParamsParser();
            params = ctx.params().accept(paramsParser);
        }

        BlockParser blockParser = new BlockParser();
        StmtNode statement = ctx.impBlock().accept(blockParser);

        return new DefFunctionNode(type, identifier, params, statement);
    }

    @Override
    public DefNode visitVisDef(AFSParser.VisDefContext ctx) {
        TypeNode type = new TypeShapeNode();

        String identifier = ctx.ID().getText();

        List<Param> params = new ArrayList<>();
        if (ctx.params() != null) {
            ParamsParser paramsParser = new ParamsParser();
            params = ctx.params().accept(paramsParser);
        }

        BlockParser blockParser = new BlockParser();
        StmtNode statement = ctx.declBlock().accept(blockParser);

        return new DefFunctionNode(type, identifier, params, statement);
    }

    @Override
    public DefDeclarationNode visitVarDef(AFSParser.VarDefContext ctx) {
        TypeParser typeParser = new TypeParser();
        TypeNode type = ctx.type().accept(typeParser);

        String id = ctx.ID().getText();

        ExprParser exprParser = new ExprParser();
        ExprNode expression = ctx.expr().accept(exprParser);

        return new DefDeclarationNode(type, id, expression);
    }
}
