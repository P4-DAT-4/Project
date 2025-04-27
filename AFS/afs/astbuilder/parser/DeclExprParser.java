package afs.astbuilder.parser;

import afs.AFSBaseVisitor;
import afs.AFSParser;
import afs.astbuilder.nodes.expr.*;

import java.util.ArrayList;
import java.util.List;

public class DeclExprParser extends AFSBaseVisitor<ExprNode> {
    private final ExprParser exprParser = new ExprParser();

    @Override
    public ExprNode visitPlaceExpr(AFSParser.PlaceExprContext ctx) {
        AFSParser.ExprContext leftExprCtx = ctx.expr(0);
        AFSParser.ExprContext middleExprCtx = ctx.expr(1);
        AFSParser.ExprContext rightExprCtx = ctx.expr(2);

        ExprNode leftExpression = leftExprCtx.accept(exprParser);
        ExprNode middleExpression = middleExprCtx.accept(exprParser);
        ExprNode rightExpression = rightExprCtx.accept(exprParser);

        List<ExprNode> expressions = new ArrayList<>();
        expressions.add(middleExpression);
        expressions.add(rightExpression);

        ExprListNode exprListNode = new ExprListNode(expressions);

        return new ExprPlaceNode(leftExpression, exprListNode);
    }

    @Override
    public ExprNode visitScaleExpr(AFSParser.ScaleExprContext ctx) {
        AFSParser.ExprContext leftExprCtx = ctx.expr(0);
        AFSParser.ExprContext rightExprCtx = ctx.expr(1);

        ExprNode leftExpression = leftExprCtx.accept(exprParser);
        ExprNode rightExpression = rightExprCtx.accept(exprParser);

        return new ExprScaleNode(leftExpression, rightExpression);
    }

    @Override
    public ExprNode visitRotateExpr(AFSParser.RotateExprContext ctx) {
        AFSParser.ExprContext leftExprCtx = ctx.expr(0);
        AFSParser.ExprContext middleExprCtx = ctx.expr(1);
        AFSParser.ExprContext rightExprCtx = ctx.expr(2);

        ExprNode leftExpression = leftExprCtx.accept(exprParser);
        ExprNode middleExpression = middleExprCtx.accept(exprParser);
        ExprNode rightExpression = rightExprCtx.accept(exprParser);

        return new ExprRotateNode(leftExpression, middleExpression, rightExpression);
    }

    @Override
    public ExprNode visitTextExpr(AFSParser.TextExprContext ctx) {
        AFSParser.ExprContext exprCtx = ctx.expr();

        ExprNode expression = exprCtx.accept(exprParser);

        return new ExprTextNode(expression);
    }

    @Override
    public ExprNode visitLineExpr(AFSParser.LineExprContext ctx) {
        List<AFSParser.ExprContext> exprCtxs = ctx.expr();
        List<ExprNode> expressions = new ArrayList<>();

        for (AFSParser.ExprContext exprCtx : exprCtxs) {
            ExprNode expression = exprCtx.accept(exprParser);
            expressions.add(expression);
        }

        return new ExprLineNode(expressions);
    }

    @Override
    public ExprNode visitCurveExpr(AFSParser.CurveExprContext ctx) {
        List<AFSParser.ExprContext> exprCtxs = ctx.expr();
        List<ExprNode> expressions = new ArrayList<>();

        for (AFSParser.ExprContext exprCtx : exprCtxs) {
            ExprNode expression = exprCtx.accept(exprParser);
            expressions.add(expression);
        }

        return new ExprCurveNode(expressions);
    }
}
