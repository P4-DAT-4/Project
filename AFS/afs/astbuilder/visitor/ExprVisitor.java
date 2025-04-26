package afs.astbuilder.visitor;

import afs.astbuilder.nodes.expr.*;

public interface ExprVisitor extends ASTVisitor {
    void visitExprAdditionNode(ExprAdditionNode node);
    void visitExprAndNode(ExprAndNode node);
    void visitExprBoolNode(ExprBoolNode node);
    void visitExprConcatenationNode(ExprConcatenationNode node);
    void visitExprCurveNode(ExprCurveNode node);
    void visitExprDivisionNode(ExprDivisionNode node);
    void visitExprDoubleNode(ExprDoubleNode node);
    void visitExprEqualNode(ExprEqualsNode node);
    void visitExprFunctionCallNode(ExprFunctionCallNode node);
    void visitExprIdentifierNode(ExprIdentifierNode node);
    void visitExprIntNode(ExprIntNode node);
    void visitExprLessThanNode(ExprLessThanNode node);
    void visitExprLineNode(ExprLineNode node);
    void visitExprListAccessNode(ExprListAccessNode node);
    void visitExprListNode(ExprListNode node);
    void visitExprMultiplicationNode(ExprMultiplicationNode node);
    void visitExprNegationNode(ExprNegationNode node);
    void visitExprNotNode(ExprNotNode node);
    void visitExprPlaceNode(ExprPlaceNode node);
    void visitExprRotateNode(ExprRotateNode node);
    void visitExprScaleNode(ExprScaleNode node);
    void visitExprStringNode(ExprStringNode node);
    void visitExprSubtractionNode(ExprSubtractionNode node);
    void visitExprTextNode(ExprTextNode node);
}
