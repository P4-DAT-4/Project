package afs.astbuilder.visitor;

import afs.astbuilder.nodes.def.DefDeclarationNode;
import afs.astbuilder.nodes.def.DefFunctionNode;
import afs.astbuilder.nodes.def.DefVisualizeNode;

public interface DefVisitor extends ASTVisitor {
    void visitDefDeclarationNode(DefDeclarationNode node);
    void visitDefFunctionNode(DefFunctionNode node);
    void visitDefVisualizeNode(DefVisualizeNode node);
}
