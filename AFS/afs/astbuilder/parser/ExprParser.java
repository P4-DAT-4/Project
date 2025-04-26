package afs.astbuilder.parser;

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
    public ExprNode visitFuncCallExpr(AFSParser.FuncCallExprContext ctx) {
        return visit(ctx.funcCall());
    }

    @Override
    public ExprNode visitNegExpr(AFSParser.NegExprContext ctx) {
        AFSParser.ExprContext exprCtx = ctx.expr();

        ExprNode expr = visit(exprCtx);
        return new ExprNegationNode(expr);
    }

    @Override
    public ExprNode visitNotExpr(AFSParser.NotExprContext ctx) {
        AFSParser.ExprContext exprCtx = ctx.expr();

        ExprNode expr = visit(exprCtx);
        return new ExprNotNode(expr);
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
    public ExprNode visitConcatExpr(AFSParser.ConcatExprContext ctx) {
        AFSParser.ExprContext leftExprCtx = ctx.expr(0);
        AFSParser.ExprContext rightExprCtx = ctx.expr(1);

        ExprNode leftExpression = visit(leftExprCtx);
        ExprNode rightExpression = visit(rightExprCtx);

        return new ExprConcatenationNode(leftExpression, rightExpression);
    }

    @Override
    public ExprNode visitPlaceExpr(AFSParser.PlaceExprContext ctx) {
        AFSParser.ExprContext leftExprCtx = ctx.expr(0);
        AFSParser.ExprContext middleExprCtx = ctx.expr(1);
        AFSParser.ExprContext rightExprCtx = ctx.expr(2);

        ExprNode leftExpression = visit(leftExprCtx);
        ExprNode middleExpression = visit(middleExprCtx);
        ExprNode rightExpression = visit(rightExprCtx);

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

        ExprNode leftExpression = visit(leftExprCtx);
        ExprNode rightExpression = visit(rightExprCtx);

        return new ExprScaleNode(leftExpression, rightExpression);
    }

    @Override
    public ExprNode visitRotateExpr(AFSParser.RotateExprContext ctx) {
        AFSParser.ExprContext leftExprCtx = ctx.expr(0);
        AFSParser.ExprContext middleExprCtx = ctx.expr(1);
        AFSParser.ExprContext rightExprCtx = ctx.expr(2);

        ExprNode leftExpression = visit(leftExprCtx);
        ExprNode middleExpression = visit(middleExprCtx);
        ExprNode rightExpression = visit(rightExprCtx);

        return new ExprRotateNode(leftExpression, middleExpression, rightExpression);
    }

    @Override
    public ExprNode visitLEqExpr(AFSParser.LEqExprContext ctx) {
        AFSParser.ExprContext leftExprCtx = ctx.expr(0);
        AFSParser.ExprContext rightExprCtx = ctx.expr(1);

        ExprNode leftExpression = visit(leftExprCtx);
        ExprNode rightExpression = visit(rightExprCtx);

        ExprLessThanNode lessThanNode = new ExprLessThanNode(leftExpression, rightExpression);
        ExprEqualsNode equalsNode = new ExprEqualsNode(leftExpression, rightExpression);

        ExprNotNode notLeftExpression = new ExprNotNode(lessThanNode);
        ExprNotNode notRightExpression = new ExprNotNode(equalsNode);

        ExprAndNode andNode = new ExprAndNode(notLeftExpression, notRightExpression);
        return new ExprNotNode(andNode);
    }

    @Override
    public ExprNode visitGEqExpr(AFSParser.GEqExprContext ctx) {
        AFSParser.ExprContext leftExprCtx = ctx.expr(0);
        AFSParser.ExprContext rightExprCtx = ctx.expr(1);

        ExprNode leftExpression = visit(leftExprCtx);
        ExprNode rightExpression = visit(rightExprCtx);

        ExprLessThanNode lessThanNode = new ExprLessThanNode(rightExpression, leftExpression);
        ExprEqualsNode equalsNode = new ExprEqualsNode(leftExpression, rightExpression);

        ExprNotNode notLeftExpression = new ExprNotNode(lessThanNode);
        ExprNotNode notRightExpression = new ExprNotNode(equalsNode);

        ExprAndNode andNode = new ExprAndNode(notLeftExpression, notRightExpression);
        return new ExprNotNode(andNode);
    }

    @Override
    public ExprNode visitEqExpr(AFSParser.EqExprContext ctx) {
        AFSParser.ExprContext leftExprCtx = ctx.expr(0);
        AFSParser.ExprContext rightExprCtx = ctx.expr(1);

        ExprNode leftExpression = visit(leftExprCtx);
        ExprNode rightExpression = visit(rightExprCtx);

        return new ExprEqualsNode(leftExpression, rightExpression);
    }

    @Override
    public ExprNode visitNEgExpr(AFSParser.NEgExprContext ctx) {
        AFSParser.ExprContext leftExprCtx = ctx.expr(0);
        AFSParser.ExprContext rightExprCtx = ctx.expr(1);

        ExprNode leftExpression = visit(leftExprCtx);
        ExprNode rightExpression = visit(rightExprCtx);

        ExprEqualsNode equalsNode = new ExprEqualsNode(leftExpression, rightExpression);

        return new ExprNotNode(equalsNode);
    }

    @Override
    public ExprNode visitLsExpr(AFSParser.LsExprContext ctx) {
        AFSParser.ExprContext leftExprCtx = ctx.expr(0);
        AFSParser.ExprContext rightExprCtx = ctx.expr(1);

        ExprNode leftExpression = visit(leftExprCtx);
        ExprNode rightExpression = visit(rightExprCtx);

        return new ExprLessThanNode(leftExpression, rightExpression);
    }

    @Override
    public ExprNode visitGtExpr(AFSParser.GtExprContext ctx) {
        AFSParser.ExprContext leftExprCtx = ctx.expr(0);
        AFSParser.ExprContext rightExprCtx = ctx.expr(1);

        ExprNode leftExpression = visit(leftExprCtx);
        ExprNode rightExpression = visit(rightExprCtx);

        return new ExprLessThanNode(rightExpression, leftExpression);
    }

    @Override
    public ExprNode visitOrExpr(AFSParser.OrExprContext ctx) {
        AFSParser.ExprContext leftExprCtx = ctx.expr(0);
        AFSParser.ExprContext rightExprCtx = ctx.expr(1);

        ExprNode leftExpression = visit(leftExprCtx);
        ExprNode rightExpression = visit(rightExprCtx);

        ExprNotNode notLeftExpression = new ExprNotNode(leftExpression);
        ExprNotNode notRightExpression = new ExprNotNode(rightExpression);

        ExprAndNode andNode = new ExprAndNode(notLeftExpression, notRightExpression);
        return new ExprNotNode(andNode);
    }

    @Override
    public ExprNode visitAndExpr(AFSParser.AndExprContext ctx) {
        AFSParser.ExprContext leftExprCtx = ctx.expr(0);
        AFSParser.ExprContext rightExprCtx = ctx.expr(1);

        ExprNode leftExpression = visit(leftExprCtx);
        ExprNode rightExpression = visit(rightExprCtx);

        return new ExprAndNode(leftExpression, rightExpression);
    }

    @Override
    public ExprNode visitTextExpr(AFSParser.TextExprContext ctx) {
        AFSParser.ExprContext exprCtx = ctx.expr();

        ExprNode expression = visit(exprCtx);

        return new ExprTextNode(expression);
    }

    @Override
    public ExprNode visitLineExpr(AFSParser.LineExprContext ctx) {
        List<AFSParser.ExprContext> exprCtxs = ctx.expr();
        List<ExprNode> expressions = new ArrayList<>();

        for (AFSParser.ExprContext exprCtx : exprCtxs) {
            ExprNode expression = visit(exprCtx);
            expressions.add(expression);
        }

        return new ExprLineNode(expressions);
    }

    @Override
    public ExprNode visitCurveExpr(AFSParser.CurveExprContext ctx) {
        List<AFSParser.ExprContext> exprCtxs = ctx.expr();
        List<ExprNode> expressions = new ArrayList<>();

        for (AFSParser.ExprContext exprCtx : exprCtxs) {
            ExprNode expression = visit(exprCtx);
            expressions.add(expression);
        }

        return new ExprCurveNode(expressions);
    }

    @Override
    public ExprNode visitID(AFSParser.IDContext ctx) {
        String identifier = ctx.ID().getText();
        return new ExprIdentifierNode(identifier);
    }

    @Override
    public ExprNode visitInt(AFSParser.IntContext ctx) {
        int value = Integer.parseInt(ctx.INT().getText()); // Add try-catch to this? An integer that is too large/small should probably stop parsing - e.g. result in an invalid program
        return new ExprIntNode(value);
    }

    @Override
    public ExprNode visitBool(AFSParser.BoolContext ctx) {
        boolean value = Boolean.parseBoolean(ctx.BOOL().getText());
        return new ExprBoolNode(value);
    }

    @Override
    public ExprNode visitDouble(AFSParser.DoubleContext ctx) {
        double value = Double.parseDouble(ctx.DOUBLE().getText());
        return new ExprDoubleNode(value);
    }

    @Override
    public ExprNode visitString(AFSParser.StringContext ctx) {
        String value = ctx.STRING().getText();
        return new ExprStringNode(value);
    }
}
