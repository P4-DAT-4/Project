package afs.astbuilder.nodes.stmt;

public class StmtCompositionNode extends StmtNode {
    private final StmtNode leftStatement;
    private final StmtNode rightStatement;

    public StmtCompositionNode(StmtNode leftStatement, StmtNode rightStatement) {
        this.leftStatement = leftStatement;
        this.rightStatement = rightStatement;
    }

    public StmtNode getLeftStatement() {
        return leftStatement;
    }

    public StmtNode getRightStatement() {
        return rightStatement;
    }

    @Override
    public String toString() {
        return "S1;S2";
    }
}
