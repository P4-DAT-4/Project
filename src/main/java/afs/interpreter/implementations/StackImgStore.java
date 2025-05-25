package afs.interpreter.implementations;

import afs.interpreter.expressions.ShapeVal;
import afs.interpreter.interfaces.ImgStore;

import java.util.ArrayDeque;
import java.util.Deque;

public class StackImgStore implements ImgStore {
    private final Deque<ShapeVal> _images;

    public StackImgStore() {
        _images = new ArrayDeque<>();
    }

    @Override
    public void push(ShapeVal shape) {
        _images.push(shape);
    }

    @Override
    public ShapeVal pop() {
        boolean isEmpty = _images.isEmpty();
        if (isEmpty) {
            throw new RuntimeException("ImgStore is empty");
        }
        return _images.pop();
    }

    @Override
    public boolean isEmpty() {
        return _images.isEmpty();
    }

    @Override
    public int size() {
        return _images.size();
    }
}
