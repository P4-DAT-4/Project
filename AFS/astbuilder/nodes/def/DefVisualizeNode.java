package astbuilder.nodes.def;

import astbuilder.visitor.DefVisitor;

public class DefVisualizeNode extends DefNode{
    @Override
    public void acceptVisit(DefVisitor visitor) {
        visitor.visitDefVisualizeNode(this);
    }
}
