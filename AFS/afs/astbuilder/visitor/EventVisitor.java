package afs.astbuilder.visitor;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.nodes.event.EventCompositionNode;
import afs.astbuilder.nodes.event.EventDeclarationNode;

public interface EventVisitor extends ASTVisitor {
    AFSType visitEventCompositionNode(EventCompositionNode node);
    AFSType visitEventDeclarationNode(EventDeclarationNode node);
}
