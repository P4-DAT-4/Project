package afs.interpreter.interfaces;

public interface VarEnvironment {
    void declare(String ident, int location);
    int lookup(String ident);

    void set(String ident, int location);

    VarEnvironment newScope();
    boolean isLocal(String ident);
}
