package astbuilder.builder;

import astbuilder.nodes.event.EventCompositionNode;
import astbuilder.nodes.event.EventDeclarationNode;

public interface EventVisitor extends ASTVisitor {
    void visitEventCompositionNode(EventCompositionNode node);
    void visitEventDeclarationNode(EventDeclarationNode node);
}
