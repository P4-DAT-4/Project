package afs.astbuilder.parser;

import afs.AFSBaseVisitor;
import afs.AFSParser;
import afs.astbuilder.nodes.stmt.*;

import java.util.ArrayList;
import java.util.List;

public class BlockParser extends AFSBaseVisitor<StmtNode> {
    @Override
    public StmtNode visitDeclBlock(AFSParser.DeclBlockContext ctx) {
        DeclStmtParser declStmtParser = new DeclStmtParser();

        List<AFSParser.DeclStmtContext> stmtContexts = ctx.declStmt();
        List<StmtNode> statements = stmtContexts.stream().map(stmt -> stmt.accept(declStmtParser)).toList();
        return createBlockAndTree(statements);
    }

    @Override
    public StmtNode visitImpBlock(AFSParser.ImpBlockContext ctx) {
        ImpStmtParser impStmtParser = new ImpStmtParser();

        List<AFSParser.ImpStmtContext> stmtContexts = ctx.impStmt();
        List<StmtNode> statements = stmtContexts.stream().map(stmt -> stmt.accept(impStmtParser)).toList();
        return createBlockAndTree(statements);
    }

    private StmtNode createBlockAndTree(List<StmtNode> statements) {
        List<StmtDeclarationNode> declarations = new ArrayList<>();
        List<StmtNode> otherStatementContexts = new ArrayList<>();

        for (StmtNode statement : statements) {
            if (statement instanceof StmtDeclarationNode) {
                declarations.add((StmtDeclarationNode) statement);
            } else {
                otherStatementContexts.add(statement);
            }
        }

        StmtNode tree = buildStmtTree(otherStatementContexts);

        if (!declarations.isEmpty()) {
            return new StmtBlockNode(declarations, tree);
        } else {
            return tree;
        }
    }

    private StmtNode buildStmtTree(List<StmtNode> stmtContexts) {
        if (stmtContexts.isEmpty()) {
            return new StmtSkipNode(); // If there are no statements, return a skip statement
        }

        StmtNode leftStatement = stmtContexts.getFirst();

        if (stmtContexts.size() > 1) {
            List<StmtNode> rest = stmtContexts.subList(1, stmtContexts.size());
            StmtNode rightStatement = buildStmtTree(rest);
            return new StmtCompositionNode(leftStatement, rightStatement);
        }

        return leftStatement;
    }
}
