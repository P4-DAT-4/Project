package afs.astbuilder.nodes.stmt;

import afs.astbuilder.nodes.def.DefDeclarationNode;
import afs.astbuilder.visitor.StmtVisitor;

import java.util.List;
import java.util.stream.Collectors;

public class StmtDeclarationNode extends StmtNode {
    private final List<DefDeclarationNode> declarations;
    private final StmtNode statement;

    public StmtDeclarationNode(List<DefDeclarationNode> declarations, StmtNode statement) {
        this.declarations = declarations;
        this.statement = statement;
    }

    public List<DefDeclarationNode> getDeclarations() {
        return declarations;
    }

    public StmtNode getStatement() {
        return statement;
    }

    @Override
    public void acceptVisit(StmtVisitor visitor) {
        visitor.visitStmtDeclarationNode(this);
    }

    @Override
    public String toString() {
        return String.format("StmtDeclaration (%s %s)", declarations.stream().map(DefDeclarationNode::toString).collect(Collectors.joining(",")), statement);
    }
}
