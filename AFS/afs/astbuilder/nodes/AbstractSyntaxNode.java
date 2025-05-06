package afs.astbuilder.nodes;

import afs.astbuilder.checker.types.AFSType;

public abstract class AbstractSyntaxNode {
    protected AFSType type = null;
    protected int lineNumber;
    protected int columnNumber;    

    @Override
    public String toString() {
        return getClass().getName();
    }
}
