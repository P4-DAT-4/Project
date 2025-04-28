package afs.astbuilder.nodes;
import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.visitor.ASTVisitor;

public abstract class AbstractSyntaxNode<T extends ASTVisitor> {

    public abstract AFSType acceptVisit(T visitor);

    @Override
    public String toString() {
        return getClass().getName();
    }
}
