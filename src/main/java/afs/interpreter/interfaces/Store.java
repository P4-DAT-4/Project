package afs.interpreter.interfaces;

import afs.interpreter.expressions.Val;

public interface Store {
    void declare(int location, Val value);
    Val lookup(int location);
    int nextLocation();
}
