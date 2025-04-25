package astbuilder.nodes;

import astbuilder.builder.ASTVisitor;

public abstract class AbstractSyntaxNode<T extends ASTVisitor> {

    public abstract void acceptVisit(T visitor);

    @Override
    public String toString() {
        return getClass().getName();
    }
}
