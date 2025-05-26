package afs.interpreter;

import afs.interpreter.expressions.ListVal;
import afs.interpreter.expressions.Val;
import afs.interpreter.implementations.MapVarEnvironment;
import afs.interpreter.interfaces.*;
import afs.nodes.def.*;
import afs.nodes.expr.ExprNode;
import afs.nodes.stmt.StmtFunctionCallNode;
import afs.nodes.type.TypeNode;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.List;

public class DefInterpreter {

    public static Pair<Store, ImgStore> evalDef(VarEnvironment envV,
                                                FunEnvironment envF,
                                                EventEnvironment envE,
                                                int location,
                                                DefNode def,
                                                Store store,
                                                ImgStore imgStore) {
        return switch (def) {
            case DefDeclarationNode defDeclarationNode -> {
                String varName = defDeclarationNode.getIdentifier();
                var expr = defDeclarationNode.getExpression();
                var nextDef = defDeclarationNode.getDefinition();
                System.out.println("Evaluating DefDeclarationNode for " + varName + ", expr: " + expr + ", nextDef: " + nextDef);

                Val value = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore).getValue0();
                System.out.println("Expression value: " + value);

                // Evaluate the expression
                int newLocation = store.nextLocation();
                store.declare(newLocation, value);
                // Allocate a new location
                envV.declare(varName, newLocation);
                System.out.println("Bound " + varName + " to location " + newLocation);

                // Evaluate the definition and return
                yield evalDef(envV, envF, envE, newLocation, nextDef, store, imgStore);
            }
            case DefFunctionNode defFunctionNode -> {
                String varName = defFunctionNode.getIdentifier();
                List<Param> params = defFunctionNode.getParameters();
                // declare() requires list of strings
                List<String> paramNames = params.stream().map(Param::getIdentifier).toList();

                var body = defFunctionNode.getStatement();
                var nextDef = defFunctionNode.getDefinition();

                // Declare function environment, give it a new environment
                envF.declare(varName, new Triplet<>(body, paramNames, envV.newScope()));

                // Evaluate the definition and return
                yield evalDef(envV, envF, envE, location, nextDef, store, imgStore);
            }
            case DefVisualizeNode defVisualizeNode -> {
                String funName = defVisualizeNode.getIdentifier();
                List<ExprNode> args = defVisualizeNode.getArguments();
                var nextEvent = defVisualizeNode.getEvent();

                // Update the event environment
                var updatedEnvE = EventInterpreter.evalEvent(nextEvent, envE);


                // Create function call
                var functionCallAsStmt = new StmtFunctionCallNode(funName, args, -1, -1);

                // Evaluate function body
                StmtInterpreter.evalStmt(envV, envF, updatedEnvE, location, functionCallAsStmt, store, imgStore);

                // Return the stores
                yield Pair.with(store, imgStore);
            }
        };
    }
}