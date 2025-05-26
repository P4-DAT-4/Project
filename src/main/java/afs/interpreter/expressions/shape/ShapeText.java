package afs.interpreter.expressions.shape;

import java.util.List;

public final class ShapeText extends Shape {
    private final Point placement;
    private final String textContent;

    public ShapeText(String textContent) {
        this.textContent = textContent;
        placement = new Point(10,20);
    }

    public ShapeText(String textContent, List<Point> placement) {
        this.textContent = textContent;
        this.placement = placement.getFirst();
    }

    public String getTextContent() {
        return textContent;
    }

    @Override
    public List<Point> getPoints() {
        return List.of(placement);
    }
}