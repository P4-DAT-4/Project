package astbuilder.nodes.stmt;

import astbuilder.builder.StmtVisitor;

public class StmtReturnNode extends StmtNode {
    @Override
    public void acceptVisit(StmtVisitor visitor) {
        visitor.visitStmtReturnNode(this);
    }
}
