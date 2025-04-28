package afs.astbuilder.visitor;

import afs.astbuilder.checker.types.AFSType;
import afs.astbuilder.nodes.expr.*;

public interface ExprVisitor extends ASTVisitor {
    AFSType visitExprAdditionNode(ExprAdditionNode node);
    AFSType visitExprAndNode(ExprAndNode node);
    AFSType visitExprBoolNode(ExprBoolNode node);
    AFSType visitExprConcatenationNode(ExprConcatenationNode node);
    AFSType visitExprCurveNode(ExprCurveNode node);
    AFSType visitExprDivisionNode(ExprDivisionNode node);
    AFSType visitExprDoubleNode(ExprDoubleNode node);
    AFSType visitExprEqualNode(ExprEqualsNode node);
    AFSType visitExprFunctionCallNode(ExprFunctionCallNode node);
    AFSType visitExprIdentifierNode(ExprIdentifierNode node);
    AFSType visitExprIntNode(ExprIntNode node);
    AFSType visitExprLessThanNode(ExprLessThanNode node);
    AFSType visitExprLineNode(ExprLineNode node);
    AFSType visitExprListAccessNode(ExprListAccessNode node);
    AFSType visitExprListNode(ExprListNode node);
    AFSType visitExprMultiplicationNode(ExprMultiplicationNode node);
    AFSType visitExprNegationNode(ExprNegationNode node);
    AFSType visitExprNotNode(ExprNotNode node);
    AFSType visitExprPlaceNode(ExprPlaceNode node);
    AFSType visitExprRotateNode(ExprRotateNode node);
    AFSType visitExprScaleNode(ExprScaleNode node);
    AFSType visitExprStringNode(ExprStringNode node);
    AFSType visitExprSubtractionNode(ExprSubtractionNode node);
    AFSType visitExprTextNode(ExprTextNode node);
}
