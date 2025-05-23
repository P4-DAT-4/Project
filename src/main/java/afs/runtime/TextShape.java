package afs.runtime;

/**
 * Represents a text shape in AFS.
 */
public class TextShape extends Shape {
    private final String text;

    /**
     * Creates a text shape with the given text.
     * @param text The text to display
     */
    public TextShape(String text) {
        super(Shape.createText(text, 0.0, 0.0).getSegments());
        this.text = text;
    }

    /**
     * Gets the text content.
     * @return The text
     */
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "TextShape: \"" + text + "\"";
    }
}