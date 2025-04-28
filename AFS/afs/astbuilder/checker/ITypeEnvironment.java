package afs.astbuilder.checker;

import afs.astbuilder.checker.types.AFSType;

public interface ITypeEnvironment {
    void enterScope();
    void exitScope();
    void declare(String name, AFSType type);
    AFSType lookup(String name);
}
