package afs.interpreter.interfaces;

import afs.nodes.stmt.StmtNode;
import afs.nodes.type.TypeNode;
import org.javatuples.Quartet;
import org.javatuples.Triplet;

import java.util.List;

public interface FunEnvironment {
    void declare(String ident, Triplet<StmtNode, List<String>, VarEnvironment> fun);

    Triplet<StmtNode, List<String>, VarEnvironment> lookup(String ident);
}