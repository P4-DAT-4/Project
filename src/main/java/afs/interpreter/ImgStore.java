package afs.interpreter;

import afs.semantic_analysis.types.AFSType;

public interface ImgStore {
    void push(AFSType shape);
    AFSType pop();
}
