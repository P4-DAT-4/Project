package afs.interpreter.interfaces;

public interface VarEnvironment {
    void declare(String ident, int location);
    int lookup(String ident);

    VarEnvironment newScope();
}
