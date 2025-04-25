package astbuilder.nodes.event;

import astbuilder.visitor.EventVisitor;

public class EventDeclarationNode extends EventNode {
    @Override
    public void acceptVisit(EventVisitor visitor) {
        visitor.visitEventDeclarationNode(this);
    }
}
