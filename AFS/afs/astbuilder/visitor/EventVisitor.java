package afs.astbuilder.visitor;

import afs.astbuilder.nodes.event.EventCompositionNode;
import afs.astbuilder.nodes.event.EventDeclarationNode;

public interface EventVisitor extends ASTVisitor {
    void visitEventCompositionNode(EventCompositionNode node);
    void visitEventDeclarationNode(EventDeclarationNode node);
}
