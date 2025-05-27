package interpreter;

import afs.interpreter.DefInterpreter;
import afs.interpreter.ExprInterpreter;
import afs.interpreter.StmtInterpreter;
import afs.interpreter.expressions.IntVal;
import afs.interpreter.expressions.Val;
import afs.interpreter.implementations.*;
import afs.interpreter.interfaces.*;
import afs.nodes.def.DefFunctionNode;
import afs.nodes.def.DefNode;
import afs.nodes.def.DefVisualizeNode;
import afs.nodes.def.Param;
import afs.nodes.event.EventDeclarationNode;
import afs.nodes.event.EventNode;
import afs.nodes.expr.*;
import afs.nodes.stmt.*;
import afs.nodes.type.TypeIntNode;
import afs.nodes.type.TypeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import setup.ASTGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StmtInterpreterTest {
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

    @Test
    public void StmtAssignmentNodeTest(){
        // Declare x = 5
        envV.declare("x", location);
        store.bind(location, new IntVal(5));

        // assign x = 10
        ExprNode expr = new ExprIntNode("10",0,0);
        StmtNode assignment = new StmtAssignmentNode("x",expr ,0,0);

        StmtInterpreter.evalStmt(envV, envF, envE, location, assignment, store, imgStore);

        Val val = store.lookup(location);

        assertInstanceOf(IntVal.class, val, "Expected value to be of type IntVal");
        assertEquals(new IntVal(10), val, "Expected 10");
    }


    @Nested
    class StmtCompositionNodeTest {
        @Test
        public void StmtCompositionNode() {
            // Setup: declare int variable x = 0
            String varName = "x";
            envV.declare(varName, ++location);
            store.bind(location, new IntVal(0));

            // First statement: x = 5;
            ExprNode valueX = new ExprIntNode("5", 0, 0);
            StmtNode assignX = new StmtAssignmentNode(varName, valueX, 0, 0);

            // Second statement: return x;
            ExprNode idX = new ExprIdentifierNode(varName, 0, 0);
            StmtNode returnStmt = new StmtReturnNode(idX, 0, 0);

            // Compose: assignX; return x;
            StmtNode composition = new StmtCompositionNode(assignX, returnStmt, 0, 0);

            // Evaluate composition
            var result = StmtInterpreter.evalStmt(envV, envF, envE, location, composition, store, imgStore);

            // Result should be return value 5
            Val retVal = (Val) result.getValue0();
            assertTrue(retVal instanceof IntVal, "Return value should be IntVal");
            assertEquals(new IntVal(5), retVal, "Return value should be 5");

            // Store should reflect x = 5
            Val storedX = (Val) result.getValue1().lookup(location);
            assertEquals(new IntVal(5), storedX, "Stored value of x should be 5");


        }

        @Test
        public void StmtCompositionScopeNodeTest() {
            // if (true) { int x = 5; }  // declare x inside if block
            StmtNode ifStmt = new StmtIfNode(
                    new ExprIntNode("1", 0, 0),  // condition = true
                    new StmtDeclarationNode(
                            new TypeIntNode(0, 0),   // int type
                            "x",
                            new ExprIntNode("5", 0, 0),
                            new StmtSkipNode(),      // no next statement inside if block
                            0, 0
                    ),
                    new StmtSkipNode(),            // else skip
                    0, 0
            );

            // Compose: if statement + assignment x = 10 outside if block
            StmtNode stmt = new StmtCompositionNode(
                    ifStmt,
                    new StmtAssignmentNode("x", new ExprIntNode("10", 0, 0), 0, 0),
                    0, 0
            );


            // The assignment to x should fail (x out of scope)
            assertThrows(RuntimeException.class, () -> {
                StmtInterpreter.evalStmt(envV, envF, envE, location, stmt, store, imgStore);
            }, "Expected RuntimeException when assigning to variable outside its scope");


        }


    }

    @Test
    public void StmtDeclarationNodeTest(){
        // Arrange
        String type = "int";
        String varName = "testVarX";
        int testValue = 50;
        String testInput = String.format("%s %s = %d;", type, varName, testValue);
        StmtNode stmt = ASTGenerator.parseStmt(testInput);

        // Act
        StmtInterpreter.evalStmt(envV, envF, envE, location, stmt, store, imgStore);

        // Assert
        Val val = store.lookup(location);
        assertInstanceOf(IntVal.class, val);
        assertEquals(testValue, val.asInt());
    }


    @Nested
    class StmtFunctionCallNodeTest{
        @Test
        public void StmtFunctionNode(){

            // Setup function parameter x
            String paramName =  "x";
            TypeNode type = new TypeIntNode(0,0);
            List<Param> params = List.of ( new Param(type,paramName,0,0));

            // Setup function body return x + 1
            ExprNode paramExpr = new ExprIdentifierNode(paramName, 0, 0);
            ExprNode value = new ExprIntNode("1",0,0);
            ExprNode bodyExpr = new ExprBinopNode(paramExpr, BinOp.ADD, value, 0,0);
            StmtNode funcBody = new StmtReturnNode(bodyExpr, 0, 0);


            // Create function definition
            String funcName = "increment";
            String eventName = "ev1";
            List<ExprNode> eventArgs = List.of(new ExprIntNode("2", 0, 0));

            // Use helper to create the function def node
            DefNode functionDef = createFunction(type, funcName, params, funcBody, eventName, eventArgs);


            // Evaluate the function declaration to register it in function environment
            DefInterpreter.evalDef(envV, envF, envE, location, functionDef, store, imgStore);


            // Create function call increment(6)
            List<ExprNode> args = List.of(new ExprIntNode("6",0,0));
            StmtNode funcCall = new StmtFunctionCallNode(funcName, args,0,0);

            // Evaluate the function call statement using StmtInterpreter.evalStmt
            var callResult = StmtInterpreter.evalStmt(envV, envF, envE, location, funcCall, store, imgStore);

            // Get the return value
            Val returnVal = callResult.getValue0();

            // Your assertions here, for example:
            assertNotNull(returnVal);
            assertInstanceOf(IntVal.class, returnVal);
            assertEquals(7, ((IntVal) returnVal).getValue());

        }

        @Test
        public void StmtFunctionPassByValueNode_LiteralPassed(){
            // Arrange
            String varName = "x";
            int initialVal = 5;
            String input = String.format(
                    "int %s = %d;\n" +
                    "\n" +
                    "fn void increment(int x) {\n" +
                    "    %s = %s + 1;\n" +
                    "}\n" +
                    "\n" +
                    "visualize increment(1):\n" +
                    "    a do b();", varName, initialVal, varName, varName);
            DefNode def = ASTGenerator.parseDef(input);

            // Act
            DefInterpreter.evalDef(envV, envF, envE, location, def, store, imgStore);

            // Assert
            Val finalVal = store.lookup(location);
            assertEquals(initialVal, finalVal.asInt(), "Expected the value not to be changed");
        }

        @Test
        public void StmtFunctionPassByValueNode_IdentifierPassed(){
            // Arrange
            String varName = "x";
            int initialVal = 5;
            String input = String.format(
                    "int %s = %d;\n" +
                    "\n" +
                    "fn void increment(int x) {\n" +
                    "    %s = %s + 1;\n" +
                    "}\n" +
                    "\n" +
                    "visualize increment(x):\n" +
                    "    a do b();", varName, initialVal, varName, varName);
            DefNode def = ASTGenerator.parseDef(input);

            // Act
            DefInterpreter.evalDef(envV, envF, envE, location, def, store, imgStore);

            // Assert
            Val finalVal = store.lookup(location);
            assertEquals(initialVal, finalVal.asInt(), "Expected the value not to be changed");
        }

        @Test
        public void StmtFunctionPassByReference_IdentifierPassed(){
            // Arrange
            String varName = "x";
            int value0 = 4, value1 = 5;
            String listValue = String.format("[%d, %d]", value0, value1);
            ExprNode exprNode = ASTGenerator.parseExpr(listValue);
            String input = String.format(
                    "[int] %s = [1, 2, 3];\n" +
                    "\n" +
                    "fn void increment([int] x) {\n" +
                    "    %s = %s;\n" +
                    "}\n" +
                    "\n" +
                    "visualize increment(x):\n" +
                    "    a do b();", varName, varName, listValue);
            DefNode def = ASTGenerator.parseDef(input);

            // Act
            Val listVal = ExprInterpreter.evalExpr(envV, envF, envE, location, exprNode, store, imgStore).getValue0();
            DefInterpreter.evalDef(envV, envF, envE, location, def, store, imgStore);

            // Assert
            Val finalVal = store.lookup(location);
            assertEquals(listVal.asList().size(), finalVal.asList().size(), "Expected the value to be changed");
            assertEquals(value0, listVal.asList().getFirst().asInt());
            assertEquals(value1, listVal.asList().getLast().asInt());
        }

        @Test
        public void StmtFunctionPassByReference_IdentifierIndexPassed(){
            // Arrange
            String varName = "x";
            String listValue = "[4, 5, 9]";
            String input = String.format(
                    "[[int]] %s = [[1, 2, 3],[6,7]];\n" +
                    "\n" +
                    "fn void increment([[int]] x) {\n" +
                    "    %s[0] = %s;\n" +
                    "}\n" +
                    "\n" +
                    "visualize increment(x[1]):\n" +
                    "    a do b();", varName, varName, listValue);
            DefNode def = ASTGenerator.parseDef(input);

            // Act
            Executable action = () -> DefInterpreter.evalDef(envV, envF, envE, location, def, store, imgStore);

            // Assert
            Exception exception = assertThrows(RuntimeException.class, action);
            assertTrue(
                    exception.getMessage().contains("Arrays are call-by-reference - cannot pass an array literal or an index of an array"),
                    "Expected error as x[1] cannot be looked up in the environment"
            );
        }

        @Test
        public void StmtFunctionPassByReference_LiteralArrayPassed(){
            // Arrange
            String varName = "x";
            String listValue = "[4, 5, 9]";
            String input = String.format(
                    "[int] %s = [1, 2, 3];\n" +
                    "\n" +
                    "fn void increment([int] x) {\n" +
                    "    %s = %s;\n" +
                    "}\n" +
                    "\n" +
                    "visualize increment([7,8,10]):\n" +
                    "    a do b();", varName, varName, listValue);
            DefNode def = ASTGenerator.parseDef(input);

            // Act
            Executable action = () -> DefInterpreter.evalDef(envV, envF, envE, location, def, store, imgStore);

            // Assert
            Exception exception = assertThrows(RuntimeException.class, action);
            assertTrue(
                    exception.getMessage().contains("Arrays are call-by-reference - cannot pass an array literal or an index of an array"),
                    "Expected error as x[1] cannot be looked up in the environment"
            );
        }
    }

    @Test
    public void StmtIfNodeTest(){
        // Declare x
        String varName = "x";
        envV.declare(varName, location);
        store.bind(location, new IntVal(0));

        // if (true) then x = 1 else x = 2
        ExprNode exprThen = new ExprIntNode("1",0,0);
        StmtNode thenStmt = new StmtAssignmentNode(varName, exprThen, 0,0);
        ExprNode exprElse = new ExprIntNode("2",0,0);
        StmtNode elseStmt = new StmtAssignmentNode(varName, exprElse, 0,0);
        ExprNode bool = new ExprBoolNode("true",0,0);
        StmtNode ifStmt = new StmtIfNode(bool,thenStmt,elseStmt,0,0);

        var result = StmtInterpreter.evalStmt(envV, envF, envE, location, ifStmt, store, imgStore);
        Val value = result.getValue1().lookup(location);
        assertEquals(new IntVal(1), value, "Expected 1");


    }


    @Nested
    class StmtListAssignmentNodeTest{

        @Test
        public void StmtListAssignmentNode1D(){
            String varName = "myList";
            location = declareList(varName, List.of(
                    new ExprIntNode("1", 0, 0),
                    new ExprIntNode("3", 0, 0),
                    new ExprIntNode("6", 0, 0)
            ));

            // Assign to myList[1] = 10;
            List<ExprNode> indexExprs = List.of(new ExprIntNode("1",0,0));
            ExprNode newVal = new ExprIntNode("10",0,0);
            StmtNode listAssingment = new StmtListAssignmentNode(varName, indexExprs, newVal, 0,0);

            // run list assignmemnt
            var result2 = StmtInterpreter.evalStmt(envV, envF, envE, location, listAssingment, store, imgStore);

            // validate updated list
            Val updatedList = result2.getValue1().lookup(location);
            List<Val> elements = updatedList.asList();

            assertEquals(3, elements.size(), "Expected updated list size to remain 3");
            assertEquals(new IntVal(1), elements.get(0), "Expected first element unchanged");
            assertEquals(new IntVal(10), elements.get(1), "Expected second element updated to 10");
            assertEquals(new IntVal(6), elements.get(2), "Expected third element unchanged");

        }

        @Test
        public void StmtListAssignmentNode2D(){
            // Prepare nested expressions as List of List<ExprNode>
            List<List<ExprNode>> nestedExprs = List.of(
                    List.of(new ExprIntNode("1", 0, 0), new ExprIntNode("2", 0, 0)),
                    List.of(new ExprIntNode("3", 0, 0), new ExprIntNode("4", 0, 0))
            );

            // Declare variable for the 2D list
            String varName = "my2DList";
            location = declare2DList(varName, nestedExprs);


            // Assign to my2DList[1][0] = 10;
            List<ExprNode> indexExprs = List.of(
                    new ExprIntNode("1", 0, 0),  // second row
                    new ExprIntNode("0", 0, 0)   // first column
            );

            ExprNode newVal = new ExprIntNode("10", 0, 0);
            StmtNode listAssignment = new StmtListAssignmentNode(varName, indexExprs, newVal, 0, 0);

            // Run list assignment
            var result2 = StmtInterpreter.evalStmt(envV, envF, envE, location, listAssignment, store, imgStore);

            // Validate updated list
            Val updatedOuterList = result2.getValue1().lookup(location);
            List<Val> outerElements = updatedOuterList.asList();

            assertEquals(2, outerElements.size(), "Outer list size should remain 2");

            // First row should be unchanged: [1, 2]
            Val firstRow = outerElements.getFirst();
            List<Val> firstRowElems = firstRow.asList();
            assertEquals(new IntVal(1), firstRowElems.get(0), "Unchanged element in first row, first column");
            assertEquals(new IntVal(2), firstRowElems.get(1), "Unchanged element in first row, second column");


            // Second row: [10, 4] after update
            Val secondRow = outerElements.get(1);
            List<Val> secondRowElems = secondRow.asList();
            assertEquals(new IntVal(10), secondRowElems.get(0), "Updated element in second row, first column");
            assertEquals(new IntVal(4), secondRowElems.get(1), "Unchanged element in second row, second column");

        }

        @Test
        public void StmtListAssignmentNodeOutOfBounds(){

            // Prepare list: [1, 3, 6]
            String varName = "myList";
            location = declareList(varName, List.of(
                    new ExprIntNode("1", 0, 0),
                    new ExprIntNode("3", 0, 0),
                    new ExprIntNode("6", 0, 0)
            ));

            // myList[10] = 99  → Invalid index
            List<ExprNode> indexExprs = List.of(new ExprIntNode("10", 0, 0));  // out of bounds
            ExprNode newVal = new ExprIntNode("99", 0, 0);
            StmtNode listAssignment = new StmtListAssignmentNode(varName, indexExprs, newVal, 0, 0);

            // Assert exception thrown
            assertThrows(IndexOutOfBoundsException.class, () -> StmtInterpreter.evalStmt(envV, envF, envE, location, listAssignment, store, imgStore), "Expected out-of-bounds assignment to throw IndexOutOfBoundsException");
        }

    }


    @Test
    public void StmtReturnNodeTest(){
        ExprNode returnExpr = new ExprIntNode("10", 0, 0);
        StmtNode returnStmt = new StmtReturnNode(returnExpr,0,0);

        // Evaluate return statment
        var result = StmtInterpreter.evalStmt(envV, envF, envE, location, returnStmt, store, imgStore);
        Val returnVal =  result.getValue0();

        assertInstanceOf(IntVal.class, returnVal, "Returned value should be IntVal");
        assertEquals(new IntVal(10), returnVal, "Returned value should be 10");

    }


    @Test
    public void StmtSkipNodeTest(){
        StmtNode skipStmt = new StmtSkipNode();

        // Evaluate the skip statement
        var result = StmtInterpreter.evalStmt(envV, envF, envE, location, skipStmt, store, imgStore);

        // The value should be null since skip returns null as the first tuple value
        assertNull(result.getValue0(), "Expected null as return value for skip statement");

    }

    @Test
    public void StmtWhileNodeTest(){
        // Declare x = 0
        String varName = "x";
        envV.declare(varName, location);
        IntVal val = new IntVal(0);
        store.bind(location, val);

        // While (x < 3)
        ExprNode left = new ExprIdentifierNode(varName, 0,0);
        ExprNode right = new ExprIntNode("3",0,0);
        ExprNode condition = new ExprBinopNode(left, BinOp.LT, right, 0,0);

        // Body x = x + 1;
        ExprNode leftI = new ExprIdentifierNode(varName, 0,0);
        ExprNode rightI = new ExprIntNode("1",0,0);
        ExprNode incrementExpr = new ExprBinopNode(leftI, BinOp.ADD, rightI, 0,0);
        StmtNode body = new StmtAssignmentNode(varName, incrementExpr, 0,0);

        // Whil loop node
        StmtNode whileStmt = new StmtWhileNode(condition,body,0,0);
        var result = StmtInterpreter.evalStmt(envV, envF, envE, location, whileStmt, store, imgStore);

        Val finalVal = result.getValue1().lookup(location);
        assertEquals(new IntVal(3), finalVal, "Expected 3");

    }



    private int declareList(String varName, List<ExprNode> exprs) {
        ExprNode listDeclar = new ExprListDeclaration(exprs, 0, 0);
        var result = ExprInterpreter.evalExpr(envV, envF, envE, location, listDeclar, store, imgStore);
        Val listVal = result.getValue0();

        envV.declare(varName, ++location);
        store.bind(location, listVal);
        return location;
    }

    private int declare2DList(String varName, List<List<ExprNode>> nestedExprs) {
        // Convert inner lists into ExprListDeclaration nodes
        List<ExprNode> outerList = nestedExprs.stream()
                .map(innerList -> (ExprNode) new ExprListDeclaration(innerList, 0, 0))
                .toList();

        ExprNode nestedListDecl = new ExprListDeclaration(outerList, 0, 0);

        var result = ExprInterpreter.evalExpr(envV, envF, envE, location, nestedListDecl, store, imgStore);
        Val listVal = result.getValue0();

        envV.declare(varName, ++location);
        store.bind(location, listVal);

        return location;
    }

    private DefNode createFunction(
            TypeNode returnType,
            String funcName,
            List<Param> params,
            StmtNode body,
            String eventName,
            List<ExprNode> eventArgs
    ) {
        EventNode eventDecl = new EventDeclarationNode(eventName, funcName, eventArgs, 0, 0);
        DefNode visualize = new DefVisualizeNode(funcName, eventArgs, eventDecl, 0, 0);

        return new DefFunctionNode(returnType, funcName, params, body, visualize, 0, 0);
    }
}
