package afs.interpreter;

import afs.interpreter.interfaces.*;
import afs.nodes.def.*;
import afs.nodes.stmt.StmtFunctionCallNode;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.List;

public class DefInterpreter {
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
                var exprResult = new ExprInterpreter().evalExpr(envV, envF, envE, location, expr, store, imgStore);
                Object value = exprResult.getValue0();
                var store2 = exprResult.getValue1();
                var imgStore2 = exprResult.getValue2();

                // Update the environment
                envV.declare(varName, location);

                // Update the store
                store2.store(location, value);

                // Get next location
                int nextLocation = store2.nextLocation();

                // Evaluate the definition and return
                yield evalDef(envV, envF, envE, nextLocation, nextDef, store2, imgStore2);
            }
            case DefFunctionNode defFunctionNode -> {
                String varName = defFunctionNode.getIdentifier();
                List<Param> params = defFunctionNode.getParameters();
                // declare() requires list of strings
                List<String> paramNames = params.stream().map(Param::getIdentifier).toList();
                var body = defFunctionNode.getStatement();
                var nextDef = defFunctionNode.getDefinition();

                // Declare function environment
                envF.declare(varName, new Triplet<>(body, paramNames, envV));

                // Evaluate the definition and return
                yield evalDef(envV, envF, envE, location, nextDef, store, imgStore);
            }
            case DefVisualizeNode defVisualizeNode -> {
                String funName = defVisualizeNode.getIdentifier();
                var args = defVisualizeNode.getArguments();
                var nextEvent = defVisualizeNode.getEvent();

                // Update the event environment
                var updatedEnvE = new EventInterpreter().evalEvent(nextEvent, envE);

                // Create function call
                var functionCallAsStmt = new StmtFunctionCallNode(funName, args, -1, -1);

                // Evaluate function body
                var result = new StmtInterpreter().evalStmt(envV, envF, updatedEnvE, location, functionCallAsStmt, store, imgStore);
                var store2 = result.getValue1();
                var imgStore2 = result.getValue2();

                // Return the stores
                yield Pair.with(store2, imgStore2);
            }
        };
    }
}