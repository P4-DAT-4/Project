package afs.interpreter.implementations;

import afs.interpreter.interfaces.ImgStore;
import afs.semantic_analysis.types.AFSType;

import java.util.ArrayDeque;
import java.util.Deque;

public class StackImgStore implements ImgStore {
    private final Deque<AFSType> _images;

    public StackImgStore() {
        _images = new ArrayDeque<>();
    }

    @Override
    public void push(AFSType shape) {
        _images.push(shape);
    }

    @Override
    public AFSType pop() {
        boolean isEmpty = _images.isEmpty();
        if (isEmpty) {
            throw new RuntimeException("")
        }
        return _images.pop();
    }
}
