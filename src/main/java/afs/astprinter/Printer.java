package afs.astprinter;

import afs.nodes.prog.ProgNode;

public interface Printer {
    void print(ProgNode program, String filePath, boolean printTypes);
}