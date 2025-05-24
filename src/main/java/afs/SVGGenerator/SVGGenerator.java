package afs.SVGGenerator;

import afs.interpreter.expressions.Val;
import afs.interpreter.expressions.shape.Point;
import afs.interpreter.expressions.shape.Shape;
import afs.interpreter.expressions.shape.*;
import afs.interpreter.interfaces.ImgStore;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class SVGGenerator {
    // drawable interface to make sure each shappy has a draw
    public interface SVGDrawable {
        void draw(SVGGraphics2D g);
    }

    // polyline class
    public static class Polyline implements SVGDrawable {
        private final double[][] points;

        public Polyline(double[][] points) {
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

        public PolyCurve(double[][] points, double[][] controlPoints) {
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
            this(position, content, 10);
        }

        @Override
        public void draw(SVGGraphics2D g) {
            g.setPaint(Color.BLACK);
            Font font = new Font("Courier New", Font.PLAIN, fontSize);
            g.setFont(font);

            // Get font metrics to calculate text dimensions
            FontMetrics metrics = g.getFontMetrics(font);

            // Calculate the width and height of the text
            int textWidth = metrics.stringWidth(content);
            int textHeight = metrics.getHeight();

            // Calculate the centered position
            // Subtract half the width from x to center horizontally
            float centeredX = (float) position[0] - (textWidth / 2.0f);

            // Add half the ascent to y to center vertically
            float centeredY = (float) position[1] + (textHeight/ 2.0f);

            // Draw the text at the centered position
            g.drawString(content, centeredX, centeredY);
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

    private static SVGDrawable shapeToDrawable(Shape shape) {
        return switch (shape) {
            case ShapeCurve shapeCurve -> {
                var curvePoints = convertPointsToArray(List.of(shapeCurve.getStart(), shapeCurve.getEnd()));
                var controlPoints = convertPointsToArray(List.of(shapeCurve.getControl()));
                yield new PolyCurve(curvePoints, controlPoints);
            }
            case ShapeLine shapeLine -> new Polyline(convertPointsToArray(shapeLine.getPoints()));
            case ShapeText shapeText ->
                    new Text(convertPointsToArray(shapeText.getPoints())[0], shapeText.getTextContent());
        };
    }

    private static void generateImage(List<Shape> shapes, double width, double height, String filename) throws IOException {
        SVGGraphics2D g = new SVGGraphics2D((int) Math.ceil(width), (int) Math.ceil(height));

        g.setPaint(Color.WHITE);
        g.fill(new Rectangle2D.Double(0, 0, width, height));

        int i = 0;
        for (Shape shape: shapes) {
            SVGDrawable drawable = shapeToDrawable(shape);
            //System.out.println("Drawing shape" + ++i);
            drawable.draw(g);
        }

        File outputFile = new File(filename + ".svg");
        SVGUtils.writeToSVG(outputFile, g.getSVGElement());
    }

    public static void generateToFile (ImgStore imgStore,double width, double height, String filename) throws
    IOException {
        // Process each shape
        int counter = 0;
        while (!imgStore.isEmpty()) {
            String currentFileName = filename + counter;
            Val val = imgStore.pop();
            generateImage(val.asShape(), width, height, currentFileName);
            counter ++;
        }
    }
}