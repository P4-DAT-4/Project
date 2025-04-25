package astbuilder.builder;

import astbuilder.nodes.prog.ProgNode;

public interface ProgVisitor extends ASTVisitor {
    void visitProgNode(ProgNode node);
}
