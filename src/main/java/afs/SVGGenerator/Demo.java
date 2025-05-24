package afs.SVGGenerator;

import afs.interpreter.expressions.ShapeVal;
import afs.interpreter.expressions.shape.Point;
import afs.interpreter.expressions.shape.Shape;
import afs.interpreter.expressions.shape.ShapeLine;
import afs.interpreter.expressions.shape.ShapeCurve;
import afs.interpreter.expressions.shape.ShapeText;
import afs.interpreter.implementations.StackImgStore;
import afs.interpreter.interfaces.ImgStore;

import java.util.List;
import java.util.ArrayList;

public class Demo {
    // Scale factor from 80 to 150
    private static final double SCALE = 150.0 / 80.0;  // 1.875

    // Helper to scale points
    private static Point scalePoint(double x, double y) {
        return new Point(x * SCALE, y * SCALE);
    }

    public static void main(String[] args) {
        ImgStore imgStore = new StackImgStore();

        // 1. Simple line
        Shape line = new ShapeLine(scalePoint(15, 40), scalePoint(65, 40));
        ShapeVal lineWithText = new ShapeVal(List.of(
                line,
                new ShapeText("Line", List.of(scalePoint(40, 45)))
        ));
        imgStore.push(lineWithText);

        // 2. Square made of lines
        List<Shape> squareShapes = new ArrayList<>();
        squareShapes.add(new ShapeLine(scalePoint(20, 15), scalePoint(60, 15)));
        squareShapes.add(new ShapeLine(scalePoint(60, 15), scalePoint(60, 55)));
        squareShapes.add(new ShapeLine(scalePoint(60, 55), scalePoint(20, 55)));
        squareShapes.add(new ShapeLine(scalePoint(20, 55), scalePoint(20, 15)));
        squareShapes.add(new ShapeText("Square", List.of(scalePoint(40, 60))));
        ShapeVal squareVal = new ShapeVal(squareShapes);
        imgStore.push(squareVal);

        // 3. Circle approximation using curves
        List<Shape> circleShapes = new ArrayList<>();
        circleShapes.add(new ShapeCurve(
                scalePoint(40, 15), scalePoint(60, 15), scalePoint(60, 35)));
        circleShapes.add(new ShapeCurve(
                scalePoint(60, 35), scalePoint(60, 55), scalePoint(40, 55)));
        circleShapes.add(new ShapeCurve(
                scalePoint(40, 55), scalePoint(20, 55), scalePoint(20, 35)));
        circleShapes.add(new ShapeCurve(
                scalePoint(20, 35), scalePoint(20, 15), scalePoint(40, 15)));
        circleShapes.add(new ShapeText("Circle", List.of(scalePoint(40, 60))));
        ShapeVal circleVal = new ShapeVal(circleShapes);
        imgStore.push(circleVal);

        // 4. Bezier curve
        Shape bezier = new ShapeCurve(
                scalePoint(15, 45), scalePoint(40, 15), scalePoint(65, 45));
        ShapeVal bezierWithText = new ShapeVal(List.of(
                bezier,
                new ShapeText("Bezier", List.of(scalePoint(40, 50)))
        ));
        imgStore.push(bezierWithText);

        // 5. Cross made of two lines
        List<Shape> crossShapes = new ArrayList<>();
        crossShapes.add(new ShapeLine(scalePoint(20, 20), scalePoint(60, 60)));
        crossShapes.add(new ShapeLine(scalePoint(20, 60), scalePoint(60, 20)));
        crossShapes.add(new ShapeText("Cross", List.of(scalePoint(40, 65))));
        ShapeVal crossVal = new ShapeVal(crossShapes);
        imgStore.push(crossVal);

        // 6. Triangle
        List<Shape> triangleShapes = new ArrayList<>();
        triangleShapes.add(new ShapeLine(scalePoint(40.0, 15.0), scalePoint(60.0, 50.0)));
        triangleShapes.add(new ShapeLine(scalePoint(60.0, 50.0), scalePoint(20.0, 50.0)));
        triangleShapes.add(new ShapeLine(scalePoint(20.0, 50.0), scalePoint(40.0, 15.0)));
        triangleShapes.add(new ShapeText("Triangle", List.of(scalePoint(40, 55))));
        ShapeVal triangleVal = new ShapeVal(triangleShapes);
        imgStore.push(triangleVal);

        // 7. Text
        List<Shape> specialShapes = new ArrayList<>();
        specialShapes.add(new ShapeText("Text:", List.of(scalePoint(40, 15))));
        specialShapes.add(new ShapeText("<= >= == !=", List.of(scalePoint(40, 25))));
        specialShapes.add(new ShapeText("* / % ^ ±", List.of(scalePoint(40, 35))));
        specialShapes.add(new ShapeText("$ € £ ¥ ¢", List.of(scalePoint(40, 45))));
        specialShapes.add(new ShapeText("[] {} \"\" ''", List.of(scalePoint(40, 55))));
        specialShapes.add(new ShapeText("? @ # & | ~", List.of(scalePoint(40, 65))));
        ShapeVal specialText = new ShapeVal(specialShapes);
        imgStore.push(specialText);

        // 8. Sun shape (small) with detached, short rays and text
        List<Shape> sunShapes = new ArrayList<>();
        sunShapes.add(new ShapeCurve(scalePoint(40, 20), scalePoint(55, 20), scalePoint(55, 35)));
        sunShapes.add(new ShapeCurve(scalePoint(55, 35), scalePoint(55, 50), scalePoint(40, 50)));
        sunShapes.add(new ShapeCurve(scalePoint(40, 50), scalePoint(25, 50), scalePoint(25, 35)));
        sunShapes.add(new ShapeCurve(scalePoint(25, 35), scalePoint(25, 20), scalePoint(40, 20)));

        sunShapes.add(new ShapeLine(scalePoint(40, 15), scalePoint(40, 10)));
        sunShapes.add(new ShapeLine(scalePoint(40, 55), scalePoint(40, 60)));

        sunShapes.add(new ShapeLine(scalePoint(20, 35), scalePoint(15, 35)));
        sunShapes.add(new ShapeLine(scalePoint(60, 35), scalePoint(65, 35)));

        sunShapes.add(new ShapeText("Sun", List.of(scalePoint(40, 70))));
        ShapeVal sunVal = new ShapeVal(sunShapes);
        imgStore.push(sunVal);

        try {
            SVGGenerator.generateToFile(imgStore, 150, 150, "testOutput");
            System.out.println("SVG files generated successfully!");
            System.out.println("Generated files: testOutput0.svg through testOutput6.svg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
