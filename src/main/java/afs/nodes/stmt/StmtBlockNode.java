package afs.nodes.stmt;

public class StmtBlockNode extends StmtNode {
    private final StmtDeclarationNode declaration;
    private final StmtNode statement;

    public StmtBlockNode(StmtDeclarationNode declaration, StmtNode statement, int line, int column) {
        super(line, column);
        this.declaration = declaration;
        this.statement = statement;
    }

    public StmtDeclarationNode getDeclaration() {
        return declaration;
    }

    public StmtNode getStatement() {
        return statement;
    }

    @Override
    public String toString() {
        return "{arrow(T x = e); S}";
    }
}
