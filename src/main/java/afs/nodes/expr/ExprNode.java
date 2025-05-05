package afs.nodes.expr;

import afs.nodes.AbstractSyntaxNode;

public sealed abstract class ExprNode extends AbstractSyntaxNode permits ExprBinopNode, ExprBoolNode, ExprCurveNode, ExprDoubleNode, ExprFunctionCallNode, ExprIdentifierNode, ExprIntNode, ExprLineNode, ExprListAccessNode, ExprListDeclaration, ExprPlaceNode, ExprRotateNode, ExprScaleNode, ExprStringNode, ExprTextNode, ExprUnopNode {
    protected ExprNode(int lineNumber, int columnNumber) {
        super(lineNumber, columnNumber);
    }
}
