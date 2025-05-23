package afs.SVGGenerator;

import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;
import afs.interpreter.expressions.Point;
import afs.runtime.Shape;
import java.awt.geom.Path2D;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;


public class SVGGenerator {

    // drawable interface to make sure each shappy has a draw
    public interface SVGDrawable {
        void draw(SVGGraphics2D g);
    }

    // polyline class
    public static class Polyline implements SVGDrawable {
        private final double[][] points;

        public Polyline(double[][] points) {
            if (points.length < 2) {
                throw new IllegalArgumentException("Points array must have at least 2 points");
            }
            this.points = points.clone();
        }

        @Override
        public void draw(SVGGraphics2D g) {
            g.setPaint(Color.BLACK);
            Path2D.Double path = new Path2D.Double();
            path.moveTo(points[0][0], points[0][1]);
            for (int i = 1; i < points.length; i++) {
                path.lineTo(points[i][0], points[i][1]);
            }
            g.draw(path);
        }
    }

    public static class PolyCurve implements SVGDrawable {
        private final double[][] points;
        private final double[][] controlPoints;
        private boolean closed = false;

        public PolyCurve(double[][] points, double[][] controlPoints) {
            if (points.length < 2) {
                throw new IllegalArgumentException("Points array must have at least 2 points");
            }
            if (controlPoints.length < 1) {
                throw new IllegalArgumentException("ControlPoints array must have at least 1 point");
            }
            if (controlPoints.length != points.length - 1) {
                throw new IllegalArgumentException("For n points, n-1 control points are required");
            }

            for (double[] point : points) {
                if (point.length != 2) {
                    throw new IllegalArgumentException("Each point must be exactly [x,y]");
                }
            }
            for (double[] cp : controlPoints) {
                if (cp.length != 2) {
                    throw new IllegalArgumentException("Each control point must be exactly [cx,cy]");
                }
            }

            this.points = points.clone();
            this.controlPoints = controlPoints.clone();
        }

        @Override
        public void draw(SVGGraphics2D g) {
            g.setPaint(Color.BLACK);
            Path2D.Double path = new Path2D.Double();
            path.moveTo(points[0][0], points[0][1]);

            for (int i = 0; i < points.length - 1; i++) {
                path.quadTo(
                        controlPoints[i][0], controlPoints[i][1],
                        points[i + 1][0], points[i + 1][1]
                );
            }

            if (closed) {
                if (closed && points.length > 2) {
                    path.quadTo(
                            controlPoints[controlPoints.length - 1][0],
                            controlPoints[controlPoints.length - 1][1],
                            points[0][0], points[0][1]
                    );
                }
                path.closePath();
            }

            g.draw(path);
        }
    }

    public static class Text implements SVGDrawable {
        private final double[] position;
        private final int fontSize;
        private final String content;

        public Text(double[] position, String content, int fontSize) {
            if (position.length != 2) {
                throw new IllegalArgumentException("Position must be exactly [x,y]");
            }
            this.position = position.clone();
            this.content = content;
            this.fontSize = fontSize;
        }

        public Text(double[] position, String content) {
            this(position, content, 14);
        }

        @Override
        public void draw(SVGGraphics2D g) {
            g.setPaint(Color.BLACK);
            g.setFont(new Font("Courier New", Font.PLAIN, fontSize));
            g.drawString(content, (float) position[0], (float) position[1]);
        }
    }

    /**
     * Helper method to convert Point objects to double arrays
     */
    private static double[][] convertPointsToArray(List<Point> points) {
        double[][] result = new double[points.size()][2];
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            result[i][0] = p.getX();
            result[i][1] = p.getY();
        }
        return result;
    }

    /**
     * Convert a Shape.Segment to an SVGDrawable
     */
    private static SVGDrawable convertSegmentToDrawable(Shape.Segment segment) {
        List<afs.interpreter.expressions.Point> points = segment.getCoordinates();

        switch (segment.getType()) {
            case LINE:
                if (points.size() < 2) {
                    throw new IllegalArgumentException("LINE segment must have at least 2 points");
                }
                return new Polyline(convertPointsToArray(points));

            case CURVE:
                if (points.size() < 3) {
                    throw new IllegalArgumentException("CURVE segment must have at least 3 points");
                }
                // Interpret points as: start, control1, end, control2, end2, etc.
                // For simplicity, assume every 3 points make a quadratic curve: start, control, end
                if (points.size() == 3) {
                    // Simple case: one quadratic curve
                    double[][] curvePoints = new double[][]{
                            {points.get(0).getX(), points.get(0).getY()},
                            {points.get(2).getX(), points.get(2).getY()}
                    };
                    double[][] controlPoints = new double[][]{
                            {points.get(1).getX(), points.get(1).getY()}
                    };
                    return new PolyCurve(curvePoints, controlPoints);
                } else {
                    // Multiple curves - assume alternating pattern: point, control, point, control, etc.
                    // Create a polycurve connecting all the points
                    int numCurves = (points.size() - 1) / 2;
                    double[][] curvePoints = new double[numCurves + 1][2];
                    double[][] controlPoints = new double[numCurves][2];

                    // First point
                    curvePoints[0][0] = points.get(0).getX();
                    curvePoints[0][1] = points.get(0).getY();

                    for (int i = 0; i < numCurves; i++) {
                        // Control point
                        controlPoints[i][0] = points.get(i * 2 + 1).getX();
                        controlPoints[i][1] = points.get(i * 2 + 1).getY();

                        // End point
                        curvePoints[i + 1][0] = points.get(i * 2 + 2).getX();
                        curvePoints[i + 1][1] = points.get(i * 2 + 2).getY();
                    }

                    return new PolyCurve(curvePoints, controlPoints);
                }

            case TEXT:
                if (points.isEmpty()) {
                    throw new IllegalArgumentException("TEXT segment must have at least 1 point for position");
                }
                Point textPos = points.get(0);
                String content = segment.getTextContent();
                if (content == null) {
                    content = ""; // Default empty string if no text content
                }
                return new Text(new double[]{textPos.getX(), textPos.getY()}, content);

            default:
                throw new IllegalArgumentException("Unknown segment type: " + segment.getType());
        }
    }

    /**
     * Generate an SVG file from a list of Shape objects
     */
    public static void generateToFile(List<Shape> shapes, double width, double height, String filename) throws IOException {
        SVGGraphics2D g = new SVGGraphics2D((int) Math.ceil(width), (int) Math.ceil(height));

        // Optional background
        g.setPaint(Color.WHITE);
        g.fill(new Rectangle2D.Double(0, 0, width, height));

        // Process each shape
        for (Shape shape : shapes) {
            // Process each segment in the shape
            for (Shape.Segment segment : shape.getSegments()) {
                SVGDrawable drawable = convertSegmentToDrawable(segment);
                drawable.draw(g);
            }
        }

        File outputFile = new File(filename);
        SVGUtils.writeToSVG(outputFile, g.getSVGElement());
    }
}
