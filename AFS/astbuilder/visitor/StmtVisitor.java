package astbuilder.visitor;

import astbuilder.nodes.stmt.*;

public interface StmtVisitor extends ASTVisitor {
    void visitStmtAssignmentNode(StmtAssignmentNode node);
    void visitStmtCompositionNode(StmtCompositionNode node);
    void visitStmtDeclarationNode(StmtDeclarationNode node);
    void visitStmtIfNode(StmtIfNode node);
    void visitStmtReturnNode(StmtReturnNode node);
    void visitStmtSkipNode(StmtSkipNode node);
    void visitStmtWhileNode(StmtWhileNode node);
}
