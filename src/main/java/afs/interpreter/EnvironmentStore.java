package afs.interpreter;

import afs.semantic_analysis.types.AFSType;

import java.util.Deque;
import java.util.Map;

public class EnvironmentStore implements IEnvironmentStore {
    private static final Deque<Map<String, Integer>> envVar = new java.util.ArrayDeque<>();
    private static final Map<Integer, Object> locations = new java.util.HashMap<>();
    private static int locationCounter = 0;

    @Override
    public void nextLocation() {
        locationCounter++;
    }

    private EnvironmentStore() {}

    private static class Holder {
        private static final EnvironmentStore INSTANCE = new EnvironmentStore();
    }

    @Override
    public IEnvironmentStore getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void enterScope() {

    }

    @Override
    public void exitScope() {

    }

    @Override
    public void declareVariable(String identifier, Object value) {
    }

    @Override
    public AFSType lookupVariable(String identifier) {
        return null;
    }

    @Override
    public void declareFunction(String identifier, Object value) {

    }

    @Override
    public Object callFunction(String identifier) {
        return null;
    }
}
