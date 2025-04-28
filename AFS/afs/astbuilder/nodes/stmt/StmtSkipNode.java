package afs.astbuilder.nodes.stmt;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.visitor.StmtVisitor;

public class StmtSkipNode extends StmtNode {
    @Override
    public AFSType acceptVisit(StmtVisitor visitor) {
        return visitor.visitStmtSkipNode(this);
    }

    @Override
    public String toString() {
        return "Skip";
    }
}
