package afs.astbuilder.parser;

import afs.AFSBaseVisitor;
import afs.AFSParser;
import afs.astbuilder.nodes.def.DefDeclarationNode;
import afs.astbuilder.nodes.expr.ExprFunctionCallNode;
import afs.astbuilder.nodes.expr.ExprIdentifierNode;
import afs.astbuilder.nodes.expr.ExprListAccessNode;
import afs.astbuilder.nodes.expr.ExprNode;
import afs.astbuilder.nodes.stmt.*;
import afs.astbuilder.nodes.type.TypeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StmtParser extends AFSBaseVisitor<StmtNode> {

    @Override
    public StmtNode visitStmtDef(AFSParser.StmtDefContext ctx) {
        TypeParser typeParser = new TypeParser();
        TypeNode type = ctx.type().accept(typeParser);

        String id = ctx.ID().getText();

        ExprParser exprParser = new ExprParser();
        ExprNode expression = ctx.expr().accept(exprParser);

        return new StmtDeclarationNode(type, id, expression);
    }

    @Override
    public StmtNode visitStmtAss(AFSParser.StmtAssContext ctx) {
        String identifier = ctx.ID().getText();
        ExprNode leftExpression = new ExprIdentifierNode(identifier);

        ExprParser exprParser = new ExprParser();
        ExprNode rightExpression = ctx.expr().accept(exprParser);

        return new StmtAssignmentNode(leftExpression, rightExpression);
    }

    @Override
    public StmtNode visitStmtListAss(AFSParser.StmtListAssContext ctx) {
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
    public StmtNode visitStmtIf(AFSParser.StmtIfContext ctx) {
        ExprParser exprParser = new ExprParser();
        ExprNode expression = ctx.expr().accept(exprParser);

        List<AFSParser.BlockContext> blockContexts = ctx.block();

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
    public StmtNode visitStmtWhl(AFSParser.StmtWhlContext ctx) {
        ExprParser exprParser = new ExprParser();
        ExprNode expression = ctx.expr().accept(exprParser);

        BlockParser blockParser = new BlockParser();
        StmtNode statement = ctx.block().accept(blockParser);

        return new StmtWhileNode(expression, statement);
    }

    @Override
    public StmtNode visitStmtRtn(AFSParser.StmtRtnContext ctx) {
        ExprParser exprParser = new ExprParser();
        ExprNode expression = ctx.expr().accept(exprParser);

        return new StmtReturnNode(expression);
    }

    @Override
    public StmtNode visitStmtFuncCall(AFSParser.StmtFuncCallContext ctx) {
        FuncCallParser funcCallParser = new FuncCallParser();
        ExprFunctionCallNode functionCall = ctx.funcCall().accept(funcCallParser);

        return new StmtFunctionCallNode(functionCall);
    }
}
