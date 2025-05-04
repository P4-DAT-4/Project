package afs.nodes;

public abstract class AbstractSyntaxNode {
    protected int lineNumber;
    protected int columnNumber;

    protected AbstractSyntaxNode(int lineNumber, int columnNumber) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    @Override
    public String toString() {
        return getClass().getName();
    }
}
