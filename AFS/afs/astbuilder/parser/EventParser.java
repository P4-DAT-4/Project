package afs.astbuilder.parser;

import afs.AFSBaseVisitor;
import afs.AFSParser;
import afs.astbuilder.nodes.event.EventDeclarationNode;
import afs.astbuilder.nodes.event.EventNode;
import afs.astbuilder.nodes.expr.ExprFunctionCallNode;
import afs.astbuilder.nodes.expr.ExprNode;

public class EventParser extends AFSBaseVisitor<EventNode> {
    @Override
    public EventNode visitEventFunc(AFSParser.EventFuncContext ctx) {
        ExprParser exprParser = new ExprParser();
        ExprNode expression = ctx.expr().accept(exprParser);

        FuncCallParser funcCallParser = new FuncCallParser();
        ExprFunctionCallNode functionCall = ctx.funcCall().accept(funcCallParser);

        return new EventDeclarationNode(expression, functionCall);
    }
}
