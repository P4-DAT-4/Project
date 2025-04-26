package afs.astbuilder.visitor;

import afs.astbuilder.nodes.prog.ProgNode;

public interface ProgVisitor extends ASTVisitor {
    void visitProgNode(ProgNode node);
}
