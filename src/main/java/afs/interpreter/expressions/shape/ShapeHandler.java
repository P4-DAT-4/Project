package afs.interpreter.expressions.shape;

import java.util.List;

public class ShapeHandler {
    public static Point getCenter(List<Shape> shapes) {
        // Calculate the center of the shape
        double centerX = 0;
        double centerY = 0;
        int pointCount = 0;
        for (Shape shape : shapes) {
            for (Point point : shape.getPoints()) {
                centerX += point.getX();
                centerY += point.getY();
                pointCount++;
            }
        }
        if (pointCount > 0) {
            centerX /= pointCount;
            centerY /= pointCount;
        }

        return new Point(centerX, centerY);
    }
}
