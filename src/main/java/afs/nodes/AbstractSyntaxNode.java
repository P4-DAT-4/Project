package afs.nodes;

import afs.checker.types.AFSType;

public abstract class AbstractSyntaxNode {
    private AFSType type = null;
    protected int lineNumber;
    protected int columnNumber;

    protected AbstractSyntaxNode(int lineNumber, int columnNumber) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public AFSType getAFSType() {
        return type;
    }

    public void setType(AFSType type) {
        if (this.type != null) {
            throw new IllegalStateException("Type already set.");
        }
        this.type = type;
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
