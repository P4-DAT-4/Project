package afs.interpreter.expressions;

import afs.runtime.Shape;

public class ShapeVal implements Val{
    private final Shape shape;

    public ShapeVal(Shape shape) {
        this.shape = shape;
    }

    public Shape getShape() {
        return shape;
    }

    @Override
    public String toString() {
        return shape.toString();
    }

    @Override
    public Val copy() {
        return new ShapeVal(shape);
    }

}
