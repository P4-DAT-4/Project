package afs.astbuilder.visitor;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.nodes.stmt.*;

public interface StmtVisitor extends ASTVisitor {
    AFSType visitStmtAssignmentNode(StmtAssignmentNode node);
    AFSType visitStmtCompositionNode(StmtCompositionNode node);
    AFSType visitStmtBlockNode(StmtBlockNode node);
    AFSType visitStmtIfNode(StmtIfNode node);
    AFSType visitStmtReturnNode(StmtReturnNode node);
    AFSType visitStmtSkipNode(StmtSkipNode node);
    AFSType visitStmtWhileNode(StmtWhileNode node);
    AFSType visitStmtFunctionCallNode(StmtFunctionCallNode node);
    AFSType visitStmtDeclarationNode(StmtDeclarationNode node);
}
