package afs.interpreter.expressions.shape;

import java.util.ArrayList;
import java.util.List;

public sealed abstract class Shape permits ShapeCurve, ShapeLine, ShapeText{
    public abstract List<Point> getPoints();

    public Shape addToPoints(double x, double y) {
        List<Point> newPoints = new ArrayList<>();
        for (Point point : this.getPoints()) {
            Point newPoint = new Point(point.getX() + x, point.getY() + y);
            newPoints.add(newPoint);
        }
        return switch(this) {
            case ShapeCurve shapeCurve -> new ShapeCurve(newPoints);
            case ShapeLine shapeLine -> new ShapeLine(newPoints);
            case ShapeText shapeText -> new ShapeText(shapeText.getTextContent(), newPoints);
        };
    }

    public Shape createShape(List<Point> points) {
        return switch (this) {
            case ShapeCurve shapeCurve -> new ShapeCurve(points);
            case ShapeLine shapeLine -> new ShapeLine(points);
            case ShapeText shapeText -> new ShapeText(shapeText.getTextContent(), points);
        };
    }
}
