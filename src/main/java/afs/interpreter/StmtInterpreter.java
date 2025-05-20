package afs.interpreter;

import afs.nodes.stmt.*;
import org.javatuples.Triplet;

public class StmtInterpreter {
    public Triplet<Object, Store, ImgStore> evalStmt(VarEnvironment envV,
                                                     FunEnvironment envF,
                                                     EventEnvironment envE,
                                                     int location,
                                                     StmtNode stmt,
                                                     Store store,
                                                     ImgStore imgStore) {
        return switch (stmt) {
            case StmtAssignmentNode stmtAssignmentNode -> {
                String varName = stmtAssignmentNode.getIdentifier();
                var exprNode = stmtAssignmentNode.getExpression();

                // Evaluate the expression
                var exprResult = new ExprInterpreter().evalExpr(envV, envF, envE, location, exprNode, store, imgStore);
                Object value = exprResult.getValue0();
                var store2 = exprResult.getValue1();
                var imgStore2 = exprResult.getValue2();

                // Get location of variable
                int varLocation = envV.lookup(varName);

                // Update store
                store2.store(varLocation, value);

                // Return
                yield new Triplet<>(null, store2, imgStore2);
            }
            case StmtCompositionNode stmtCompositionNode -> {
                var s1 = stmtCompositionNode.getLeftStatement();
                var s2 = stmtCompositionNode.getRightStatement();

                var r1 = evalStmt(envV, envF, envE, location, s1, store, imgStore);
                var store2 = r1.getValue1();
                var imgStore2 = r1.getValue2();

                yield evalStmt(envV, envF, envE, location, s2, store2, imgStore2);
            }
            case StmtDeclarationNode stmtDeclarationNode -> null;
            case StmtFunctionCallNode stmtFunctionCallNode -> null;
            case StmtIfNode stmtIfNode -> null;
            case StmtListAssignmentNode stmtListAssignmentNode -> null;
            case StmtReturnNode stmtReturnNode -> null;
            case StmtSkipNode stmtSkipNode -> new Triplet<>(null, store, imgStore);
            case StmtWhileNode stmtWhileNode -> null;
        };
    }
}