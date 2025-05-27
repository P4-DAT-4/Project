package interpreter;

import afs.interpreter.DefInterpreter;
import afs.interpreter.EventInterpreter;
import afs.interpreter.ExprInterpreter;

import java.util.Collections;
import java.util.List;

import afs.interpreter.StmtInterpreter;
import afs.interpreter.expressions.*;
import afs.interpreter.implementations.*;
import afs.interpreter.interfaces.*;
import afs.nodes.def.DefDeclarationNode;
import afs.nodes.def.DefFunctionNode;
import afs.nodes.def.DefVisualizeNode;
import afs.nodes.def.Param;
import afs.nodes.event.EventDeclarationNode;
import afs.nodes.event.EventNode;
import afs.nodes.expr.*;
import afs.nodes.stmt.StmtAssignmentNode;
import afs.nodes.stmt.StmtFunctionCallNode;
import afs.nodes.stmt.StmtNode;
import afs.nodes.stmt.StmtSkipNode;
import afs.nodes.type.TypeIntNode;
import afs.nodes.type.TypeNode;
import afs.nodes.type.TypeVoidNode;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefInterpreterTest {
    private VarEnvironment envV;
    private FunEnvironment envF;
    private EventEnvironment envE;
    private Store store;
    private ImgStore imgStore;
    private int location;

    @BeforeEach
    public void setUp() {
        envV = new MapVarEnvironment();
        envF = new MapFunEnvironment();
        envE = new MapEventEnvironment();
        store = new MapStore();
        imgStore = new StackImgStore();
        location = 0;
    }


    @Test
    public void defFunctionNodeTest() {
        // Visualize function name and param
        String vizFuncName = "visualizeX";
        String vizParamName = "x";
        TypeNode intType = new TypeIntNode(0, 0);
        List<Param> vizParams = List.of(new Param(intType, vizParamName, 0, 0));
        StmtNode vizBody = new StmtSkipNode();

        // Event declaration needed for visualize
        String eventName = "event1";
        List<ExprNode> eventArgs = List.of(new ExprIdentifierNode(vizParamName, 0, 0));
        EventNode eventDecl = new EventDeclarationNode(eventName, vizFuncName, eventArgs, 0, 0);

        DefVisualizeNode defVisualize = new DefVisualizeNode(vizFuncName, eventArgs, eventDecl, 0, 0);


        ExprNode expr42 = new ExprIntNode("42", 0, 0);
        DefDeclarationNode defDeclaration = new DefDeclarationNode(
                new TypeIntNode(0, 0),
                "x", expr42,
                defVisualize,
                0, 0);

        //Create the visualize function definition that must be declared in the function environment
        TypeNode voidType = new TypeVoidNode(0, 0);
        DefFunctionNode defFunction = new DefFunctionNode(voidType, vizFuncName, vizParams, vizBody, defDeclaration, 0, 0);
        // Evaluate and register the visualize function first
        DefInterpreter.evalDef(envV, envF, envE, location, defFunction, store, imgStore);


        // Evaluate the definition chain
        DefInterpreter.evalDef(envV, envF, envE, location, defDeclaration, store, imgStore);


        // Now check that function is registered in envF
        try {
            var funEntry = envF.lookup(vizFuncName);
            assertNotNull(funEntry, "Function '" + vizFuncName + "' should be registered in the function environment");
        } catch (RuntimeException e) {
            fail("Function '" + vizFuncName + "' was not found in function environment");
        }

        // Check that store and imgStore are not null
        assertNotNull(store, "Store should be returned");
        assertNotNull(imgStore, "Image store should be returned");


    }


    @Test
    public void defVisualizeNodeTest() {
        // Setup basic data
        String vizFuncName = "visualizeX";
        String vizParamName = "x";

        // Visualize function parameter (int x)
        TypeNode intType = new TypeIntNode(0, 0);
        List<Param> vizParams = List.of(new Param(intType, vizParamName, 0, 0));

        // Visualize function body: skip (empty)
        StmtNode vizBody = new StmtSkipNode();

        // Create event declaration linked to visualize function
        List<ExprNode> eventArgs = List.of(new ExprIdentifierNode(vizParamName, 0, 0));
        EventNode eventDecl = new EventDeclarationNode("event1", vizFuncName, eventArgs, 0, 0);

        // Create DefVisualizeNode (visualize function + event)
        DefVisualizeNode defVisualize = new DefVisualizeNode(vizFuncName, eventArgs, eventDecl, 0, 0);

        // Evaluate event directly to simulate visualize evaluation
        EventEnvironment updatedEnvE = EventInterpreter.evalEvent(eventDecl, envE);

        // Now assert that the event environment has the event "event1"
        ExprFunctionCallNode registeredCall = updatedEnvE.lookup("event1");
        assertNotNull(registeredCall, "Event 'event1' should be registered in the event environment");
        assertInstanceOf(ExprFunctionCallNode.class, registeredCall, "Registered event should be a function call");

        assertEquals(vizFuncName, registeredCall.getIdentifier(), "Event should be linked to 'visualizeX' function");
        assertEquals(eventArgs, registeredCall.getArguments(), "Event arguments should match the visualize function parameters");
    }
}

