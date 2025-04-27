package afs.astbuilder.visitor;

import afs.astbuilder.nodes.def.Param;

public interface ParamVisitor extends ASTVisitor {
    void visitParam(Param node);
}
