package afs.semantic_analysis;
import afs.semantic_analysis.exceptions.DeclarationException;
import afs.semantic_analysis.exceptions.LookupException;
import afs.semantic_analysis.types.AFSType;

public interface ITypeEnvironment {
    void enterScope();
    void exitScope();
    void declareVariable(String identifier, AFSType type) throws DeclarationException;
    AFSType lookupVariable(String identifier) throws LookupException;
    void declareFunction(String identifier, AFSType type) throws DeclarationException;
    AFSType lookupFunction(String identifier) throws LookupException;
}
