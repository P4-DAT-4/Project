package afs.interpreter.implementations;

import afs.interpreter.interfaces.FunEnvironment;
import afs.interpreter.interfaces.VarEnvironment;
import afs.nodes.stmt.StmtNode;
import org.javatuples.Triplet;

import java.util.HashMap;
import java.util.List;

public class MapFunEnvironment implements FunEnvironment {
    private final HashMap<String, Triplet<StmtNode, List<String>, VarEnvironment>> _environment;

    public MapFunEnvironment() {
        _environment = new HashMap<>();
    }

    @Override
    public void declare(String ident, Triplet<StmtNode, List<String>, VarEnvironment> fun) {
        _environment.put(ident, fun);
    }

    @Override
    public Triplet<StmtNode, List<String>, VarEnvironment> lookup(String ident) {
        boolean found = _environment.containsKey(ident);
        if (!found) {
            throw new RuntimeException(String.format("Function '%s' not found in FunEnvironment", ident));
        }
        return _environment.get(ident);
    }
}
