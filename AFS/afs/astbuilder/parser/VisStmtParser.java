package afs.astbuilder.parser;

import afs.AFSBaseVisitor;
import afs.AFSParser;
import afs.astbuilder.nodes.def.DefNode;
import afs.astbuilder.nodes.def.DefVisualizeNode;
import afs.astbuilder.nodes.event.EventCompositionNode;
import afs.astbuilder.nodes.event.EventNode;
import afs.astbuilder.nodes.expr.ExprFunctionCallNode;

import java.util.List;

public class VisStmtParser extends AFSBaseVisitor<DefNode> {
    private final EventParser eventParser = new EventParser();

    @Override
    public DefNode visitVisStmt(AFSParser.VisStmtContext ctx) {
        FuncCallParser funcCallParser = new FuncCallParser();
        ExprFunctionCallNode functionCall = ctx.funcCall().accept(funcCallParser);

        List<AFSParser.EventContext> eventContexts = ctx.event();
        EventNode event = buildEventTree(eventContexts);

        return new DefVisualizeNode(functionCall, event);
    }

    private EventNode buildEventTree(List<AFSParser.EventContext> eventContexts) {
        if (eventContexts.isEmpty()) {
            return null; // Should probably throw error as we dont want to have on object of type null in the tree
        }

        EventNode left = eventContexts.getFirst().accept(eventParser);

        if (eventContexts.size() > 1) {
            List<AFSParser.EventContext> rest = eventContexts.subList(1, eventContexts.size());
            EventNode right = buildEventTree(rest);
            return new EventCompositionNode(left, right);
        }

        return left;
    }
}
