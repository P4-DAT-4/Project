package afs.interpreter.expressions;

import afs.interpreter.expressions.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class ShapeVal implements Val{
    private final List<Shape> shapes;

    public ShapeVal(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public ShapeVal(Shape... shapes) {
        this.shapes = new ArrayList<>(List.of(shapes));
    }

    public ShapeVal(Val... shapeVals) {
        this.shapes = new ArrayList<>();
        for (Val val : shapeVals) {
            this.shapes.addAll(((ShapeVal) val).getValue());
        }
    }

    public List<Shape> getValue() {
        return shapes;
    }
}
