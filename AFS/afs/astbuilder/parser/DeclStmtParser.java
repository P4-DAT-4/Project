package afs.astbuilder.parser;

import afs.AFSBaseVisitor;
import afs.AFSParser;
import afs.astbuilder.nodes.expr.ExprFunctionCallNode;
import afs.astbuilder.nodes.expr.ExprIdentifierNode;
import afs.astbuilder.nodes.expr.ExprNode;
import afs.astbuilder.nodes.stmt.*;
import afs.astbuilder.nodes.type.TypeNode;

import java.util.List;

public class DeclStmtParser extends AFSBaseVisitor<StmtNode> {
    private final DeclExprParser declExprParser = new DeclExprParser();

    @Override
    public StmtNode visitDeclStmtDef(AFSParser.DeclStmtDefContext ctx) {
        TypeParser typeParser = new TypeParser();
        TypeNode type = ctx.type().accept(typeParser);

        String id = ctx.ID().getText();
        ExprIdentifierNode identifier = new ExprIdentifierNode(id);
        ExprNode expression = ctx.declExpr().accept(declExprParser);

        if (expression == null) {
            System.out.println(String.format("Expression is null: %s", id));
        }

        return new StmtDeclarationNode(type, identifier, expression);
    }

    @Override
    public StmtNode visitDeclStmtIf(AFSParser.DeclStmtIfContext ctx) {
        ExprNode expression = ctx.declExpr().accept(declExprParser);

        List<AFSParser.DeclBlockContext> blockContexts = ctx.declBlock();

        BlockParser blockParser = new BlockParser();
        StmtNode leftStatement = blockContexts.getFirst().accept(blockParser);

        StmtNode rightStatement;

        if (blockContexts.size() > 1) {
            rightStatement = blockContexts.getLast().accept(blockParser);
        } else {
            rightStatement = new StmtSkipNode();
        }

        return new StmtIfNode(expression, leftStatement, rightStatement);
    }

    @Override
    public StmtNode visitDeclStmtFuncCall(AFSParser.DeclStmtFuncCallContext ctx) {
        FuncCallParser funcCallParser = new FuncCallParser();
        ExprFunctionCallNode functionCall = ctx.funcCall().accept(funcCallParser);

        return new StmtFunctionCallNode(functionCall);
    }
}
