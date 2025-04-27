package afs.astbuilder.parser;

import afs.AFSBaseVisitor;
import afs.AFSParser;
import afs.astbuilder.nodes.expr.ExprFunctionCallNode;
import afs.astbuilder.nodes.expr.ExprIdentifierNode;
import afs.astbuilder.nodes.expr.ExprNode;

import java.util.ArrayList;
import java.util.List;

public class FuncCallParser extends AFSBaseVisitor<ExprFunctionCallNode> {
    @Override
    public ExprFunctionCallNode visitFuncCall(AFSParser.FuncCallContext ctx) {
        String id = ctx.ID().getText();
        ExprIdentifierNode identifier = new ExprIdentifierNode(id);

        ExprParser exprParser = new ExprParser();

        List<ExprNode> expressions = new ArrayList<>();
        for (AFSParser.ExprContext exprCtx : ctx.expr()) {
            ExprNode expression = exprCtx.accept(exprParser);
            expressions.add(expression);
        }

        return new ExprFunctionCallNode(identifier, expressions);
    }
}
