package afs.interpreter.expressions;

import java.util.List;

public class CurveVal {
    public final List<BezierSegment> segments;

    public CurveVal(List<BezierSegment> segments) {
        this.segments = segments;
    }

}
