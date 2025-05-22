package afs.interpreter.expressions;

import java.util.List;

public class ListVal {
    private final List<Object> elements;

    public ListVal(List<Object> elements) {
        this.elements = elements;
    }

    public List<Object> getElments() {
        return elements;
    }

    @Override
    public String toString() {
        return elements.toString();
    }
}
