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

    private final StmtInterpreter stmtInterpreter;
    private final ExprInterpreter exprInterpreter;
    private final EventInterpreter eventInterpreter;

    public DefInterpreter(StmtInterpreter stmtInterpreter, ExprInterpreter exprInterpreter, EventInterpreter eventInterpreter) {
        this.stmtInterpreter = stmtInterpreter;
        this.exprInterpreter = exprInterpreter;
        this.eventInterpreter = eventInterpreter;
    }


    public Pair<Store, ImgStore> evalDef(VarEnvironment envV,
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

                // Evaluate the expression
                var exprResult = exprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore);
                Val value = (Val)exprResult.getValue0();


                // Handle list values specially for reference semantics
                if (value instanceof ListVal listVal) {
                    store.store(location, listVal);
                } else {
                    store.store(location, value.copy());
                }

                // Update the environment
                envV.declare(varName, location);

                // Get next location
                int nextLocation = store.nextLocation();

                // Evaluate the definition and return
                yield evalDef(envV, envF, envE, nextLocation, nextDef, store, imgStore);
            }
            case DefFunctionNode defFunctionNode -> {
                String varName = defFunctionNode.getIdentifier();
                List<Param> params = defFunctionNode.getParameters();
                // declare() requires list of strings
                List<String> paramNames = params.stream().map(Param::getIdentifier).toList();

                var body = defFunctionNode.getStatement();
                var nextDef = defFunctionNode.getDefinition();

                // Create a fresh environment for the function
                VarEnvironment funcDeclEnv = new MapVarEnvironment();


                // Declare function environment
                envF.declare(varName, new Triplet<>(body, paramNames,funcDeclEnv));

                // Evaluate the definition and return
                yield evalDef(envV, envF, envE, location, nextDef, store, imgStore);
            }
            case DefVisualizeNode defVisualizeNode -> {
                String funName = defVisualizeNode.getIdentifier();
                List<ExprNode> args = defVisualizeNode.getArguments();
                var nextEvent = defVisualizeNode.getEvent();

                // Update the event environment
                var updatedEnvE = eventInterpreter.evalEvent(nextEvent, envE);


                // Create function call
                var functionCallAsStmt = new StmtFunctionCallNode(funName, args, -1, -1);

                // Evaluate function body
                var result = stmtInterpreter.evalStmt(envV, envF, updatedEnvE, location, functionCallAsStmt, store, imgStore);


                // Return the stores
                yield Pair.with(store, imgStore);
            }
        };
    }
}