package astbuilder.builder;

import astbuilder.nodes.def.DefDeclarationNode;
import astbuilder.nodes.def.DefFunctionNode;
import astbuilder.nodes.def.DefVisualizeNode;

public interface DefVisitor extends ASTVisitor {
    void visitDefDeclarationNode(DefDeclarationNode node);
    void visitDefFunctionNode(DefFunctionNode node);
    void visitDefVisualizeNode(DefVisualizeNode node);
}
