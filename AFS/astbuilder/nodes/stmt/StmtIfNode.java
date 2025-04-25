package astbuilder.nodes.stmt;

import astbuilder.visitor.StmtVisitor;

public class StmtIfNode extends StmtNode {
    @Override
    public void acceptVisit(StmtVisitor visitor) {
        visitor.visitStmtIfNode(this);
    }
}
