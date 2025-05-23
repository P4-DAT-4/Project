package afs.runtime;
import afs.interpreter.expressions.Point;
import afs.semantic_analysis.types.AFSType;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a shape in AFS as a collection of segments.
 * Each segment contains points and a type indicator.
 */
public class Shape extends AFSType<List<Shape.Segment>> {

    /**
     * Represents a segment in a shape with its type and points.
     */

    public static class Segment {
        public enum SegmentType {
            LINE,
            CURVE,
            TEXT
        }
        private final SegmentType type;
        private final List<Point> points;
        private String textContent; // Only used for TEXT segments

        public Segment(SegmentType type) {
            this.type = type;
            this.points = new ArrayList<>();
        }

        public Segment(SegmentType type, List<Point> points) {
            this.type = type;
            this.points = new ArrayList<>(points); // Fixed: actually use the points parameter
        }

        public void addPoint(double x, double y) {
            points.add(new Point(x, y));
        }

        public List<Point> getCoordinates() {
            return new ArrayList<>(points);
        }

        public SegmentType getType() {
            return type;
        }

        public void setTextContent(String text) {
            this.textContent = text;
        }

        public String getTextContent() {
            return textContent;
        }
    }

    /**
     * Creates an empty shape.
     */
    public Shape() {
        super(new ArrayList<>()); // Call parent constructor with empty list
    }

    /**
     * Creates a shape with the given segments.
     */
    public Shape(List<Segment> segments) {
        super(new ArrayList<>(segments)); // Call parent constructor with segments copy
    }

    /**
     * Adds a segment to this shape.
     */
    public void addSegment(Segment segment) {
        getValue().add(segment); // Use the inherited getValue() method
    }

    /**
     * Gets all segments in this shape.
     */
    public List<Segment> getSegments() {
        return new ArrayList<>(getValue()); // Use the inherited getValue() method
    }

    /**
     * Combines this shape with another shape.
     * Implements the union operation from concat-shapeBS semantics.
     */
    public Shape concat(Shape other) {
        List<Segment> combinedSegments = new ArrayList<>(getValue());
        combinedSegments.addAll(other.getValue());
        return new Shape(combinedSegments);
    }

    /**
     * Creates a text shape.
     */
    public static Shape createText(String text, double x, double y) {
        Shape textShape = new Shape();
        Segment textSegment = new Segment(Segment.SegmentType.TEXT);
        // Add the position point
        textSegment.addPoint(x, y);
        // Set the text content
        textSegment.setTextContent(text);
        textShape.addSegment(textSegment);
        return textShape;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Shape with ");
        List<Segment> segments = getValue();
        sb.append(segments.size()).append(" segments: ");
        for (Segment segment : segments) {
            sb.append(segment.getType().name()).append("(");
            sb.append(segment.getCoordinates().size()).append(" points), ");

        }
        return sb.toString();
    }
}