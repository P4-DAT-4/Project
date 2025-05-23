package afs.interpreter;

import afs.interpreter.implementations.*;
import afs.interpreter.interfaces.ImgStore;
import afs.nodes.def.DefNode;
import afs.nodes.prog.ProgNode;
import afs.interpreter.interfaces.*;

public class ProgramInterpreter {
    public ImgStore evalProg(ProgNode progNode){

        // Initial environments and stores
        VarEnvironment envV = new MapVarEnvironment();
        FunEnvironment funV = new MapFunEnvironment();
        EventEnvironment eventV = new MapEventEnvironment();

        MapStore store = new MapStore();
        StackImgStore stackImg = new StackImgStore();

        int location = store.nextLocation(); // location l0


        DefNode defNode = progNode.getDefinition();

        var result = DefInterpreter.evalDef(envV, funV, eventV, location, defNode, store, stackImg);

        return result.getValue1();


    }
}