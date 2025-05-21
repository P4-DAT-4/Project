package afs.interpreter.expressions;

import java.util.List;

public class LineVal {
    public final List<LineSegment> segments;

    public LineVal(List<LineSegment> segments) {
        this.segments = segments;
    }
}
