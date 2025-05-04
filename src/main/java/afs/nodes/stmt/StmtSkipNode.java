package afs.nodes.stmt;

public class StmtSkipNode extends StmtNode {

    public StmtSkipNode() {
        super(0, 0);
    }

    @Override
    public int getLineNumber() {
        throw new UnsupportedOperationException("Skip does not have a line number.");
    }

    @Override
    public int getColumnNumber() {
        throw new UnsupportedOperationException("Skip does not have a column number.");
    }

    @Override
    public String toString() {
        return "Skip";
    }
}
