package astbuilder.nodes.stmt;

import astbuilder.visitor.StmtVisitor;

public class StmtWhileNode extends StmtNode {
    @Override
    public void acceptVisit(StmtVisitor visitor) {
        visitor.visitStmtWhileNode(this);
    }
}
