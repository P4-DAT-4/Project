package afs.semantic_analysis;

import afs.nodes.def.DefDeclarationNode;
import afs.nodes.def.DefFunctionNode;
import afs.nodes.def.DefNode;
import afs.nodes.def.DefVisualizeNode;
import afs.nodes.prog.ProgNode;
import afs.nodes.stmt.*;
import afs.nodes.type.TypeVoidNode;
import afs.semantic_analysis.exceptions.MissingReturnException;
import afs.semantic_analysis.exceptions.UnreachableCodeException;

public class ReturnChecker {

    public void checkReturn(ProgNode program) {
        checkDefinition(program.getDefinition());
    }

    private void checkDefinition(DefNode def) {
        switch (def) {
            case DefDeclarationNode defDeclarationNode -> {
                checkDefinition(defDeclarationNode.getDefinition());
            }
            case DefFunctionNode defFunctionNode -> {
                if (!(defFunctionNode.getType() instanceof TypeVoidNode)) {
                    boolean functionValid = checkStatement(defFunctionNode.getStatement());
                    if (!functionValid) {
                        missingReturn(defFunctionNode);
                    }
                }
                checkDefinition(defFunctionNode.getDefinition());
            }
            case DefVisualizeNode ignored -> {}
        }
    }

    private boolean checkStatement(StmtNode stmt) {
        switch (stmt) {
            case StmtAssignmentNode ignored -> {
                return false;
            }
            case StmtListAssignmentNode ignored -> {
                return false;
            }
            case StmtFunctionCallNode ignored -> {
                return false;
            }
            case StmtReturnNode ignored -> {
                return true;
            }
            case StmtSkipNode ignored -> {
                return false;
            }
            case StmtCompositionNode stmtCompositionNode -> {
                boolean left = checkStatement(stmtCompositionNode.getLeftStatement());
                boolean right = checkStatement(stmtCompositionNode.getRightStatement());
                if (left) {
                    unreachableCode(stmtCompositionNode.getRightStatement());
                }
                return right;
            }
            case StmtDeclarationNode stmtDeclarationNode -> {
                return checkStatement(stmtDeclarationNode.getStatement());
            }
            case StmtIfNode stmtIfNode -> {
                boolean left = checkStatement(stmtIfNode.getLeftStatement());
                boolean right = checkStatement(stmtIfNode.getRightStatement());
                return left && right;
            }
            case StmtWhileNode stmtWhileNode -> {
                return checkStatement(stmtWhileNode.getStatement());
            }
        }
    }

    private void unreachableCode(StmtNode stmt) {
        int line = stmt.getLineNumber();
        int column = stmt.getColumnNumber();
        throw new UnreachableCodeException(String.format("Code after a return is unreachable: line %d, column %d", line, column));
    }

    private void missingReturn(DefFunctionNode defFunctionNode) {
        String ident = defFunctionNode.getIdentifier();
        int line = defFunctionNode.getLineNumber();
        int column = defFunctionNode.getColumnNumber();
        throw new MissingReturnException(String.format("Missing return statement in function '%s', line %d, column %d", ident, line, column));
    }
}
