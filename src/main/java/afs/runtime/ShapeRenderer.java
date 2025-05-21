package afs.runtime;

import afs.interpreter.SVGGenerator.SVGGenerator;
import afs.runtime.Shape;
import afs.semantic_analysis.types.AFSType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts AFS shapes to SVG drawables and renders them.
 */
public class ShapeRenderer {

    /**
     * Renders all shapes in an ImgStore to an SVG file.
     */
    public static void renderToSVG(ImgStore imgStore, int width, int height, String filename) throws IOException {
        List<SVGGeneratorJFree.SVGDrawable> drawables = new ArrayList<>();

        // Make a copy of the image store to preserve it
        ImgStore copyStore = new ImageStore((ImageStore) imgStore);

        // Process all shapes in the ImgStore
        while (true) {
            try {
                AFSType shape = copyStore.pop();
                if (shape instanceof Shape) {
                    List<SVGGeneratorJFree.SVGDrawable> shapeDrawables = convertToDrawables((Shape) shape);
                    drawables.addAll(shapeDrawables);
                }
            } catch (RuntimeException e) {
                // Stack is empty
                break;
            }
        }

        // Generate the SVG file
        SVGGeneratorJFree.generateToFile(drawables, width, height, filename);
    }

    /**
     * Converts an AFS shape to a list of SVG drawables (one per segment).
     */
    private static List<SVGGeneratorJFree.SVGDrawable> convertToDrawables(Shape shape) {
        List<SVGGeneratorJFree.SVGDrawable> drawables = new ArrayList<>();

        for (Shape.Segment segment : shape.getSegments()) {
            List<List<Double>> coordinates = segment.getCoordinates();

            // Convert coordinates to the required double[][] format
            double[][] coordinatesArray = new double[coordinates.size()][2];
            for (int i = 0; i < coordinates.size(); i++) {
                coordinatesArray[i][0] = coordinates.get(i).get(0);
                coordinatesArray[i][1] = coordinates.get(i).get(1);
            }

            switch (segment.getType()) {
                case TEXT:
                    if (!coordinates.isEmpty()) {
                        double[] position = new double[] { coordinates.get(0).get(0), coordinates.get(0).get(1) };
                        drawables.add(new SVGGeneratorJFree.Text(position, segment.getTextContent()));
                    }
                    break;
                case LINE:
                    drawables.add(new SVGGeneratorJFree.Polyline(coordinatesArray));
                    break;
                case CURVE:
                    drawables.add(new SVGGeneratorJFree.PolyCurve(coordinatesArray));
                    break;
            }
        }

        return drawables;
    }
}