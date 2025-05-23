package afs.interpreter.interfaces;

import afs.interpreter.expressions.ShapeVal;

public interface ImgStore {
    void push(ShapeVal shape);
    ShapeVal pop();
    boolean isEmpty();
}
