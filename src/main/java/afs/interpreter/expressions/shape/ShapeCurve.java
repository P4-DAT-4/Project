package afs.interpreter.expressions.shape;

import java.util.List;

public final class ShapeCurve extends Shape {
    private Point start;
    private Point control;
    private Point end;

    public ShapeCurve(Point start, Point control, Point end) {
        this.start = start;
        this.control = control;
        this.end = end;
    }

    public ShapeCurve(List<Point> points) {
        this.start = points.getFirst();
        this.control = points.get(1);
        this.end = points.getLast();
    }

    public Point getStart() {
        return start;
    }

    public Point getControl() {
        return control;
    }

    public Point getEnd() {
        return end;
    }

    @Override
    public List<Point> getPoints() {
        return List.of(start, control, end);
    }
}