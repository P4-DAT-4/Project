package astbuilder.nodes.def;

import astbuilder.visitor.DefVisitor;

public class DefFunctionNode extends DefNode {

    @Override
    public void acceptVisit(DefVisitor visitor) {
        visitor.visitDefFunctionNode(this);
    }
}
