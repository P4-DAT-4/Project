package afs.SVGGenerator;

import static afs.SVGGenerator.SVGGenerator.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        List<SVGElement> shapes = Arrays.asList(
                // Large square
                new Line(10, 10, 60, 10),
                new Line(60, 10, 60, 60),
                new Line(60, 60, 10, 60),
                new Line(10, 60, 10, 10),
                new Text(15, 80, "Square"),

                // Circle (approximated using Bezier curves)
                new BezierCurve(120, 10, 147, 10, 147, 35),
                new BezierCurve(147, 35, 147, 60, 120, 60),
                new BezierCurve(120, 60, 93, 60, 93, 35),
                new BezierCurve(93, 35, 93, 10, 120, 10),
                new Text(100, 80, "Circle"),

                // Diagonal line
                new Line(180, 10, 230, 60),
                new Text(185, 80, "Line"),

                // Bezier curve
                new BezierCurve(250, 10, 275, 0, 300, 60),
                new Text(255, 80, "Bezier"),

                // Six small squares below (each made of 4 lines)
                // Square 1
                new Line(10, 110, 30, 110),
                new Line(30, 110, 30, 130),
                new Line(30, 130, 10, 130),
                new Line(10, 130, 10, 110),

                // Square 2
                new Line(40, 110, 60, 110),
                new Line(60, 110, 60, 130),
                new Line(60, 130, 40, 130),
                new Line(40, 130, 40, 110),

                // Square 3
                new Line(70, 110, 90, 110),
                new Line(90, 110, 90, 130),
                new Line(90, 130, 70, 130),
                new Line(70, 130, 70, 110),

                // Square 4
                new Line(100, 110, 120, 110),
                new Line(120, 110, 120, 130),
                new Line(120, 130, 100, 130),
                new Line(100, 130, 100, 110),

                // Square 5
                new Line(130, 110, 150, 110),
                new Line(150, 110, 150, 130),
                new Line(150, 130, 130, 130),
                new Line(130, 130, 130, 110),

                // Square 6
                new Line(160, 110, 180, 110),
                new Line(180, 110, 180, 130),
                new Line(180, 130, 160, 130),
                new Line(160, 130, 160, 110),
                new Text(50, 150, "array of squares")
        );

        try {
            SVGGenerator.generateToFile(shapes, 400, 200, "test7.svg");
            System.out.println("SVG saved as test7.svg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}