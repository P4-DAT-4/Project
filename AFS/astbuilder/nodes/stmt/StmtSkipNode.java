package astbuilder.nodes.stmt;

import astbuilder.visitor.StmtVisitor;

public class StmtSkipNode extends StmtNode {
    @Override
    public void acceptVisit(StmtVisitor visitor) {
        visitor.visitStmtSkipNode(this);
    }

    @Override
    public String toString() {
        return "StmtSkip";
    }
}
