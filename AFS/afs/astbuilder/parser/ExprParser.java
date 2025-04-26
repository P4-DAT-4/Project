package afs.astbuilder.builder;

import afs.AFSBaseVisitor;
import afs.AFSParser;
import afs.astbuilder.nodes.expr.*;

import java.util.ArrayList;
import java.util.List;

public class ExprParser extends AFSBaseVisitor<ExprNode> {
    @Override
    public ExprNode visitParenExpr(AFSParser.ParenExprContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public ExprNode visitListIndexExpr(AFSParser.ListIndexExprContext ctx) {
        AFSParser.ExprContext leftExprCtx = ctx.expr(0);
        AFSParser.ExprContext rightExprCtx = ctx.expr(1);

        ExprNode leftExpression = visit(leftExprCtx);
        ExprNode rightExpression = visit(rightExprCtx);

        return new ExprListAccessNode(leftExpression, rightExpression);
    }

    @Override
    public ExprNode visitListExpr(AFSParser.ListExprContext ctx) {
        List<ExprNode> expressions = new ArrayList<>();
        for (AFSParser.ExprContext exprCtx : ctx.expr()) {
            ExprNode expr = visit(exprCtx);
            expressions.add(expr);
        }

        return new ExprListNode(expressions);
    }

    @Override
    public ExprNode visitAddExpr(AFSParser.AddExprContext ctx) {
        AFSParser.ExprContext leftExprCtx = ctx.expr(0);
        AFSParser.ExprContext rightExprCtx = ctx.expr(1);

        ExprNode leftExpression = visit(leftExprCtx);
        ExprNode rightExpression = visit(rightExprCtx);

        return new ExprAdditionNode(leftExpression, rightExpression);
    }

    @Override
    public ExprNode visitSubExpr(AFSParser.SubExprContext ctx) {
        AFSParser.ExprContext leftExprCtx = ctx.expr(0);
        AFSParser.ExprContext rightExprCtx = ctx.expr(1);

        ExprNode leftExpression = visit(leftExprCtx);
        ExprNode rightExpression = visit(rightExprCtx);

        return new ExprSubtractionNode(leftExpression, rightExpression);
    }

    @Override
    public ExprNode visitMulExpr(AFSParser.MulExprContext ctx) {
        AFSParser.ExprContext leftExprCtx = ctx.expr(0);
        AFSParser.ExprContext rightExprCtx = ctx.expr(1);

        ExprNode leftExpression = visit(leftExprCtx);
        ExprNode rightExpression = visit(rightExprCtx);

        return new ExprMultiplicationNode(leftExpression, rightExpression);
    }

    @Override
    public ExprNode visitDivExpr(AFSParser.DivExprContext ctx) {
        AFSParser.ExprContext leftExprCtx = ctx.expr(0);
        AFSParser.ExprContext rightExprCtx = ctx.expr(1);

        ExprNode leftExpression = visit(leftExprCtx);
        ExprNode rightExpression = visit(rightExprCtx);

        return new ExprDivisionNode(leftExpression, rightExpression);
    }

    @Override
    public ExprNode visitNegExpr(AFSParser.NegExprContext ctx) {
        AFSParser.ExprContext exprCtx = ctx.expr();

        ExprNode expr = visit(exprCtx);
        return new ExprNegationNode(expr);
    }


}
