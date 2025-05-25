package afs.interpreter.expressions;

import java.util.ArrayList;
import java.util.List;

public final class ListVal implements Val {
    private final List<Val> elements;

    public ListVal(List<Val> elements) {
        this.elements = elements;
    }

    public List<Val> getValue() {
        return elements;
    }

    @Override
    public String toString() {
        return elements.toString();
    }

    @Override
    public Val copy() {
        List<Val> copiedList = new ArrayList<>();
        for (Val v : elements) {
            copiedList.add(v.copy());  // recursive copy
        }
        return new ListVal(copiedList);
    }
}
