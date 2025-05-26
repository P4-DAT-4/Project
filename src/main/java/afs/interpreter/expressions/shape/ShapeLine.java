package afs.interpreter.expressions.shape;

import java.util.List;

public final class ShapeLine extends Shape {
    private final Point start;
    private final Point end;

    public ShapeLine(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public ShapeLine(List<Point> points) {
        this.start = points.getFirst();
        this.end = points.getLast();
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    @Override
    public List<Point> getPoints() {
        return List.of(start, end);
    }
}