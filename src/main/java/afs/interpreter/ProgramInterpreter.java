package afs.interpreter;

import afs.interpreter.implementations.*;
import afs.interpreter.interfaces.EventEnvironment;
import afs.interpreter.interfaces.FunEnvironment;
import afs.interpreter.interfaces.ImgStore;
import afs.interpreter.interfaces.VarEnvironment;
import afs.nodes.def.DefNode;
import afs.nodes.prog.ProgNode;

public class ProgramInterpreter {
    public static ImgStore evalProg(ProgNode progNode){

        // Initial environments and stores
        VarEnvironment envV = new MapVarEnvironment();
        FunEnvironment funV = new MapFunEnvironment();
        EventEnvironment eventV = new MapEventEnvironment();

        MapStore store = new MapStore();
        ImgStore stackImg = new StackImgStore();

        int location = 0; // location l0

        DefNode defNode = progNode.getDefinition();

        var result = DefInterpreter.evalDef(envV, funV, eventV, location, defNode, store, stackImg);

        return result.getValue1();
    }
}