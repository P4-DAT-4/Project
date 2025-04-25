package astbuilder.nodes.stmt;

import astbuilder.visitor.StmtVisitor;

public class StmtReturnNode extends StmtNode {
    @Override
    public void acceptVisit(StmtVisitor visitor) {
        visitor.visitStmtReturnNode(this);
    }
}
