package afs.astbuilder.nodes;

public abstract class AbstractSyntaxNode {

    @Override
    public String toString() {
        return getClass().getName();
    }
}
