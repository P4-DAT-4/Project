package afs.SVGGenerator;

import afs.runtime.Shape;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Demo {
    public static void main(String[] args) {

        // 1. Square using 1 line segment
        Shape squareShape = new Shape();
        Shape.Segment squareSegment = new Shape.Segment(Shape.Segment.SegmentType.LINE);
        squareSegment.addPoint(10.0, 10.0);
        squareSegment.addPoint(60.0, 10.0);
        squareSegment.addPoint(60.0, 60.0);
        squareSegment.addPoint(10.0, 60.0);
        squareSegment.addPoint(10.0, 10.0);
        squareShape.addSegment(squareSegment);

        // 2. Circle approximation using multiple CURVE segments
        Shape circleShape = new Shape();

        // First quadratic curve (top-right)
        Shape.Segment curve1 = new Shape.Segment(Shape.Segment.SegmentType.CURVE);
        curve1.addPoint(120.0, 10.0);  // Start
        curve1.addPoint(147.0, 10.0);  // Control
        curve1.addPoint(147.0, 35.0);  // End
        circleShape.addSegment(curve1);

        // Second quadratic curve (bottom-right)
        Shape.Segment curve2 = new Shape.Segment(Shape.Segment.SegmentType.CURVE);
        curve2.addPoint(147.0, 35.0);  // Start
        curve2.addPoint(147.0, 60.0);  // Control
        curve2.addPoint(120.0, 60.0);  // End
        circleShape.addSegment(curve2);

        // Third quadratic curve (bottom-left)
        Shape.Segment curve3 = new Shape.Segment(Shape.Segment.SegmentType.CURVE);
        curve3.addPoint(120.0, 60.0);  // Start
        curve3.addPoint(93.0, 60.0);   // Control
        curve3.addPoint(93.0, 35.0);   // End
        circleShape.addSegment(curve3);

        // Fourth quadratic curve (top-left)
        Shape.Segment curve4 = new Shape.Segment(Shape.Segment.SegmentType.CURVE);
        curve4.addPoint(93.0, 35.0);   // Start
        curve4.addPoint(93.0, 10.0);   // Control
        curve4.addPoint(120.0, 10.0);  // End
        circleShape.addSegment(curve4);

        // 3. Simple diagonal line
        Shape lineShape = new Shape();
        Shape.Segment lineSegment = new Shape.Segment(Shape.Segment.SegmentType.LINE);
        lineSegment.addPoint(180.0, 10.0);
        lineSegment.addPoint(230.0, 60.0);
        lineShape.addSegment(lineSegment);

        // 4. Simple bezier curve
        Shape bezierShape = new Shape();
        Shape.Segment bezierSegment = new Shape.Segment(Shape.Segment.SegmentType.CURVE);
        bezierSegment.addPoint(250.0, 10.0);  // Start
        bezierSegment.addPoint(275.0, 0.0);   // Control
        bezierSegment.addPoint(300.0, 60.0);  // End
        bezierShape.addSegment(bezierSegment);

        // 5. Cross made of two LINE segments in one shape
        Shape crossShape = new Shape();

        // First line of the cross (diagonal from top-left to bottom-right)
        Shape.Segment crossLine1 = new Shape.Segment(Shape.Segment.SegmentType.LINE);
        crossLine1.addPoint(330.0, 10.0);
        crossLine1.addPoint(380.0, 60.0);
        crossShape.addSegment(crossLine1);

        // Second line of the cross (diagonal from bottom-left to top-right)
        Shape.Segment crossLine2 = new Shape.Segment(Shape.Segment.SegmentType.LINE);
        crossLine2.addPoint(330.0, 60.0);
        crossLine2.addPoint(380.0, 10.0);
        crossShape.addSegment(crossLine2);

        // 6. Precise line with fractional coordinates
        Shape preciseShape = new Shape();
        Shape.Segment preciseSegment = new Shape.Segment(Shape.Segment.SegmentType.LINE);
        preciseSegment.addPoint(410.5, 10.5);
        preciseSegment.addPoint(435.75, 35.25);
        preciseSegment.addPoint(410.5, 60.5);
        preciseShape.addSegment(preciseSegment);

        // Add text labels to each shape
        // Add text to square shape
        Shape.Segment squareTextSegment = new Shape.Segment(Shape.Segment.SegmentType.TEXT);
        squareTextSegment.addPoint(15.0, 80.0);
        squareTextSegment.setTextContent("Square");
        squareShape.addSegment(squareTextSegment);

        // Add text to circle shape
        Shape.Segment circleTextSegment = new Shape.Segment(Shape.Segment.SegmentType.TEXT);
        circleTextSegment.addPoint(100.0, 80.0);
        circleTextSegment.setTextContent("Circle");
        circleShape.addSegment(circleTextSegment);

        // line shape with text
        Shape.Segment lineTextSegment = new Shape.Segment(Shape.Segment.SegmentType.TEXT);
        lineTextSegment.addPoint(185.0, 80.0);
        lineTextSegment.setTextContent("Line");
        lineShape.addSegment(lineTextSegment);

        // bezier shape with text
        Shape.Segment bezierTextSegment = new Shape.Segment(Shape.Segment.SegmentType.TEXT);
        bezierTextSegment.addPoint(255.0, 80.0);
        bezierTextSegment.setTextContent("Bezier");
        bezierShape.addSegment(bezierTextSegment);

        // Add text to cross shape
        Shape.Segment crossTextSegment = new Shape.Segment(Shape.Segment.SegmentType.TEXT);
        crossTextSegment.addPoint(340.0, 80.0);
        crossTextSegment.setTextContent("Cross");
        crossShape.addSegment(crossTextSegment);

        // Add text to precise shape (decimal locations)
        Shape.Segment preciseTextSegment = new Shape.Segment(Shape.Segment.SegmentType.TEXT);
        preciseTextSegment.addPoint(407.5, 80.0);
        preciseTextSegment.setTextContent("Precise");
        preciseShape.addSegment(preciseTextSegment);

        // Create a standalone text shape for the special characters
        Shape specialTextShape = Shape.createText(" 1 > 2 < 4 $ & |", 20.0, 120.0);

        // Combine all shapes into a list
        List<Shape> shapes = Arrays.asList(
                squareShape,
                circleShape,
                lineShape,
                bezierShape,
                crossShape,
                preciseShape,
                specialTextShape
        );

        try {
            SVGGenerator.generateToFile(shapes, 480.0, 200.0, "test_shapes1.svg");
            System.out.println("SVG saved as test_shapes1.svg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}