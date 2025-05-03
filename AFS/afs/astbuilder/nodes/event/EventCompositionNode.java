package afs.astbuilder.nodes.event;

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
    public String toString() {
        return "E1;E2";
    }
}
