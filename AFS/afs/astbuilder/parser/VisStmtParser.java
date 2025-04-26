package afs.astbuilder.parser;

import afs.AFSBaseVisitor;
import afs.AFSParser;
import afs.astbuilder.nodes.event.EventCompositionNode;
import afs.astbuilder.nodes.event.EventNode;

import java.util.List;

public class VisStmtParser extends AFSBaseVisitor<EventNode> {
    private final EventParser eventParser = new EventParser();

    @Override
    public EventNode visitVisStmt(AFSParser.VisStmtContext ctx) {
        List<AFSParser.EventContext> eventContexts = ctx.event();

        return buildEventTree(eventContexts);
    }

    private EventNode buildEventTree(List<AFSParser.EventContext> eventContexts) {
        if (eventContexts.isEmpty()) {
            return null; // Should probably throw error as we dont want to have a null thingy in the tree
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
