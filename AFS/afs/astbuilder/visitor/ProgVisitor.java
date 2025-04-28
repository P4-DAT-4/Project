package afs.astbuilder.visitor;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.nodes.prog.ProgNode;

public interface ProgVisitor extends ASTVisitor {
    AFSType visitProgNode(ProgNode node);
}
