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
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class SVGGenerator {
    // drawable interface to make sure each shape has a draw
    public interface SVGDrawable {
        void draw(SVGGraphics2D g);
    }

    // line class
    public static class Line implements SVGDrawable {
        private final double x1, y1, x2, y2;

        public Line(double x1, double y1, double x2, double y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        @Override
        public void draw(SVGGraphics2D g) {
            g.setPaint(Color.BLACK);
            g.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }

    // Curve class
    public static class Curve implements SVGDrawable {
        private final double startX, startY;
        private final double controlX, controlY;
        private final double endX, endY;

        public Curve(double startX, double startY, double controlX, double controlY, double endX, double endY) {
            this.startX = startX;
            this.startY = startY;
            this.controlX = controlX;
            this.controlY = controlY;
            this.endX = endX;
            this.endY = endY;
        }

        @Override
        public void draw(SVGGraphics2D g) {
            g.setPaint(Color.BLACK);
            Path2D.Double path = new Path2D.Double();
            path.moveTo(startX, startY);
            path.quadTo(controlX, controlY, endX, endY);
            g.draw(path);
        }
    }

    //text class
    public static class Text implements SVGDrawable {
        private final double x, y;
        private final int fontSize;
        private final String content;

        public Text(double x, double y, String content, int fontSize) {
            this.x = x;
            this.y = y;
            this.content = content;
            this.fontSize = fontSize;
        }

        public Text(double x, double y, String content) {
            this(x, y, content, 10);
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
            float centeredX = (float) x - (textWidth / 2.0f);

            // Add half the ascent to y to center vertically
            float centeredY = (float) y + (textHeight / 2.0f);

            // Draw the text at the centered position
            g.drawString(content, centeredX, centeredY);
        }
    }

    private static SVGDrawable shapeToDrawable(Shape shape) {
        return switch (shape) {
            case ShapeCurve shapeCurve -> {
                Point start = shapeCurve.getStart();
                Point control = shapeCurve.getControl();
                Point end = shapeCurve.getEnd();
                yield new Curve(start.getX(), start.getY(),
                        control.getX(), control.getY(),
                        end.getX(), end.getY());
            }
            case ShapeLine shapeLine -> {
                Point start = shapeLine.getStart();
                Point end = shapeLine.getEnd();
                yield new Line(start.getX(), start.getY(), end.getX(), end.getY());
            }
            case ShapeText shapeText -> {
                List<Point> points = shapeText.getPoints();
                Point position = points.getFirst();
                yield new Text(position.getX(), position.getY(), shapeText.getTextContent());
            }
        };
    }

    private static void generateImage(List<Shape> shapes, int width, int height, String filename) throws IOException {
        SVGGraphics2D g = new SVGGraphics2D(width, height);

        g.setPaint(Color.WHITE);
        g.fill(new Rectangle2D.Double(0, 0, width, height));

        for (Shape shape : shapes) {
            SVGDrawable drawable = shapeToDrawable(shape);
            drawable.draw(g);
        }
        File outputFile = new File(filename + ".svg");
        SVGUtils.writeToSVG(outputFile, g.getSVGElement());
    }

    public static void generateToFile(ImgStore imgStore, int width, int height, String filename) throws IOException {
        // Process each shape
        int counter = 0;
        while (!imgStore.isEmpty()) {
            String currentFileName = filename + counter;
            Val val = imgStore.pop();
            generateImage(val.asShape(), width, height, currentFileName);
            counter++;
        }
    }
}