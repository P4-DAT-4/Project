package afs.astbuilder.checker;

import afs.astbuilder.checker.exceptions.DeclarationException;
import afs.astbuilder.checker.exceptions.LookupException;
import afs.astbuilder.checker.types.AFSType;

public interface ITypeEnvironment {
    void enterScope();
    void exitScope();
    void declare(String name, AFSType type) throws DeclarationException;
    AFSType lookup(String name) throws LookupException;
}
