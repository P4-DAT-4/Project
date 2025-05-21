package afs.runtime;

import afs.semantic_analysis.types.AFSType;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a shape in AFS as a collection of segments.
 * Each segment contains points and a type indicator.
 */
public class Shape extends AFSType {

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
        private final List<List<Double>> points;
        private String textContent; // Only used for TEXT segments

        public Segment(SegmentType type) {
            this.type = type;
            this.points = new ArrayList<>();
        }

        public Segment(SegmentType type, List<List<Double>> points) {
            this.type = type;
            this.points = new ArrayList<>(points);
        }

        public void addPoint(double x, double y) {
            List<Double> point = new ArrayList<>(2);
            point.add(x);
            point.add(y);
            points.add(point);
        }

        public List<List<Double>> getPoints() {
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

    // List of segments that make up this shape
    private final List<Segment> segments;

    /**
     * Creates an empty shape.
     */
    public Shape() {
        this.segments = new ArrayList<>();
    }

    /**
     * Creates a shape with the given segments.
     */
    public Shape(List<Segment> segments) {
        this.segments = new ArrayList<>(segments);
    }

    /**
     * Adds a segment to this shape.
     */
    public void addSegment(Segment segment) {
        segments.add(segment);
    }

    /**
     * Gets all segments in this shape.
     */
    public List<Segment> getSegments() {
        return new ArrayList<>(segments);
    }

    /**
     * Combines this shape with another shape.
     * Implements the union operation from concat-shapeBS semantics.
     */
    public Shape concat(Shape other) {
        List<Segment> combinedSegments = new ArrayList<>(segments);
        combinedSegments.addAll(other.segments);
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
        sb.append(segments.size()).append(" segments: ");

        for (Segment segment : segments) {
            sb.append(segment.getType().name()).append("(");
            sb.append(segment.getPoints().size()).append(" points), ");
        }

        return sb.toString();
    }
}