package afs.interpreter;

public interface Store {
    void store(int location, Object value);
    Object lookup(int location);
}
