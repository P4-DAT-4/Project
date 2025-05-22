package afs.semantic_analysis.types;

import java.util.ArrayList;
import java.util.List;

public class Shape {
    private final List<Object> lines;
    private final List<Object> curves;
    private final List<Object> strings;

    public Shape() {
        lines = new ArrayList<>();
        curves = new ArrayList<>();
        strings = new ArrayList<>();
    }
}
