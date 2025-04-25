package astbuilder.nodes.stmt;

import astbuilder.visitor.StmtVisitor;

public class StmtAssignmentNode extends StmtNode {
    @Override
    public void acceptVisit(StmtVisitor visitor) {
        visitor.visitStmtAssignmentNode(this);
    }
}
