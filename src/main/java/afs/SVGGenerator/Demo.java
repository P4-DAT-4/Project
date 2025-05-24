package afs.SVGGenerator;

import afs.interpreter.expressions.ShapeVal;
import afs.interpreter.expressions.shape.*;
import afs.interpreter.implementations.StackImgStore;
import afs.interpreter.interfaces.ImgStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Demo {
    // Scale factor from 80 to 150
    private static final double SCALE = 150.0 / 80.0;  // 1.875

    // Helper to scale points
    private static Point scalePoint(double x, double y) {
        return new Point(x * SCALE, y * SCALE);
    }

    public static void main(String[] args) {
        ImgStore imgStore = new StackImgStore();

        // Test: Many shapes (grid visualization)
        List<Shape> gridTest = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                double x = 5 + i * 7;
                double y = 5 + j * 7;
                // Small box
                gridTest.add(new ShapeLine(new Point(x, y), new Point(x + 5, y)));
                gridTest.add(new ShapeLine(new Point(x + 5, y), new Point(x + 5, y + 5)));
                gridTest.add(new ShapeLine(new Point(x + 5, y + 5), new Point(x, y + 5)));
                gridTest.add(new ShapeLine(new Point(x, y + 5), new Point(x, y)));
            }
        }
        imgStore.push(new ShapeVal(gridTest));

        //Data structure visualizations
        // Test: Binary tree structure
        List<Shape> treeShapes = new ArrayList<>();
        // Root
        treeShapes.add(new ShapeText("5", List.of(new Point(75, 20))));
        // Left child
        treeShapes.add(new ShapeText("3", List.of(new Point(50, 50))));
        treeShapes.add(new ShapeLine(new Point(70, 25), new Point(55, 45)));
        // Right child
        treeShapes.add(new ShapeText("8", List.of(new Point(100, 50))));
        treeShapes.add(new ShapeLine(new Point(80, 25), new Point(95, 45)));
        // Grandchildren
        treeShapes.add(new ShapeText("1", List.of(new Point(35, 80))));
        treeShapes.add(new ShapeLine(new Point(45, 55), new Point(40, 75)));
        treeShapes.add(new ShapeText("4", List.of(new Point(65, 80))));
        treeShapes.add(new ShapeLine(new Point(55, 55), new Point(60, 75)));
        imgStore.push(new ShapeVal(treeShapes));

        // Test: Array sorting animation frames
        List<Integer> array = Arrays.asList(5, 3, 8, 1, 9);
        for (int i = 0; i < array.size() - 1; i++) {
            List<Shape> frame = new ArrayList<>();
            // Draw array boxes
            for (int j = 0; j < array.size(); j++) {
                double x = 20 + j * 25;
                // Box outline
                frame.add(new ShapeLine(new Point(x, 50), new Point(x + 20, 50)));
                frame.add(new ShapeLine(new Point(x + 20, 50), new Point(x + 20, 70)));
                frame.add(new ShapeLine(new Point(x + 20, 70), new Point(x, 70)));
                frame.add(new ShapeLine(new Point(x, 70), new Point(x, 50)));
                // Value
                frame.add(new ShapeText(String.valueOf(array.get(j)),
                        List.of(new Point(x + 10, 60))));
            }
            // Highlight comparing elements
            double highlightX = 20 + i * 25;
            frame.add(new ShapeLine(new Point(highlightX, 48),
                    new Point(highlightX + 45, 48)));
            imgStore.push(new ShapeVal(frame));
        }

        // 21 Simple line
        Shape line = new ShapeLine(scalePoint(15, 40), scalePoint(65, 40));
        ShapeVal lineWithText = new ShapeVal(List.of(
                line,
                new ShapeText("Line", List.of(scalePoint(40, 45)))
        ));
        imgStore.push(lineWithText);

        // 20 Square made of lines
        List<Shape> squareShapes = new ArrayList<>();
        squareShapes.add(new ShapeLine(scalePoint(20, 15), scalePoint(60, 15)));
        squareShapes.add(new ShapeLine(scalePoint(60, 15), scalePoint(60, 55)));
        squareShapes.add(new ShapeLine(scalePoint(60, 55), scalePoint(20, 55)));
        squareShapes.add(new ShapeLine(scalePoint(20, 55), scalePoint(20, 15)));
        squareShapes.add(new ShapeText("Square", List.of(scalePoint(40, 60))));
        ShapeVal squareVal = new ShapeVal(squareShapes);
        imgStore.push(squareVal);

        // 19 Circle approximation using curves
        List<Shape> circleShapes = new ArrayList<>();
        circleShapes.add(new ShapeCurve(
                scalePoint(40, 15), scalePoint(60, 15), scalePoint(60, 35)));
        circleShapes.add(new ShapeCurve(
                scalePoint(60, 35), scalePoint(60, 55), scalePoint(40, 55)));
        circleShapes.add(new ShapeCurve(
                scalePoint(40, 55), scalePoint(20, 55), scalePoint(20, 35)));
        circleShapes.add(new ShapeCurve(
                scalePoint(20, 35), scalePoint(20, 15), scalePoint(40, 15)));
        ShapeVal circleVal = new ShapeVal(circleShapes);
        imgStore.push(circleVal);

        // 18 Bezier curve
        Shape bezier = new ShapeCurve(
                scalePoint(15, 45), scalePoint(40, 15), scalePoint(65, 45));
        ShapeVal bezierWithText = new ShapeVal(List.of(
                bezier,
                new ShapeText("Bezier", List.of(scalePoint(40, 50)))
        ));
        imgStore.push(bezierWithText);

        // 17 Cross made of two lines
        List<Shape> crossShapes = new ArrayList<>();
        crossShapes.add(new ShapeLine(scalePoint(20, 20), scalePoint(60, 60)));
        crossShapes.add(new ShapeLine(scalePoint(20, 60), scalePoint(60, 20)));
        ShapeVal crossVal = new ShapeVal(crossShapes);
        imgStore.push(crossVal);

        // 16 Triangle
        List<Shape> triangleShapes = new ArrayList<>();
        triangleShapes.add(new ShapeLine(scalePoint(40.0, 15.0), scalePoint(60.0, 50.0)));
        triangleShapes.add(new ShapeLine(scalePoint(60.0, 50.0), scalePoint(20.0, 50.0)));
        triangleShapes.add(new ShapeLine(scalePoint(20.0, 50.0), scalePoint(40.0, 15.0)));
        triangleShapes.add(new ShapeText("Triangle", List.of(scalePoint(40, 55))));
        ShapeVal triangleVal = new ShapeVal(triangleShapes);
        imgStore.push(triangleVal);

        // 15 Text
        List<Shape> specialShapes = new ArrayList<>();
        specialShapes.add(new ShapeText("Text:", List.of(scalePoint(40, 15))));
        specialShapes.add(new ShapeText("<= >= == !=", List.of(scalePoint(40, 25))));
        specialShapes.add(new ShapeText("* / % ^ ±", List.of(scalePoint(40, 35))));
        specialShapes.add(new ShapeText("$ € £ ¥ ¢", List.of(scalePoint(40, 45))));
        specialShapes.add(new ShapeText("[] {} \"\" ''", List.of(scalePoint(40, 55))));
        specialShapes.add(new ShapeText("? @ # & | ~", List.of(scalePoint(40, 65))));
        ShapeVal specialText = new ShapeVal(specialShapes);
        imgStore.push(specialText);

        // 9 Sun shape (small) with detached, short rays and text
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

        // 8 Empty string
        List<Shape> emptyTextTest = new ArrayList<>();
        emptyTextTest.add(new ShapeText("Text before empty:", List.of(new Point(75, 30))));
        emptyTextTest.add(new ShapeText("", List.of(new Point(75, 50))));
        emptyTextTest.add(new ShapeText("Text after empty", List.of(new Point(75, 70))));
        // Add markers where empty text should be
        emptyTextTest.add(new ShapeLine(new Point(70, 50), new Point(80, 50)));
        emptyTextTest.add(new ShapeLine(new Point(75, 45), new Point(75, 55)));
        imgStore.push(new ShapeVal(emptyTextTest));

        // 7 Special characters and symbols
        List<Shape> specialCharsTest = new ArrayList<>();
        specialCharsTest.add(new ShapeText("Math: ∑ ∏ ∫ ∞ √ ≈ ≠ ≤ ≥",
                List.of(new Point(75, 20))));
        specialCharsTest.add(new ShapeText("Arrows: ← → ↑ ↓ ↔ ⇒ ⇐ ⇔",
                List.of(new Point(75, 35))));
        specialCharsTest.add(new ShapeText("Greek: α β γ δ ε θ λ π σ ω",
                List.of(new Point(75, 50))));
        specialCharsTest.add(new ShapeText("Brackets: ⟨⟩ ⟦⟧ ⌊⌋ ⌈⌉",
                List.of(new Point(75, 65))));
        specialCharsTest.add(new ShapeText("Logic: ∧ ∨ ¬ ∃ ∀ ⊂ ⊃ ∈ ∉",
                List.of(new Point(75, 80))));
        imgStore.push(new ShapeVal(specialCharsTest));

        // 6 Text at canvas boundaries
        List<Shape> boundaryTextTest = new ArrayList<>();
        // Corner positions
        boundaryTextTest.add(new ShapeText("TopLeft", List.of(new Point(0, 0))));
        boundaryTextTest.add(new ShapeText("TopRight", List.of(new Point(150, 0))));
        boundaryTextTest.add(new ShapeText("BottomLeft", List.of(new Point(0, 150))));
        boundaryTextTest.add(new ShapeText("BottomRight", List.of(new Point(150, 150))));
        // Edge positions
        boundaryTextTest.add(new ShapeText("TopEdge", List.of(new Point(75, 0))));
        boundaryTextTest.add(new ShapeText("BottomEdge", List.of(new Point(75, 150))));
        boundaryTextTest.add(new ShapeText("LeftEdge", List.of(new Point(0, 75))));
        boundaryTextTest.add(new ShapeText("RightEdge", List.of(new Point(150, 75))));
        // Add boundary box
        boundaryTextTest.add(new ShapeLine(new Point(0, 0), new Point(150, 0)));
        boundaryTextTest.add(new ShapeLine(new Point(150, 0), new Point(150, 150)));
        boundaryTextTest.add(new ShapeLine(new Point(150, 150), new Point(0, 150)));
        boundaryTextTest.add(new ShapeLine(new Point(0, 150), new Point(0, 0)));
        imgStore.push(new ShapeVal(boundaryTextTest));

        // 5 Overlapping text
        List<Shape> overlapTextTest = new ArrayList<>();
        overlapTextTest.add(new ShapeText("Background Text Here", List.of(new Point(75, 75))));
        overlapTextTest.add(new ShapeText("Overlapping!", List.of(new Point(80, 77))));
        overlapTextTest.add(new ShapeText("More Overlap", List.of(new Point(70, 73))));
        imgStore.push(new ShapeVal(overlapTextTest));

        // 4 Self-intersecting curve (figure-8)
        List<Shape> selfIntersectTest = new ArrayList<>();
        // First loop of figure-8
        selfIntersectTest.add(new ShapeCurve(new Point(75, 50), new Point(25, 25), new Point(75, 75)));
        // Second loop of figure-8
        selfIntersectTest.add(new ShapeCurve(new Point(75, 75), new Point(125, 125), new Point(75, 50)));
        selfIntersectTest.add(new ShapeText("Self-intersecting", List.of(new Point(75, 140))));
        imgStore.push(new ShapeVal(selfIntersectTest));

        // 3 Control points outside canvas
        List<Shape> outsideControlTest = new ArrayList<>();
        // Control point way above canvas
        outsideControlTest.add(new ShapeCurve(new Point(25, 75), new Point(75, -50), new Point(125, 75)));
        // Control point way below canvas
        outsideControlTest.add(new ShapeCurve(new Point(25, 100), new Point(75, 200), new Point(125, 100)));
        // Control point to the left
        outsideControlTest.add(new ShapeCurve(new Point(50, 25), new Point(-50, 50), new Point(50, 75)));
        // Control point to the right
        outsideControlTest.add(new ShapeCurve(new Point(100, 25), new Point(250, 50), new Point(100, 75)));
        outsideControlTest.add(new ShapeText("Controls outside", List.of(new Point(75, 140))));
        imgStore.push(new ShapeVal(outsideControlTest));

        // 2 Nearly-straight curves (control point on/near line)
        List<Shape> straightCurveTest = new ArrayList<>();
        // Perfectly straight (control point exactly on line)
        Point start1 = new Point(20, 50);
        Point end1 = new Point(130, 50);
        Point control1 = new Point(75, 50); // Exactly on line
        straightCurveTest.add(new ShapeCurve(start1, control1, end1));

        // Almost straight (control point very close to line)
        Point start2 = new Point(20, 70);
        Point end2 = new Point(130, 70);
        Point control2 = new Point(75, 71); // Just 1 pixel off
        straightCurveTest.add(new ShapeCurve(start2, control2, end2));

        // Slightly curved for comparison
        Point start3 = new Point(20, 90);
        Point end3 = new Point(130, 90);
        Point control3 = new Point(75, 80); // 10 pixels off
        straightCurveTest.add(new ShapeCurve(start3, control3, end3));

        straightCurveTest.add(new ShapeText("Straight curves", List.of(new Point(75, 140))));
        imgStore.push(new ShapeVal(straightCurveTest));

        // 1 S-curves and complex paths
        List<Shape> complexCurveTest = new ArrayList<>();
        // S-curve using multiple segments
        complexCurveTest.add(new ShapeCurve(new Point(20, 75), new Point(50, 25), new Point(75, 75)));
        complexCurveTest.add(new ShapeCurve(new Point(75, 75), new Point(100, 125), new Point(130, 75)));

        // Spiral-like curve
        complexCurveTest.add(new ShapeCurve(new Point(75, 50), new Point(100, 50), new Point(100, 75)));
        complexCurveTest.add(new ShapeCurve(new Point(100, 75), new Point(100, 100), new Point(75, 100)));
        complexCurveTest.add(new ShapeCurve(new Point(75, 100), new Point(50, 100), new Point(50, 75)));
        complexCurveTest.add(new ShapeCurve(new Point(50, 75), new Point(50, 50), new Point(75, 50)));

        complexCurveTest.add(new ShapeText("Complex curves", List.of(new Point(75, 140))));
        imgStore.push(new ShapeVal(complexCurveTest));

        // 0 Extreme curves (very sharp angles)
        List<Shape> extremeCurveTest = new ArrayList<>();
        // Very sharp V-shape
        extremeCurveTest.add(new ShapeCurve(new Point(50, 100), new Point(75, 20), new Point(100, 100)));

        // Extreme control point positions
        extremeCurveTest.add(new ShapeCurve(new Point(20, 50), new Point(140, 30), new Point(130, 50)));
        extremeCurveTest.add(new ShapeCurve(new Point(20, 60), new Point(10, 130), new Point(130, 60)));

        // Tiny curves
        extremeCurveTest.add(new ShapeCurve(new Point(70, 120), new Point(75, 115), new Point(80, 120)));

        extremeCurveTest.add(new ShapeText("Extreme curves", List.of(new Point(75, 140))));
        imgStore.push(new ShapeVal(extremeCurveTest));

        try {
            SVGGenerator.generateToFile(imgStore, 150, 150, "testOutput");
            System.out.println("SVG files generated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
