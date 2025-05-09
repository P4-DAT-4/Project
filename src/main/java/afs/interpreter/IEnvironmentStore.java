package afs.interpreter;

public interface IEnvironmentStore {
    IEnvironmentStore getInstance();
    void enterScope();
    void exitScope();
    void declareVariable(String identifier, Object value);
    Object lookupVariable(String identifier);
    void declareFunction(String identifier, Object value);
    Object callFunction(String identifier);
    void nextLocation();
}
