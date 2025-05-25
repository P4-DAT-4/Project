package interpreter;

import afs.interpreter.ExprInterpreter;
import afs.interpreter.StmtInterpreter;
import afs.interpreter.expressions.*;
import afs.interpreter.implementations.*;
import afs.interpreter.interfaces.*;
import afs.nodes.expr.ExprNode;
import afs.nodes.stmt.StmtNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.ASTGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StmtInterpreterTest {
    private static VarEnvironment envV = new MapVarEnvironment();
    private static FunEnvironment envF = new MapFunEnvironment();
    private static EventEnvironment envE = new MapEventEnvironment();
    private static Store store = new MapStore();
    private static ImgStore imgStore = new StackImgStore();
    private static int location = 0;

    public static void main(String[] args) {
        String list = "[[[1,2,3],[4,5],[7]],[[12,11],[13],[14],[15,16,17,18]]]";
        ExprNode listNode = ASTGenerator.parseExpr(list);
        Val listVal = ExprInterpreter.evalExpr(envV, envF, envE, location, listNode, store, imgStore).getValue0();
        printVal(listVal);
    }

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
    public void stmtListAssignmentValidIndex() {
        String varName = "arr";
        // Create an ExprNode representing an array
        String list = "[[[1,2,3],[4,5],[7]],[[12,11],[13],[14],[15,16,17,18]]]";
        ExprNode listNode = ASTGenerator.parseExpr(list);

        // Interpret the expression to get a value
        Val listVal = ExprInterpreter.evalExpr(envV, envF, envE, location, listNode, store, imgStore).getValue0();
        printVal(listVal);

        // Store the value
        envV.declare(varName, location);
        store.declare(location, listVal);

        int i = 1, j = 3, k = 2, val = 30;
        String input = String.format(
                "{" +
                        "%s[%d][%d][%d] = %d;" +
                "}", varName, i, j, k, val);

        // Create the StmtNode and interpret it
        StmtNode block = ASTGenerator.parseStmtBlock(input);
        StmtInterpreter.evalStmt(envV, envF, envE, location, block, store, imgStore);

        Val resultVal = store.lookup(envV.lookup(varName));
        assertEquals(val, resultVal.asList().get(i).asList().get(j).asList().get(k).asInt());
    }

    @Test
    public void stmtListAssignmentInvalidIndex() {
        String varName = "arr";
        // Create an ExprNode representing an array
        String list = "[[[1,2,3],[4,5],[7]],[[12,11],[13],[14],[15,16,17,18]]]";
        ExprNode listNode = ASTGenerator.parseExpr(list);

        // Interpret the expression to get a value
        int location = 0;
        Val listVal = ExprInterpreter.evalExpr(envV, envF, envE, location, listNode, store, imgStore).getValue0();
        printVal(listVal);

        // Store the value
        envV.declare(varName, location);
        store.declare(location, listVal);

        int i = 1, j = 5, k = 2, val = 30;
        String input = String.format(
                "{" +
                        "%s[%d][%d][%d] = %d;" +
                        "}", varName, i, j, k, val);

        // Create the StmtNode and interpret it
        StmtNode block = ASTGenerator.parseStmtBlock(input);
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            StmtInterpreter.evalStmt(envV, envF, envE, location, block, store, imgStore);
        });
        assertEquals(String.format("Index %d out of bounds for length %d", j, 4), exception.getMessage());
    }

    private static void printVal(Val val) {
        switch (val) {
            case BoolVal boolVal -> {
                System.out.print(boolVal.asBool());
            }
            case DoubleVal doubleVal -> {
                System.out.print(doubleVal.asDouble());
            }
            case IntVal intVal -> {
                System.out.print(intVal.asInt());
            }
            case ListVal listVal -> {
                printArray(listVal);
            }
            case ShapeVal shapeVal -> {
                System.out.print("Shape");
            }
            case StringVal stringVal -> {
                System.out.print(stringVal.asString());
            }
        }
        System.out.print(", ");
    }

    private static void printArray(ListVal listVal) {
        System.out.print("[");
        for (Val val : listVal.asList()) {
            printVal(val);
        }
        System.out.print("]");
        System.out.println();
    }
}
