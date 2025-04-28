package afs.astbuilder.visitor;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.nodes.def.DefDeclarationNode;
import afs.astbuilder.nodes.def.DefFunctionNode;
import afs.astbuilder.nodes.def.DefVisualizeNode;

public interface DefVisitor extends ASTVisitor {
    AFSType visitDefDeclarationNode(DefDeclarationNode node);
    AFSType visitDefFunctionNode(DefFunctionNode node);
    AFSType visitDefVisualizeNode(DefVisualizeNode node);
}
