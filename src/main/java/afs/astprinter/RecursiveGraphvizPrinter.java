package afs.astprinter;

import afs.nodes.AbstractSyntaxNode;
import afs.nodes.def.*;
import afs.nodes.event.EventCompositionNode;
import afs.nodes.event.EventDeclarationNode;
import afs.nodes.event.EventNode;
import afs.nodes.expr.*;
import afs.nodes.prog.ProgNode;
import afs.nodes.stmt.*;
import afs.nodes.type.TypeListNode;
import afs.nodes.type.TypeNode;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class RecursiveGraphvizPrinter implements PrinterInterface {
    private StringBuilder nodes = new StringBuilder();
    private StringBuilder edges = new StringBuilder();
    private int counter = 0;

    private void addNode(int nodeId, AbstractSyntaxNode node) {
        nodes.append("    ").append("node").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    private void addEdge(int fromNodeId, int toNodeId) {
        edges.append("    ").append("node").append(fromNodeId).append(" -> ").append("node").append(toNodeId).append(";\n");
    }

    private void addNode(int nodeId, String node) {
        nodes.append("    ").append("node").append(nodeId).append(" [label=\"").append(node).append("\"];\n");
    }

    private java.lang.String getGraphvizCode() {
        StringBuilder graphCode = new StringBuilder();
        graphCode.append("digraph AST {\n"); // Start of the Graphviz DOT format
        graphCode.append("    node [shape=box, style=filled, fillcolor=lightgrey];\n"); // Optional: Styling for nodes
        graphCode.append(nodes.toString()); // Add node definitions
        graphCode.append(edges.toString()); // Add edges
        graphCode.append("}\n"); // End of the Graphviz DOT format
        return graphCode.toString(); // Return the final Graphviz code
    }

    @Override
    public void print(ProgNode program, java.lang.String filePath) {
        int nodeId = counter++;
        addNode(nodeId, program);
        for (var def : program.getDefinitions()) {
            int defId = printDef(def);
            addEdge(nodeId, defId);
        }
        java.lang.String graphvizCode = getGraphvizCode();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(graphvizCode);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write Graphviz output to file: " + filePath, e);
        }
    }

    private int printDef(DefNode def) {
        int nodeId = counter++;
        addNode(nodeId, def);
        switch (def) {
            case DefDeclarationNode node -> {
                int typeId = printType(node.getType());
                addEdge(nodeId, typeId);

                String ident = node.getIdentifier();
                int identId = counter++;
                addNode(identId, ident);
                addEdge(nodeId, identId);

                int exprId = printExpr(node.getExpression());
                addEdge(nodeId, exprId);
            }
            case DefFunctionNode node -> {
                int typeId = printType(node.getType());
                addEdge(nodeId, typeId);

                String ident = node.getIdentifier();
                int identId = counter++;
                addNode(identId, ident);
                addEdge(nodeId, identId);

                for (var param : node.getParameters()) {
                    int paramId = printParam(param);
                    addEdge(nodeId, paramId);
                }

                int stmtId = printStmt(node.getStatement());
                addEdge(nodeId, stmtId);
            }
            case DefVisualizeNode node -> {
                String ident = node.getIdentifier();
                int identId = counter++;
                addNode(identId, ident);
                addEdge(nodeId, identId);

                for (var arg : node.getArguments()) {
                    int argId = printExpr(arg);
                    addEdge(nodeId, argId);
                }

                int eventId = printEvent(node.getEvent());
                addEdge(nodeId, eventId);
            }
        }
        return nodeId;
    }

    private int printParam(Param param) {
        int nodeId = counter++;
        addNode(nodeId, param);

        int typeId = printType(param.getType());
        addEdge(nodeId, typeId);

        String ident = param.getIdentifier();
        int identId = counter++;
        addNode(identId, ident);
        addEdge(nodeId, identId);
        return nodeId;
    }

    private int printEvent(EventNode event) {
        int nodeId = counter++;
        addNode(nodeId, event);
        switch (event) {
            case EventCompositionNode node -> {
                int leftId = printEvent(node.getLeftEvent());
                addEdge(nodeId, leftId);

                int rightId = printEvent(node.getRightEvent());
                addEdge(nodeId, rightId);
            }
            case EventDeclarationNode node -> {
                int exprId = printExpr(node.getExpression());
                addEdge(nodeId, exprId);

                String ident = node.getIdentifier();
                int identId = counter++;
                addNode(identId, ident);
                addEdge(nodeId, identId);

                for (var arg : node.getArguments()) {
                    int argId = printExpr(arg);
                    addEdge(nodeId, argId);
                }
            }
        }
        return nodeId;
    }


    private int printStmt(StmtNode stmt) {
        int nodeId = counter++;
        addNode(nodeId, stmt);
        switch (stmt) {
            case StmtAssignmentNode node -> {
                printIdent(node.getIdentifier(), nodeId);

                int exprId = printExpr(node.getExpression());
                addEdge(nodeId, exprId);
            } case StmtDeclarationNode node -> {
                int typeId = printType(node.getType());
                addEdge(nodeId, typeId);

                String ident = node.getIdentifier();
                int identId = counter++;
                addNode(identId, ident);
                addEdge(nodeId, identId);

                int exprId = printExpr(node.getExpression());
                addEdge(nodeId, exprId);

                int stmtId = printStmt(node.getStatement());
                addEdge(nodeId, stmtId);
            } case StmtCompositionNode node -> {
                int leftId = printStmt(node.getLeftStatement());
                addEdge(nodeId, leftId);

                int rightId = printStmt(node.getRightStatement());
                addEdge(nodeId, rightId);
            } case StmtFunctionCallNode node -> {
                printIdent(node.getIdentifier(), nodeId);

                for (var arg : node.getArguments()) {
                    int argId = printExpr(arg);
                    addEdge(nodeId, argId);
                }
            } case StmtIfNode node -> {
                int condId = printExpr(node.getExpression());
                addEdge(nodeId, condId);

                int thenId = printStmt(node.getLeftStatement());
                addEdge(nodeId, thenId);

                int elseId = printStmt(node.getRightStatement());
                addEdge(nodeId, elseId);
            } case StmtReturnNode node -> {
                int exprId = printExpr(node.getExpression());
                addEdge(nodeId, exprId);
            } case StmtSkipNode ignored -> {
            } case StmtWhileNode node -> {
                int condId = printExpr(node.getExpression());
                addEdge(nodeId, condId);

                int bodyId = printStmt(node.getStatement());
                addEdge(nodeId, bodyId);
            }
            case StmtListAssignmentNode node -> {
                String ident = node.getIdentifier();
                int identId = counter++;
                addNode(identId, ident);
                addEdge(nodeId, identId);

                for (var expr : node.getExpressions()) {
                    int exprId = printExpr(expr);
                    addEdge(nodeId, exprId);
                }

                int exprId = printExpr(node.getExpression());
                addEdge(nodeId, exprId);
            }
        }
        return nodeId;
    }

    private int printExpr(ExprNode expr) {
        int nodeId = counter++;
        addNode(nodeId, expr);
        switch (expr) {
            case ExprBinopNode node -> {
                int leftId = printExpr(node.getLeftExpression());
                addEdge(nodeId, leftId);

                int rightId = printExpr(node.getRightExpression());
                addEdge(nodeId, rightId);
            }
            case ExprBoolNode ignored -> {}
            case ExprDoubleNode ignored -> {}
            case ExprIntNode ignored -> {}
            case ExprStringNode ignored -> {}
            case ExprIdentifierNode ignored -> {}
            case ExprCurveNode node -> {
                for (var exprNode : node.getExpressions()) {
                    int exprId = printExpr(exprNode);
                    addEdge(nodeId, exprId);
                }
            }
            case ExprFunctionCallNode node -> {
                printIdent(node.getIdentifier(), nodeId);

                for (var arg : node.getArguments()) {
                    int argId = printExpr(arg);
                    addEdge(nodeId, argId);
                }
            }
            case ExprLineNode node -> {
                for (var exprNode : node.getExpressions()) {
                    int exprId = printExpr(exprNode);
                    addEdge(nodeId, exprId);
                }
            }
            case ExprListAccessNode node -> {
                printIdent(node.getIdentifier(), nodeId);

                for (var arg : node.getExpressions()) {
                    int argId = printExpr(arg);
                    addEdge(nodeId, argId);
                }
            }
            case ExprListDeclaration node -> {
                for (var exprNode : node.getExpressions()) {
                    int exprId = printExpr(exprNode);
                    addEdge(nodeId, exprId);
                }
            }
            case ExprPlaceNode node -> {
                int leftId = printExpr(node.getLeftExpression());
                addEdge(nodeId, leftId);

                int rightId = printExpr(node.getRightExpression());
                addEdge(nodeId, rightId);
            }
            case ExprRotateNode node -> {
                int leftId = printExpr(node.getLeftExpression());
                addEdge(nodeId, leftId);

                int middleId = printExpr(node.getMiddleExpression());
                addEdge(nodeId, middleId);

                int rightId = printExpr(node.getRightExpression());
                addEdge(nodeId, rightId);
            }
            case ExprScaleNode node -> {
                int leftId = printExpr(node.getLeftExpression());
                addEdge(nodeId, leftId);

                int rightId = printExpr(node.getRightExpression());
                addEdge(nodeId, rightId);
            }
            case ExprTextNode node -> {
                int exprId = printExpr(node.getExpression());
                addEdge(nodeId, exprId);
            }
            case ExprUnopNode node -> {
                int exprId = printExpr(node.getExpression());
                addEdge(nodeId, exprId);
            }
        }
        return nodeId;
    }

    private int printType(TypeNode type) {
        int nodeId = counter++;
        addNode(nodeId, type);
        if (type instanceof TypeListNode) {
            int typeId = printType(((TypeListNode) type).getType());
            addEdge(nodeId, typeId);
        }
        return nodeId;
    }

    private void printIdent(String ident, int nodeId) {
        int identId = counter++;
        addNode(identId, ident);
        addEdge(nodeId, identId);
    }
}
