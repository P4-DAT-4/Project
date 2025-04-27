// Generated with AI

package afs.astprinter;

import afs.astbuilder.nodes.def.*;
import afs.astbuilder.nodes.event.EventCompositionNode;
import afs.astbuilder.nodes.event.EventDeclarationNode;
import afs.astbuilder.nodes.expr.*;
import afs.astbuilder.nodes.prog.ProgNode;
import afs.astbuilder.nodes.stmt.*;
import afs.astbuilder.nodes.type.*;
import afs.astbuilder.visitor.*;

public class GraphvizVisitor implements ExprVisitor, StmtVisitor, TypeVisitor, EventVisitor, DefVisitor, ProgVisitor, ParamVisitor {
    private int idCounter = 0; // This will keep track of the unique node IDs
    private final StringBuilder edges = new StringBuilder(); // This will accumulate the Graphviz DOT format
    private final StringBuilder nodes = new StringBuilder();

    // Method to get the Graphviz DOT code
    public String getGraphvizCode() {
        // Place nodes before edges to ensure correct node definition
        StringBuilder graphCode = new StringBuilder();
        graphCode.append("digraph AST {\n"); // Start of the Graphviz DOT format
        graphCode.append("    node [shape=box, style=filled, fillcolor=lightgrey];\n"); // Optional: Styling for nodes
        graphCode.append(nodes.toString()); // Add node definitions
        graphCode.append(edges.toString()); // Add edges
        graphCode.append("}\n"); // End of the Graphviz DOT format
        return graphCode.toString(); // Return the final Graphviz code
    }

    @Override
    public void visitExprAdditionNode(ExprAdditionNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String leftId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(leftId).append(";\n");
        node.getLeftExpression().acceptVisit(this);

        String rightId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(rightId).append(";\n");
        node.getRightExpression().acceptVisit(this);
    }

    @Override
    public void visitExprAndNode(ExprAndNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String leftId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(leftId).append(";\n");
        node.getLeftExpression().acceptVisit(this);

        String rightId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(rightId).append(";\n");
        node.getRightExpression().acceptVisit(this);
    }

    @Override
    public void visitExprBoolNode(ExprBoolNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public void visitExprConcatenationNode(ExprConcatenationNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String leftId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(leftId).append(";\n");
        node.getLeftExpression().acceptVisit(this);

        String rightId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(rightId).append(";\n");
        node.getRightExpression().acceptVisit(this);
    }

    @Override
    public void visitExprCurveNode(ExprCurveNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        for (ExprNode expr : node.getExpressions()) {
            String exprId = "node" + idCounter;
            edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
            expr.acceptVisit(this);
        }
    }

    @Override
    public void visitExprDivisionNode(ExprDivisionNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String leftId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(leftId).append(";\n");
        node.getLeftExpression().acceptVisit(this);

        String rightId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(rightId).append(";\n");
        node.getRightExpression().acceptVisit(this);
    }

    public void visitExprDoubleNode(ExprDoubleNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public void visitExprEqualNode(ExprEqualsNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String leftId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(leftId).append(";\n");
        node.getLeftExpression().acceptVisit(this);

        String rightId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(rightId).append(";\n");
        node.getRightExpression().acceptVisit(this);
    }

    @Override
    public void visitExprFunctionCallNode(ExprFunctionCallNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String identifierId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(identifierId).append(";\n");
        node.getIdentifier().acceptVisit(this);

        for (ExprNode expr : node.getExpressions()) {
            String exprId = "node" + idCounter;
            edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
            expr.acceptVisit(this);
        }
    }

    @Override
    public void visitExprIdentifierNode(ExprIdentifierNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public void visitExprIntNode(ExprIntNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public void visitExprLessThanNode(ExprLessThanNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String leftId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(leftId).append(";\n");
        node.getLeftExpression().acceptVisit(this);

        String rightId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(rightId).append(";\n");
        node.getRightExpression().acceptVisit(this);
    }

    @Override
    public void visitExprLineNode(ExprLineNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        for (ExprNode expr : node.getExpressions()) {
            String exprId = "node" + idCounter;
            edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
            expr.acceptVisit(this);
        }
    }

    @Override
    public void visitExprListAccessNode(ExprListAccessNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String leftId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(leftId).append(";\n");
        node.getLeftExpression().acceptVisit(this);

        String rightId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(rightId).append(";\n");
        node.getRightExpression().acceptVisit(this);
    }

    @Override
    public void visitExprListNode(ExprListNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        for (ExprNode expr : node.getExpressions()) {
            String exprId = "node" + idCounter;
            edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
            expr.acceptVisit(this);
        }
    }

    @Override
    public void visitExprMultiplicationNode(ExprMultiplicationNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String leftId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(leftId).append(";\n");
        node.getLeftExpression().acceptVisit(this);

        String rightId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(rightId).append(";\n");
        node.getRightExpression().acceptVisit(this);
    }

    @Override
    public void visitExprNegationNode(ExprNegationNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String exprId = "node" + idCounter;
        node.getExpression().acceptVisit(this);
        edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
    }

    @Override
    public void visitExprNotNode(ExprNotNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String exprId = "node" + idCounter;
        node.getExpression().acceptVisit(this);
        edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
    }

    @Override
    public void visitExprPlaceNode(ExprPlaceNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String leftId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(leftId).append(";\n");
        node.getLeftExpression().acceptVisit(this);

        String rightId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(rightId).append(";\n");
        node.getRightExpression().acceptVisit(this);
    }

    @Override
    public void visitExprRotateNode(ExprRotateNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String leftId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(leftId).append(";\n");
        node.getLeftExpression().acceptVisit(this);

        String middleId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(middleId).append(";\n");
        node.getRightExpression().acceptVisit(this);

        String rightId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(rightId).append(";\n");
        node.getRightExpression().acceptVisit(this);
    }

    @Override
    public void visitExprScaleNode(ExprScaleNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String leftId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(leftId).append(";\n");
        node.getLeftExpression().acceptVisit(this);

        String rightId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(rightId).append(";\n");
        node.getRightExpression().acceptVisit(this);
    }

    @Override
    public void visitExprStringNode(ExprStringNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public void visitExprSubtractionNode(ExprSubtractionNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String leftId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(leftId).append(";\n");
        node.getLeftExpression().acceptVisit(this);

        String rightId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(rightId).append(";\n");
        node.getRightExpression().acceptVisit(this);
    }

    @Override
    public void visitExprTextNode(ExprTextNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String exprId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
        node.getExpression().acceptVisit(this);
    }

    @Override
    public void visitStmtAssignmentNode(StmtAssignmentNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String leftId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(leftId).append(";\n");
        node.getLeftExpression().acceptVisit(this);

        String rightId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(rightId).append(";\n");
        node.getRightExpression().acceptVisit(this);
    }

    @Override
    public void visitStmtCompositionNode(StmtCompositionNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String leftId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(leftId).append(";\n");
        node.getLeftStatement().acceptVisit(this);

        String rightId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(rightId).append(";\n");
        node.getRightStatement().acceptVisit(this);
    }

    @Override
    public void visitStmtBlockNode(StmtBlockNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        for (StmtDeclarationNode declaration : node.getDeclarations()) {
            String declarationId = "node" + idCounter;
            edges.append("    ").append(nodeId).append(" -> ").append(declarationId).append(";\n");
            declaration.acceptVisit(this);
        }

        String statementId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(statementId).append(";\n");
        node.getStatement().acceptVisit(this);
    }

    @Override
    public void visitStmtIfNode(StmtIfNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String exprId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
        node.getExpression().acceptVisit(this);

        String leftId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(leftId).append(";\n");
        node.getLeftStatement().acceptVisit(this);

        String rightId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(rightId).append(";\n");
        node.getRightStatement().acceptVisit(this);
    }

    @Override
    public void visitStmtReturnNode(StmtReturnNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String exprId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
        node.getExpression().acceptVisit(this);
    }

    @Override
    public void visitStmtSkipNode(StmtSkipNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public void visitStmtWhileNode(StmtWhileNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String exprId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
        node.getExpression().acceptVisit(this);

        String statementId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(statementId).append(";\n");
        node.getStatement().acceptVisit(this);
    }

    @Override
    public void visitStmtFunctionCallNode(StmtFunctionCallNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String funcCallId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(funcCallId).append(";\n");
        node.getFunctionCall().acceptVisit(this);
    }

    @Override
    public void visitStmtDeclarationNode(StmtDeclarationNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String typeNodeId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(typeNodeId).append(";\n");
        node.getExpression().acceptVisit(this);

        String identifierNodeId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(identifierNodeId).append(";\n");
        node.getIdentifier().acceptVisit(this);

        String exprId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
        node.getExpression().acceptVisit(this);
    }

    @Override
    public void visitTypeBoolNode(TypeBoolNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public void visitTypeDoubleNode(TypeDoubleNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public void visitTypeIntNode(TypeIntNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public void visitTypeListNode(TypeListNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String typeId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(typeId).append(";\n");
        node.getType().acceptVisit(this);
    }

    @Override
    public void visitTypeShapeNode(TypeShapeNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public void visitTypeStringNode(TypeStringNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public void visitTypeVoidNode(TypeVoidNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public void visitEventCompositionNode(EventCompositionNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String leftEventId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(leftEventId).append(";\n");
        node.getLeftEvent().acceptVisit(this);

        String rightEventId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(rightEventId).append(";\n");
        node.getRightEvent().acceptVisit(this);
    }

    @Override
    public void visitEventDeclarationNode(EventDeclarationNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String exprId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
        node.getExpression().acceptVisit(this);

        String funcCallId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(funcCallId).append(";\n");
        node.getFunctionCall().acceptVisit(this);
    }

    @Override
    public void visitDefDeclarationNode(DefDeclarationNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String typeId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(typeId).append(";\n");
        node.getType().acceptVisit(this);

        String identifierId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(identifierId).append(";\n");
        node.getIdentifier().acceptVisit(this);

        String exprId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
        node.getExpression().acceptVisit(this);
    }

    @Override
    public void visitDefFunctionNode(DefFunctionNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String typeId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(typeId).append(";\n");
        node.getType().acceptVisit(this);

        String identifierId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(identifierId).append(";\n");
        node.getIdentifier().acceptVisit(this);

        for (Param param : node.getParameters()) {
            String paramId = "node" + idCounter;
            edges.append("    ").append(nodeId).append(" -> ").append(paramId).append(";\n");
            param.acceptVisit(this);
        }

        String stmtId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(stmtId).append(";\n");
        node.getStatement().acceptVisit(this);
    }

    @Override
    public void visitParam(Param node){
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String typeId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(typeId).append(";\n");
        node.getType().acceptVisit(this);

        String identifierId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(identifierId).append(";\n");
        node.getIdentifier().acceptVisit(this);
    }

    @Override
    public void visitDefVisualizeNode(DefVisualizeNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String funcId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(funcId).append(";\n");
        node.getFunctionCall().acceptVisit(this);

        String eventId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(eventId).append(";\n");
        node.getEvent().acceptVisit(this);
    }

    @Override
    public void visitProgNode(ProgNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        for (DefNode def : node.getDefinitions()) {
            String defId = "node" + idCounter;
            edges.append("    ").append(nodeId).append(" -> ").append(defId).append(";\n");
            def.acceptVisit(this);
        }
    }
}
