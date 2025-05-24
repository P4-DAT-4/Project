package afs.interpreter;

import afs.interpreter.expressions.Val;
import afs.interpreter.interfaces.*;
import afs.nodes.expr.ExprFunctionCallNode;
import afs.nodes.expr.ExprNode;
import org.javatuples.Pair;

public class EventHandler {
    public static Pair<Store, ImgStore> check(VarEnvironment envV, FunEnvironment envF, EventEnvironment envE, int location, String ident, Store store, ImgStore imgStore) {
        if (!envE.check(ident)) {
            return new Pair<>(store, imgStore); // If x not in dom(Psi) return the store and imgStore
        }

        // Call the function
        ExprFunctionCallNode functionCall = envE.lookup(ident);
        // Get the image
        System.out.printf("Event triggered, calling '%s' \n", functionCall.getIdentifier());
        Val img = ExprInterpreter.evalExpr(envV, envF, envE, location, functionCall, store, imgStore).getValue0();

        // Put the image into the imgStore
        imgStore.push(img.asShapeVal());
        return new Pair<>(store, imgStore);
    }
}
