package astbuilder.nodes.event;

import astbuilder.visitor.EventVisitor;

public class EventCompositionNode extends EventNode {
    @Override
    public void acceptVisit(EventVisitor visitor) {
        visitor.visitEventCompositionNode(this);
    }
}
