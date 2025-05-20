package afs.interpreter;

public interface VarEnvironment {
    void declare(String ident, int location);
    int lookup(String ident);
}
