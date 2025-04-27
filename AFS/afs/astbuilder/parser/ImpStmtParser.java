package afs.astbuilder.parser;

import afs.AFSBaseVisitor;
import afs.AFSParser;
import afs.astbuilder.nodes.expr.ExprFunctionCallNode;
import afs.astbuilder.nodes.expr.ExprIdentifierNode;
import afs.astbuilder.nodes.expr.ExprListAccessNode;
import afs.astbuilder.nodes.expr.ExprNode;
import afs.astbuilder.nodes.stmt.*;
import afs.astbuilder.nodes.type.TypeNode;

import java.util.List;

public class ImpStmtParser extends AFSBaseVisitor<StmtNode> {

    @Override
    public StmtNode visitImpStmtDef(AFSParser.ImpStmtDefContext ctx) {
        TypeParser typeParser = new TypeParser();
        TypeNode type = ctx.type().accept(typeParser);

        String id = ctx.ID().getText();
        ExprIdentifierNode identifier = new ExprIdentifierNode(id);

        ExprParser exprParser = new ExprParser();
        ExprNode expression = ctx.expr().accept(exprParser);

        if (expression == null) {
            System.out.println(String.format("Expression is null: %s", id));
        }

        return new StmtDeclarationNode(type, identifier, expression);
    }

    @Override
    public StmtNode visitImpStmtAss(AFSParser.ImpStmtAssContext ctx) {
        String identifier = ctx.ID().getText();
        ExprNode leftExpression = new ExprIdentifierNode(identifier);

        ExprParser exprParser = new ExprParser();
        ExprNode rightExpression = ctx.expr().accept(exprParser);

        return new StmtAssignmentNode(leftExpression, rightExpression);
    }

    @Override
    public StmtNode visitImpStmtListAss(AFSParser.ImpStmtListAssContext ctx) {
        String identifier = ctx.ID().getText();
        ExprNode identifierNode = new ExprIdentifierNode(identifier);

        ListAccessParser listAccessParser = new ListAccessParser();
        ExprNode listAccess = ctx.listAccess().accept(listAccessParser);

        ExprListAccessNode leftExpression = new ExprListAccessNode(identifierNode, listAccess);

        ExprParser exprParser = new ExprParser();
        ExprNode rightExpression = ctx.expr().accept(exprParser);

        return new StmtAssignmentNode(rightExpression, leftExpression);
    }

    @Override
    public StmtNode visitImpStmtIf(AFSParser.ImpStmtIfContext ctx) {
        ExprParser exprParser = new ExprParser();
        ExprNode expression = ctx.expr().accept(exprParser);

        List<AFSParser.ImpBlockContext> blockContexts = ctx.impBlock();

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
    public StmtNode visitImpStmtWhl(AFSParser.ImpStmtWhlContext ctx) {
        ExprParser exprParser = new ExprParser();
        ExprNode expression = ctx.expr().accept(exprParser);

        BlockParser blockParser = new BlockParser();
        StmtNode statement = ctx.impBlock().accept(blockParser);

        return new StmtWhileNode(expression, statement);
    }

    @Override
    public StmtNode visitImpStmtRtn(AFSParser.ImpStmtRtnContext ctx) {
        ExprParser exprParser = new ExprParser();
        ExprNode expression = ctx.expr().accept(exprParser);

        return new StmtReturnNode(expression);
    }

    @Override
    public StmtNode visitImpStmtFuncCall(AFSParser.ImpStmtFuncCallContext ctx) {
        FuncCallParser funcCallParser = new FuncCallParser();
        ExprFunctionCallNode functionCall = ctx.funcCall().accept(funcCallParser);

        return new StmtFunctionCallNode(functionCall);
    }
}
