package afs.interpreter;

import afs.nodes.event.EventCompositionNode;
import afs.nodes.event.EventDeclarationNode;
import afs.nodes.event.EventNode;
import afs.nodes.expr.ExprFunctionCallNode;
import afs.nodes.expr.ExprNode;

import java.util.List;

public class EventInterpreter {
    public EventEnvironment evalEvent(EventNode event, EventEnvironment envE) {
        return switch (event) {
            case EventCompositionNode eventCompositionNode -> {
                var e1 = eventCompositionNode.getLeftEvent();
                var e2 = eventCompositionNode.getRightEvent();

                // Apply e1
                var updatedEnvE = evalEvent(e1, envE);

                // Apply e2 based on e1
                yield evalEvent(e2, updatedEnvE);
            }
            case EventDeclarationNode eventDeclarationNode -> {
                String varName = eventDeclarationNode.getIdent();
                String funcName = eventDeclarationNode.getFunIdent();
                List<ExprNode> args = eventDeclarationNode.getArguments();

                // create function call
                var functionCallAsExpr = new ExprFunctionCallNode(funcName, args, -1, -1);

                // Declare the environment
                envE.declare(varName, functionCallAsExpr);

                // Return the updated environment
                yield envE;
            }
        };
    }
}
