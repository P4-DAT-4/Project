package astbuilder.visitor;

import astbuilder.nodes.AbstractSyntaxNode;

public interface ASTVisitor {
    void visitNode(AbstractSyntaxNode<ASTVisitor> node);
}
