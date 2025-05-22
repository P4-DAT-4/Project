package afs.interpreter.expressions;

public class BezierSegment {
    public final Point start, control, end;

    public BezierSegment(Point start, Point control, Point end) {
        this.start = start;
        this.control = control;
        this.end = end;
    }

    public Point evaluateAt(double t){
        double x = Math.pow(1-t, 2) * start.getX()
                + 2 * (1 - t) * t * control.getX()
                + Math.pow(t,2) * end.getX();

        double y = Math.pow(1-t, 2) * start.getY()
                + 2 * (1-t) * t * control.getY()
                + Math.pow(t,2) * end.getY();

        return new Point(x, y);
    }
}
