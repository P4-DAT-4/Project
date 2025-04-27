package afs.astbuilder.nodes.event;

import afs.astbuilder.visitor.EventVisitor;

public class EventCompositionNode extends EventNode {
    private final EventNode leftEvent;
    private final EventNode rightEvent;

    public EventCompositionNode(EventNode leftEvent, EventNode rightEvent) {
        this.leftEvent = leftEvent;
        this.rightEvent = rightEvent;
    }

    public EventNode getLeftEvent() {
        return leftEvent;
    }

    public EventNode getRightEvent() {
        return rightEvent;
    }

    @Override
    public void acceptVisit(EventVisitor visitor) {
        visitor.visitEventCompositionNode(this);
    }

    @Override
    public String toString() {
        return "E1;E2";
    }
}
