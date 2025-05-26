package afs.interpreter.interfaces;

import afs.interpreter.expressions.Val;

public interface Store {
    void bind(int location, Val value);
    Val lookup(int location);
}
