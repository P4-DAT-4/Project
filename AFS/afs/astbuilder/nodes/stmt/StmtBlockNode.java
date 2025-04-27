package afs.astbuilder.nodes.stmt;

import afs.astbuilder.nodes.def.DefDeclarationNode;
import afs.astbuilder.visitor.StmtVisitor;

import java.util.List;
import java.util.stream.Collectors;

public class StmtBlockNode extends StmtNode {
    private final List<StmtDeclarationNode> declarations;
    private final StmtNode statement;

    public StmtBlockNode(List<StmtDeclarationNode> declarations, StmtNode statement) {
        this.declarations = declarations;
        this.statement = statement;
    }

    public List<StmtDeclarationNode> getDeclarations() {
        return declarations;
    }

    public StmtNode getStatement() {
        return statement;
    }

    @Override
    public void acceptVisit(StmtVisitor visitor) {
        visitor.visitStmtBlockNode(this);
    }

    @Override
    public String toString() {
        return String.format("StmtDeclaration (%s %s)", declarations.stream().map(StmtDeclarationNode::toString).collect(Collectors.joining(",")), statement);
    }
}
