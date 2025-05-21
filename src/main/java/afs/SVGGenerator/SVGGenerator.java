package afs.interpreter.SVGGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SVGGenerator {

    public interface SVGElement {
        String toSVG();
    }

    public static class Line implements SVGElement {
        private final int x1, y1, x2, y2;

        public Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        @Override
        public String toSVG() {
            return String.format("<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"black\" />", x1, y1, x2, y2);
        }
    }

    public static class BezierCurve implements SVGElement {
        private final int x1, y1, cx, cy, x2, y2;

        public BezierCurve(int x1, int y1, int cx, int cy, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.cx = cx;
            this.cy = cy;
            this.x2 = x2;
            this.y2 = y2;
        }

        @Override
        public String toSVG() {
            return String.format("<path d=\"M%d,%d Q%d,%d %d,%d\" stroke=\"black\" fill=\"none\" />",
                    x1, y1, cx, cy, x2, y2);
        }
    }

    public static class Text implements SVGElement {
        private final int x, y, fontSize;
        private final String content;

        public Text(int x, int y, String content, int fontSize) {
            this.x = x;
            this.y = y;
            this.content = content;
            this.fontSize = fontSize;
        }

        public Text(int x, int y, String content) {
            this(x, y, content, 14); // default size
        }

        @Override
        public String toSVG() {
            return String.format("<text x=\"%d\" y=\"%d\" fill=\"black\" font-size=\"%d\">%s</text>",
                    x, y, fontSize, content);
        }
    }


    public static String generate(List<SVGElement> elements, int width, int height) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
        sb.append(String.format("<svg width=\"%d\" height=\"%d\" xmlns=\"http://www.w3.org/2000/svg\">\n", width, height));
        sb.append("<rect width=\"100%\" height=\"100%\" fill=\"white\" />\n");

        for (SVGElement el : elements) {
            sb.append("  ").append(el.toSVG()).append("\n");
        }

        sb.append("</svg>");
        return sb.toString();
    }

    public static void generateToFile(List<SVGElement> elements, int width, int height, String filename) throws IOException {
        String svgContent = generate(elements, width, height);
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(svgContent);
        }
    }
}