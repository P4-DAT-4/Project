package afs.astbuilder.visitor;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.nodes.def.Param;

public interface ParamVisitor extends ASTVisitor {
    AFSType visitParam(Param node);
}
