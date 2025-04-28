// Generated with AI

package afs.astprinter;

import afs.astbuilder.checker.types.AFSType;
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
        graphCode.append(nodes); // Add node definitions
        graphCode.append(edges); // Add edges
        graphCode.append("}\n"); // End of the Graphviz DOT format
        return graphCode.toString(); // Return the final Graphviz code
    }

    @Override
    public AFSType visitExprAdditionNode(ExprAdditionNode node) {
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
    public AFSType visitExprAndNode(ExprAndNode node) {
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
    public AFSType visitExprBoolNode(ExprBoolNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public AFSType visitExprConcatenationNode(ExprConcatenationNode node) {
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
    public AFSType visitExprCurveNode(ExprCurveNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        for (ExprNode expr : node.getExpressions()) {
            String exprId = "node" + idCounter;
            edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
            expr.acceptVisit(this);
        }
    }

    @Override
    public AFSType visitExprDivisionNode(ExprDivisionNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String leftId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(leftId).append(";\n");
        node.getLeftExpression().acceptVisit(this);

        String rightId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(rightId).append(";\n");
        node.getRightExpression().acceptVisit(this);
    }

    public AFSType visitExprDoubleNode(ExprDoubleNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public AFSType visitExprEqualNode(ExprEqualsNode node) {
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
    public AFSType visitExprFunctionCallNode(ExprFunctionCallNode node) {
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
    public AFSType visitExprIdentifierNode(ExprIdentifierNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public AFSType visitExprIntNode(ExprIntNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public AFSType visitExprLessThanNode(ExprLessThanNode node) {
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
    public AFSType visitExprLineNode(ExprLineNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        for (ExprNode expr : node.getExpressions()) {
            String exprId = "node" + idCounter;
            edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
            expr.acceptVisit(this);
        }
    }

    @Override
    public AFSType visitExprListAccessNode(ExprListAccessNode node) {
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
    public AFSType visitExprListNode(ExprListNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        for (ExprNode expr : node.getExpressions()) {
            String exprId = "node" + idCounter;
            edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
            expr.acceptVisit(this);
        }
    }

    @Override
    public AFSType visitExprMultiplicationNode(ExprMultiplicationNode node) {
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
    public AFSType visitExprNegationNode(ExprNegationNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String exprId = "node" + idCounter;
        node.getExpression().acceptVisit(this);
        edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
    }

    @Override
    public AFSType visitExprNotNode(ExprNotNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String exprId = "node" + idCounter;
        node.getExpression().acceptVisit(this);
        edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
    }

    @Override
    public AFSType visitExprPlaceNode(ExprPlaceNode node) {
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
    public AFSType visitExprRotateNode(ExprRotateNode node) {
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
    public AFSType visitExprScaleNode(ExprScaleNode node) {
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
    public AFSType visitExprStringNode(ExprStringNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public AFSType visitExprSubtractionNode(ExprSubtractionNode node) {
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
    public AFSType visitExprTextNode(ExprTextNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String exprId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
        node.getExpression().acceptVisit(this);
    }

    @Override
    public AFSType visitStmtAssignmentNode(StmtAssignmentNode node) {
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
    public AFSType visitStmtCompositionNode(StmtCompositionNode node) {
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
    public AFSType visitStmtBlockNode(StmtBlockNode node) {
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
    public AFSType visitStmtIfNode(StmtIfNode node) {
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
    public AFSType visitStmtReturnNode(StmtReturnNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String exprId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
        node.getExpression().acceptVisit(this);
    }

    @Override
    public AFSType visitStmtSkipNode(StmtSkipNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public AFSType visitStmtWhileNode(StmtWhileNode node) {
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
    public AFSType visitStmtFunctionCallNode(StmtFunctionCallNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String funcCallId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(funcCallId).append(";\n");
        node.getFunctionCall().acceptVisit(this);
    }

    @Override
    public AFSType visitStmtDeclarationNode(StmtDeclarationNode node) {
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
    public AFSType visitTypeBoolNode(TypeBoolNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public AFSType visitTypeDoubleNode(TypeDoubleNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public AFSType visitTypeIntNode(TypeIntNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public AFSType visitTypeListNode(TypeListNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String typeId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(typeId).append(";\n");
        node.getType().acceptVisit(this);
    }

    @Override
    public AFSType visitTypeShapeNode(TypeShapeNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
    }

    @Override
    public AFSType visitTypeStringNode(TypeStringNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
        return null;
    }

    @Override
    public AFSType visitTypeVoidNode(TypeVoidNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");
        return null;
    }

    @Override
    public AFSType visitEventCompositionNode(EventCompositionNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String leftEventId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(leftEventId).append(";\n");
        node.getLeftEvent().acceptVisit(this);

        String rightEventId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(rightEventId).append(";\n");
        node.getRightEvent().acceptVisit(this);
        return null;
    }

    @Override
    public AFSType visitEventDeclarationNode(EventDeclarationNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String exprId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(exprId).append(";\n");
        node.getExpression().acceptVisit(this);

        String funcCallId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(funcCallId).append(";\n");
        node.getFunctionCall().acceptVisit(this);
        return null;
    }

    @Override
    public AFSType visitDefDeclarationNode(DefDeclarationNode node) {
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
        return null;
    }

    @Override
    public AFSType visitDefFunctionNode(DefFunctionNode node) {
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
        return null;
    }

    @Override
    public AFSType visitParam(Param node){
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String typeId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(typeId).append(";\n");
        node.getType().acceptVisit(this);

        String identifierId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(identifierId).append(";\n");
        node.getIdentifier().acceptVisit(this);
        return null;
    }

    @Override
    public AFSType visitDefVisualizeNode(DefVisualizeNode node) {
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        String funcId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(funcId).append(";\n");
        node.getFunctionCall().acceptVisit(this);

        String eventId = "node" + idCounter;
        edges.append("    ").append(nodeId).append(" -> ").append(eventId).append(";\n");
        node.getEvent().acceptVisit(this);
        return null;
    }

    @Override
    public AFSType visitProgNode(ProgNode node) { //root
        String nodeId = "node" + idCounter++;
        nodes.append("    ").append(nodeId).append(" [label=\"").append(node.toString()).append("\"];\n");

        for (DefNode def : node.getDefinitions()) {
            String defId = "node" + idCounter;
            edges.append("    ").append(nodeId).append(" -> ").append(defId).append(";\n");
            def.acceptVisit(this);
        }
        return null;
    }
}
