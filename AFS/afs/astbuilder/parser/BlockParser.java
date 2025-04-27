package afs.astbuilder.parser;

import afs.AFSBaseVisitor;
import afs.AFSParser;
import afs.astbuilder.nodes.stmt.*;

import java.util.ArrayList;
import java.util.List;

public class BlockParser extends AFSBaseVisitor<StmtNode> {
    private final StmtParser stmtParser = new StmtParser();

    @Override
    public StmtNode visitBlockScope(AFSParser.BlockScopeContext ctx) {
        List<AFSParser.StmtContext> stmtContexts = ctx.stmt();
        return createBlockAndTree(stmtContexts);
    }

    private StmtNode createBlockAndTree(List<AFSParser.StmtContext> stmtContexts) {
        List<StmtDeclarationNode> declarations = new ArrayList<>();
        List<AFSParser.StmtContext> otherStatementContexts = new ArrayList<>();

        for (AFSParser.StmtContext stmtCtx : stmtContexts) {
            if (stmtCtx instanceof AFSParser.StmtDefContext) {
                StmtDeclarationNode declaration = (StmtDeclarationNode)stmtCtx.accept(stmtParser);
                declarations.add(declaration);
            } else {
                otherStatementContexts.add(stmtCtx);
            }
        }

        StmtNode tree = buildStmtTree(otherStatementContexts);

        if (!declarations.isEmpty()) {
            return new StmtBlockNode(declarations, tree);
        } else {
            return tree;
        }
    }

    private StmtNode buildStmtTree(List<AFSParser.StmtContext> stmtContexts) {
        if (stmtContexts.isEmpty()) {
            return new StmtSkipNode(); // If there are no statements, return a skip statement
        }

        StmtNode leftStatement = stmtContexts.getFirst().accept(stmtParser);

        if (stmtContexts.size() > 1) {
            List<AFSParser.StmtContext> rest = stmtContexts.subList(1, stmtContexts.size());
            StmtNode rightStatement = buildStmtTree(rest);
            return new StmtCompositionNode(leftStatement, rightStatement);
        }

        return leftStatement;
    }
}
