package afs.interpreter.interfaces;

import afs.semantic_analysis.types.AFSType;

public interface ImgStore {
    void push(AFSType shape);
    AFSType pop();
}
