package interpreter;

import afs.interpreter.ExprInterpreter;

import java.util.List;

import afs.interpreter.expressions.DoubleVal;
import afs.interpreter.expressions.BoolVal;
import afs.interpreter.expressions.IntVal;
import afs.interpreter.expressions.StringVal;
import afs.interpreter.implementations.*;
import afs.interpreter.interfaces.*;
import afs.nodes.expr.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExprInterpreterTest extends ExprInterpreter {
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

    @Nested
    class ExprBaseNodeTest{

        @Test
        public void ExprBoolNode(){

        }

        @Test
        public void IntBoolNode(){

        }

        @Test
        public void ExprDoubleNode(){

        }

        @Test
        public void ExprIntNode(){

        }

        @Test
        public void ExprStringNode(){

        }


    }

    @Nested
    class ExprBinopNodeTest {

        @Test
        public void ExprAddNode(){
            ExprNode left = new ExprIntNode("1",0,0);
            ExprNode right = new ExprIntNode("2",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.ADD, right,0,0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Object value = result.getValue0();

            assertTrue(value instanceof IntVal, "Expected result to an Integer");
            assertEquals(3,  ((IntVal) value).getValue(), "Expected 1 + 3 to equal 3");

        }

        // minus
        public void ExprSubNode(){
            ExprNode left = new ExprIntNode("1",0,0);
            ExprNode right = new ExprIntNode("2",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.SUB, right,0,0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Object value = result.getValue0();

            assertTrue(value instanceof IntVal, "Expected result to an Integer");
            assertEquals(-2,  ((IntVal) value).getValue(), "Expected 1 - 3 to equal 3");

        }


        @Test
        public void ExprMultNode(){
            ExprNode left = new ExprDoubleNode("2",0,0);
            ExprNode right = new ExprDoubleNode("2.5",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.MUL, right,0,0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Object value = result.getValue0();

            assertTrue(value instanceof DoubleVal, "Expected result to an Double");
            assertEquals(5,  ((DoubleVal) value).getValue(), "Expected 2 + 2.5 to equal 5");

        }


        @Test
        public void ExprDivNode(){
            ExprNode left = new ExprIntNode("10",0,0);
            ExprNode right = new ExprIntNode("2",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.DIV, right,0,0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Object value = result.getValue0();

            assertTrue(value instanceof IntVal, "Expected result to an Int");
            assertEquals(5,  ((IntVal) value).getValue(), "Expected 10 / 2 to equal 5");

        }


        @Test
        public void ExprLowerThanNode(){
            ExprNode left = new ExprIntNode("3",0,0);
            ExprNode right = new ExprIntNode("4",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.LT, right,0,0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Object value = result.getValue0();

            assertTrue(value instanceof BoolVal, "Expected result to an Bool");
            assertEquals(true, ((BoolVal) value).getValue(), "Expected 3 < 4 to equal true");
//                                             ^ Comma moved inside the parentheses

        }


        @Test
        public void ExprEqualNode(){
            ExprNode left = new ExprIntNode("3",0,0);
            ExprNode right = new ExprIntNode("4",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.EQ, right,0,0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Object value = result.getValue0();

            assertTrue(value instanceof BoolVal, "Expected result to an Bool, False");
            assertEquals(false,  ((BoolVal) value).getValue(), "Expected 3 to not equal 4");

        }

        @Test
        public void ExprAndNode(){
            ExprNode left = new ExprBoolNode("false",0,0);
            ExprNode right = new ExprBoolNode("true",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.AND, right,0,0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Object value = result.getValue0();

            assertTrue(value instanceof BoolVal, "Expected result to an Bool, False");
            assertEquals(false,  ((BoolVal) value).getValue(), "Expected false to false AND true");

        }

        // Vi bliver nødt til at tilføje ektra '', fordi stringNode cutter de første og sidst væk
        @Test
        public void ExprConcatStrNode(){
            ExprNode left = new ExprStringNode("'sne'",0,0);
            ExprNode right = new ExprStringNode("'mand'",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.CONCAT, right,0,0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Object value = result.getValue0();

            assertTrue(value instanceof StringVal, "Expected result to be String");
            assertEquals("snemand",  ((StringVal) value).getValue(), "Expected snemand from sne mand");
        }

        // concat liste
        // concat shape


    }


    @Nested
    class ExprUnopNodeTest {

        @Test
        public void ExprNegNode() {
            ExprNode  left = new ExprIntNode("7",0,0);
            ExprNode expr = new ExprUnopNode(left, UnOp.NEG,0,0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Object value = result.getValue0();

            assertTrue(value instanceof IntVal, "Expected result to be Integer");
            assertEquals(-7,  ((IntVal) value).getValue(), "Expected -7 from 7");

        }


        @Test
        public void ExprNotNode() {
            ExprNode node = new ExprBoolNode("true",0,0);
            ExprNode expr = new ExprUnopNode(node,UnOp.NOT, 0, 0);
            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Object value = result.getValue0();
            assertTrue(value instanceof BoolVal, "Expected result to an Bool, False");
            assertEquals(false,  ((BoolVal) value).getValue(), "Expected false to neg true");


        }
    }


    @Test
    public void ExprIdentifierNode(){

    }










}
