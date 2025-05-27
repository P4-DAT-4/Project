package interpreter;

import afs.interpreter.DefInterpreter;
import afs.interpreter.EventInterpreter;
import afs.interpreter.implementations.*;
import afs.interpreter.interfaces.*;
import afs.nodes.event.EventCompositionNode;
import org.junit.jupiter.api.BeforeEach;


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

public class EventInterpreterTest {
    private VarEnvironment envV;
    private FunEnvironment envF;
    private EventEnvironment envE;
    private Store store;
    private ImgStore imgStore;
    private int location;

    @BeforeEach
    public void setUp(){
        envV = new MapVarEnvironment();
        envF = new MapFunEnvironment();
        envE = new MapEventEnvironment();
        store = new MapStore();
        imgStore = new StackImgStore();
        location = 0;
    }

    @Test void EventCompositionNodeTest(){
        // Setup the first event (e1)
        String e1VarName = "eventA";
        String e1FuncName = "funcA";
        List<ExprNode> e1Args = List.of(); // empty or simple args
        EventDeclarationNode e1 = new EventDeclarationNode(e1VarName, e1FuncName, e1Args, 0, 0);

        // Setup the second event (e2)
        String e2VarName = "eventB";
        String e2FuncName = "funcB";
        List<ExprNode> e2Args = List.of();
        EventDeclarationNode e2 = new EventDeclarationNode(e2VarName, e2FuncName, e2Args, 0, 0);

        // Compose e1 and e2 into an EventCompositionNode
        EventCompositionNode eventComposition = new EventCompositionNode(e1, e2, 0, 0);

        // Evaluate the composed event
        EventEnvironment finalEnvE = EventInterpreter.evalEvent(eventComposition, envE);

        // Now check that both event variables are declared in the final environment
        try {
            ExprNode boundE1 = finalEnvE.lookup(e1VarName);
            assertNotNull(boundE1, "Event variable '" + e1VarName + "' should be declared");
        } catch (RuntimeException ex) {
            fail("Event variable '" + e1VarName + "' not found in environment: " + ex.getMessage());
        }

        try {
            ExprNode boundE2 = finalEnvE.lookup(e2VarName);
            assertNotNull(boundE2, "Event variable '" + e2VarName + "' should be declared");
        } catch (RuntimeException ex) {
            fail("Event variable '" + e2VarName + "' not found in environment: " + ex.getMessage());
        }
    }



    @Test
    public void EventDeclarationNodeTest(){
        // Step 1: Setup function and visualize as usual
        String vizFuncName = "visualizeX";
        String vizParamName = "x";
        TypeNode intType = new TypeIntNode(0, 0);
        List<Param> vizParams = List.of(new Param(intType, vizParamName, 0, 0));
        StmtNode vizBody = new StmtSkipNode();

        // Step 2: Setup event declaration node with function call args
        String eventName = "event1";
        List<ExprNode> eventArgs = List.of(new ExprIdentifierNode(vizParamName, 0, 0));
        EventDeclarationNode eventDecl = new EventDeclarationNode(eventName, vizFuncName, eventArgs, 0, 0);

        // Step 3: Wrap event declaration in a DefVisualizeNode
        DefVisualizeNode defVisualize = new DefVisualizeNode(vizFuncName, eventArgs, eventDecl, 0, 0);

        // Step 4: Create DefDeclarationNode with the defVisualize chain (this will trigger eventDecl eval)
        ExprNode expr42 = new ExprIntNode("42", 0, 0);
        DefDeclarationNode defDeclaration = new DefDeclarationNode(
                new TypeIntNode(0, 0),
                "x", expr42,
                defVisualize,
                0, 0);

        // Step 5: Create and register visualize function (needed for eventDecl to resolve)
        TypeNode voidType = new TypeVoidNode(0, 0);
        DefFunctionNode defFunction = new DefFunctionNode(voidType, vizFuncName, vizParams, vizBody, defDeclaration, 0, 0);
        DefInterpreter.evalDef(envV, envF, envE, location, defFunction, store, imgStore);

        // Step 6: Evaluate the DefDeclarationNode which contains the event declaration
        DefInterpreter.evalDef(envV, envF, envE, location, defDeclaration, store, imgStore);

        // Step 7: Assert that the event variable is declared in envE as a function call
        ExprNode boundExpr = null;
        try {
            boundExpr = envE.lookup(eventName);
        } catch (RuntimeException e) {
            fail("Event variable '" + eventName + "' not declared in expression environment: " + e.getMessage());
        }

        assertNotNull(boundExpr, "Event variable should be bound in the expression environment");
        assertTrue(boundExpr instanceof ExprFunctionCallNode, "Event variable should be bound to a function call expression");

        ExprFunctionCallNode funcCall = (ExprFunctionCallNode) boundExpr;
        assertEquals(eventArgs, funcCall.getArguments(), "Function call arguments should match the event declaration");

        // Also verify store and imgStore as usual
        assertNotNull(store, "Store should not be null");
        assertNotNull(imgStore, "Image store should not be null");

    }



}
