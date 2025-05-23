package afs.interpreter.expressions;

import java.util.List;

public class ListVal implements Val {
    private final List<Val> elements;

    public ListVal(List<Val> elements) {
        this.elements = elements;
    }

    public List<Val> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        return elements.toString();
    }
}
