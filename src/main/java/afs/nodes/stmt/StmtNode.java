package afs.nodes.stmt;

import afs.nodes.AbstractSyntaxNode;

public sealed abstract class StmtNode extends AbstractSyntaxNode permits StmtAssignmentNode, StmtBlockNode, StmtCompositionNode, StmtDeclarationNode, StmtFunctionCallNode, StmtIfNode, StmtReturnNode, StmtSkipNode, StmtWhileNode {
    protected StmtNode(int lineNumber, int columnNumber) {
        super(lineNumber, columnNumber);
    }
}
