package afs.astbuilder.parser;

import afs.AFSBaseVisitor;
import afs.AFSParser;
import afs.astbuilder.nodes.expr.ExprListAccessNode;
import afs.astbuilder.nodes.expr.ExprNode;

import java.util.List;

public class ListAccessParser extends AFSBaseVisitor<ExprNode> {
    private final ExprParser exprParser = new ExprParser();

    @Override
    public ExprNode visitListAccess(AFSParser.ListAccessContext ctx) {
        List<AFSParser.ExprContext> exprContexts = ctx.expr();

        return buildListAccessTree(exprContexts);
    }

    private ExprNode buildListAccessTree(List<AFSParser.ExprContext> exprContexts) {
        if (exprContexts.isEmpty()) {
            return null; // Should probably throw error, since this shouldn't be possible
        }

        ExprNode leftExpression = exprContexts.getFirst().accept(exprParser);

        if (exprContexts.size() > 1) {
            List<AFSParser.ExprContext> restExprContexts = exprContexts.subList(1, exprContexts.size());
            ExprNode rightExpression = buildListAccessTree(restExprContexts);
            return new ExprListAccessNode(leftExpression, rightExpression);
        }

        return leftExpression;
    }
}
