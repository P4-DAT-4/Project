package afs.interpreter.implementations;

import afs.interpreter.expressions.Val;
import afs.interpreter.interfaces.Store;

import java.util.HashMap;
import java.util.Map;

public class MapStore implements Store {
    private final Map<Integer, Val> _environment;
    private int location = 0;

    public MapStore() {
        _environment = new HashMap<>();
    }

    @Override
    public void declare(int location, Val value) {
        _environment.put(location, value);
    }

    @Override
    public Val lookup(int location) {
        boolean found = _environment.containsKey(location);
        if (!found) {
            throw new RuntimeException(String.format("Location '%d' not found in Store", location));
        }
        return _environment.get(location);
    }

    @Override
    public int nextLocation() {
        return location++;
    }


}
