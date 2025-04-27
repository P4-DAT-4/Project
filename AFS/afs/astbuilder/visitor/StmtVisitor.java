package afs.astbuilder.visitor;

import afs.astbuilder.nodes.stmt.*;

public interface StmtVisitor extends ASTVisitor {
    void visitStmtAssignmentNode(StmtAssignmentNode node);
    void visitStmtCompositionNode(StmtCompositionNode node);
    void visitStmtBlockNode(StmtBlockNode node);
    void visitStmtIfNode(StmtIfNode node);
    void visitStmtReturnNode(StmtReturnNode node);
    void visitStmtSkipNode(StmtSkipNode node);
    void visitStmtWhileNode(StmtWhileNode node);
    void visitStmtFunctionCallNode(StmtFunctionCallNode node);
    void visitStmtDeclarationNode(StmtDeclarationNode node);
}
