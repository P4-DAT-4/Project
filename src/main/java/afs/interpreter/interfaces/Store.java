package afs.interpreter.interfaces;

public interface Store {
    void store(int location, Object value);
    Object lookup(int location);
}
