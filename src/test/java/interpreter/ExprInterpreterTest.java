package interpreter;

import afs.interpreter.ExprInterpreter;

import java.util.List;

import afs.interpreter.StmtInterpreter;
import afs.interpreter.expressions.*;
import afs.interpreter.implementations.*;
import afs.interpreter.interfaces.*;
import afs.nodes.expr.*;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExprInterpreterTest{
    private static ExprInterpreter exprInterpreter;
    private VarEnvironment envV;
    private FunEnvironment envF;
    private EventEnvironment envE;
    private Store store;
    private ImgStore imgStore;
    private int location;

    @BeforeEach
    public void setUp(){
        exprInterpreter = new ExprInterpreter();
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
            ExprBoolNode expr = new ExprBoolNode("false", 0, 0);
            var result = exprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            BoolVal value = (BoolVal) result.getValue0();

            assertTrue(value instanceof BoolVal, "Expected result to an Bool");
            assertEquals(false,  ((BoolVal) value).getValue(), "Expected false");

        }

        @Test
        public void ExprDoubleNode(){
            ExprDoubleNode expr = new ExprDoubleNode("8.10", 0, 0);
            var result = exprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            DoubleVal value = (DoubleVal) result.getValue0();

            assertTrue(value instanceof DoubleVal, "Expected result to an Double");
            assertEquals(8.10,  ((DoubleVal) value).getValue(), "Expected 8.10");

        }

        @Test
        public void ExprIntNode(){
            ExprIntNode expr = new ExprIntNode("50", 0, 0);
            var result = exprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            IntVal value = (IntVal) result.getValue0();

            assertTrue(value instanceof IntVal, "Expected result to an Integer");
            assertEquals(50,  ((IntVal) value).getValue(), "Expected 50");
        }

        @Test
        public void ExprStringNode(){
            ExprStringNode expr = new ExprStringNode("'good'", 0, 0);
            var result = exprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            StringVal value = (StringVal) result.getValue0();

            assertTrue(value instanceof StringVal, "Expected result to an String");
            assertEquals("good",  ((StringVal) value).getValue(), "Expected good");
        }


    }

    @Nested
    class ExprBinopNodeTest {

        @Test
        public void ExprAddNode(){
            ExprNode left = new ExprIntNode("1",0,0);
            ExprNode right = new ExprIntNode("2",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.ADD, right,0,0);

            var result = exprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            IntVal value = (IntVal) result.getValue0();

            assertTrue(value instanceof IntVal, "Expected result to an Integer");
            assertEquals(3,  ((IntVal) value).getValue(), "Expected 1 + 3 to equal 3");

        }

        // minus
        public void ExprSubNode(){
            ExprNode left = new ExprIntNode("1",0,0);
            ExprNode right = new ExprIntNode("2",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.SUB, right,0,0);

            var result = exprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            IntVal value = (IntVal) result.getValue0();

            assertTrue(value instanceof IntVal, "Expected result to an Integer");
            assertEquals(-2,  ((IntVal) value).getValue(), "Expected 1 - 3 to equal 3");

        }


        @Test
        public void ExprMultNode(){
            ExprNode left = new ExprDoubleNode("2",0,0);
            ExprNode right = new ExprDoubleNode("2.5",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.MUL, right,0,0);

            var result = exprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Object value = result.getValue0();

            assertTrue(value instanceof DoubleVal, "Expected result to an Double");
            assertEquals(5,  ((DoubleVal) value).getValue(), "Expected 2 + 2.5 to equal 5");

        }


        @Test
        public void ExprDivNode(){
            ExprNode left = new ExprIntNode("10",0,0);
            ExprNode right = new ExprIntNode("2",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.DIV, right,0,0);

            var result = exprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            IntVal value = (IntVal) result.getValue0();

            assertTrue(value instanceof IntVal, "Expected result to an Int");
            assertEquals(5,  ((IntVal) value).getValue(), "Expected 10 / 2 to equal 5");

        }


        @Test
        public void ExprLowerThanNode(){
            ExprNode left = new ExprIntNode("3",0,0);
            ExprNode right = new ExprIntNode("4",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.LT, right,0,0);

            var result = exprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            BoolVal value = (BoolVal) result.getValue0();

            assertTrue(value instanceof BoolVal, "Expected result to an Bool");
            assertEquals(true, ((BoolVal) value).getValue(), "Expected 3 < 4 to equal true");
//                                             ^ Comma moved inside the parentheses

        }


        @Test
        public void ExprEqualNode(){
            ExprNode left = new ExprIntNode("3",0,0);
            ExprNode right = new ExprIntNode("4",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.EQ, right,0,0);

            var result = exprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            BoolVal value = (BoolVal)  result.getValue0();

            assertTrue(value instanceof BoolVal, "Expected result to an Bool, False");
            assertEquals(false,  ((BoolVal) value).getValue(), "Expected 3 to not equal 4");

        }

        @Test
        public void ExprAndNode(){
            ExprNode left = new ExprBoolNode("false",0,0);
            ExprNode right = new ExprBoolNode("true",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.AND, right,0,0);

            var result = exprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            BoolVal value = (BoolVal) result.getValue0();

            assertTrue(value instanceof BoolVal, "Expected result to an Bool, False");
            assertEquals(false,  ((BoolVal) value).getValue(), "Expected false to false AND true");

        }


        @Test
        public void ExprConcatStrNode(){
            ExprNode left = new ExprStringNode("'sne'",0,0);
            ExprNode right = new ExprStringNode("'mand'",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.CONCAT, right,0,0);

            var result = exprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            StringVal value = (StringVal)  result.getValue0();

            assertTrue(value instanceof StringVal, "Expected result to be String");
            assertEquals("snemand",  ((StringVal) value).getValue(), "Expected snemand from sne mand");
        }

        // concat liste
        @Test
        public void ExprConcatListNode(){
            List<ExprNode> leftListExpr = List.of(
                    new ExprIntNode("1",0,0),
                    new ExprIntNode("2",0,0)

            );
            ExprNode leftList = new ExprListDeclaration(leftListExpr, 0, 0);

            List<ExprNode> rightListExprs = List.of(
                    new ExprIntNode("3",0,0),
                    new ExprIntNode("4",0,0)

            );
            ExprNode rightList = new ExprListDeclaration(rightListExprs, 0, 0);


            ExprNode expr = new ExprBinopNode(leftList, BinOp.CONCAT, rightList,0,0);

            var result = exprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            ListVal listVal = (ListVal)  result.getValue0();
            assertTrue(listVal instanceof ListVal, "Expected result to be a ListVal");

            List<Val> elements = listVal.getValue();

            assertEquals(4, elements.size(), "Expected concatenated list to have 4 elements");


            assertEquals(1, ((IntVal) elements.get(0)).getValue(), "Expected 1");
            assertEquals(2, ((IntVal) elements.get(1)).getValue(), "Expected 2");
            assertEquals(3, ((IntVal) elements.get(2)).getValue(), "Expected 3");
            assertEquals(4, ((IntVal) elements.get(3)).getValue(), "Expected 4");


        }

        // concat shape
     //   public void ExprConcatShapeCurveNode(){
//            ExprNode shapeExpr1 = new ExprLineNode(List.of(
//                    new ExprDoubleNode("0", 0, 0),
//                    new ExprDoubleNode("0", 0, 0),
//                    new ExprDoubleNode("1", 0, 0),
//                    new ExprDoubleNode("1", 0, 0),
//                    new ExprDoubleNode("2", 0, 0),
//                    new ExprDoubleNode("2", 0, 0)
//
//            ), 0, 0);
//
//
//            ExprNode shapeExpr2 = new ExprLineNode(List.of(
//                    new ExprDoubleNode("2", 0, 0),
//                    new ExprDoubleNode("2", 0, 0),
//                    new ExprDoubleNode("3", 0, 0),
//                    new ExprDoubleNode("3", 0, 0)
//            ), 0, 0);
//
//            // Build concat expression: shape1 ++ shape2
//            ExprNode concatExpr = new ExprBinopNode(shapeExpr1, BinOp.CONCAT, shapeExpr2, 0, 0);
//
//            // Evaluate
//            var result = exprInterpreter.evalExpr(envV, envF, envE, location, concatExpr, store, imgStore);
//            Val val = (Val) result.getValue0();
//
//            assertTrue(val instanceof ShapeVal, "Expected ShapeVal");
//
//            ShapeVal shapeVal = (ShapeVal) val;
//            Shape resultShape = shapeVal.getShape();
//            List<Shape.Segment> segments = resultShape.getSegments();
//
//            // Assertions
//            assertEquals(2, segments.size(), "Expected 2 segments after concat");
//
//            Shape.Segment first = segments.get(0);
//            Shape.Segment second = segments.get(1);
//
//            assertEquals(Shape.Segment.SegmentType.CURVE, first.getType(), "First should be Curve");
//            assertEquals(Shape.Segment.SegmentType.LINE, second.getType(), "Second should be Line");
//
//
//            assertEquals(3, first.getCoordinates().size(), "Curve should have 3 points");
//            assertEquals(2, second.getCoordinates().size(), "Line 2 should have 3 points");
        }


     //   public void ExprConcatShapeLineNode(){
//            ExprNode shapeExpr1 = new ExprLineNode(List.of(
//                    new ExprDoubleNode("0", 0, 0),
//                    new ExprDoubleNode("0", 0, 0),
//                    new ExprDoubleNode("1", 0, 0),
//                    new ExprDoubleNode("1", 0, 0)
//            ), 0, 0);
//
//
//            ExprNode shapeExpr2 = new ExprLineNode(List.of(
//                    new ExprDoubleNode("2", 0, 0),
//                    new ExprDoubleNode("2", 0, 0),
//                    new ExprDoubleNode("3", 0, 0),
//                    new ExprDoubleNode("3", 0, 0)
//            ), 0, 0);
//
//            // Build concat expression: shape1 ++ shape2
//            ExprNode concatExpr = new ExprBinopNode(shapeExpr1, BinOp.CONCAT, shapeExpr2, 0, 0);
//
//            // Evaluate
//            var result = exprInterpreter.evalExpr(envV, envF, envE, location, concatExpr, store, imgStore);
//            Val val = (Val) result.getValue0();
//
//            assertTrue(val instanceof ShapeVal, "Expected ShapeVal");
//
//            ShapeVal shapeVal = (ShapeVal) val;
//            Shape resultShape = shapeVal.getShape();
//            List<Shape.Segment> segments = resultShape.getSegments();
//
//            // Assertions
//            assertEquals(2, segments.size(), "Expected 2 segments after concat");
//
//            Shape.Segment first = segments.get(0);
//            Shape.Segment second = segments.get(1);
//
//            assertEquals(Shape.Segment.SegmentType.LINE, first.getType(), "First should be LINE");
//            assertEquals(Shape.Segment.SegmentType.LINE, second.getType(), "Second should be Line");
//
//
//            assertEquals(2, first.getCoordinates().size(), "Line 1 should have 2 points");
//            assertEquals(3, second.getCoordinates().size(), "Line 2 should have 3 points");
      //  }


   //  }


    @Nested
    class ExprUnopNodeTest {

        @Test
        public void ExprNegNode() {
            ExprNode  left = new ExprIntNode("7",0,0);
            ExprNode expr = new ExprUnopNode(left, UnOp.NEG,0,0);

            var result = exprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Object value = result.getValue0();

            assertTrue(value instanceof IntVal, "Expected result to be Integer");
            assertEquals(-7,  ((IntVal) value).getValue(), "Expected -7 from 7");

        }


        @Test
        public void ExprNotNode() {
            ExprNode node = new ExprBoolNode("true",0,0);
            ExprNode expr = new ExprUnopNode(node,UnOp.NOT, 0, 0);
            var result = exprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Object value = result.getValue0();
            assertTrue(value instanceof BoolVal, "Expected result to an Bool, False");
            assertEquals(false,  ((BoolVal) value).getValue(), "Expected false to neg true");


        }
    }


    @Nested
    class ExprIdentifierNodeTest{
        @Test
        public void ExprIdentifierIntNode(){

            // set up test data
            String varName = "x";
            IntVal value = new IntVal(42);

            // Simulate declaring variable
            envV.declare(varName, location);
            store.declare(location, value);

            ExprIdentifierNode identifierExpr = new ExprIdentifierNode(varName, 0, 0);
            var result = exprInterpreter.evalExpr(envV, envF, envE, location, identifierExpr, store, imgStore);

            assertTrue(result.getValue0() instanceof IntVal, "Expected an IntVal");
            assertEquals(new IntVal(42), result.getValue0(), "Expected value to be 42");
        }

        @Test
        public void ExprIdentifierStrNode(){

            // set up test data
            String varName = "x";
            StringVal value = new StringVal("Hej");

            // Simulate declaring variable
            envV.declare(varName, location);
            store.declare(location, value);

            ExprIdentifierNode identifierExpr = new ExprIdentifierNode(varName, 0, 0);
            var result = exprInterpreter.evalExpr(envV, envF, envE, location, identifierExpr, store, imgStore);

            assertTrue(result.getValue0() instanceof StringVal, "Expected an String");
            assertEquals(new StringVal("Hej"), result.getValue0(), "Expected 'hej'");
        }

        @Test
        public void ExprIdentifierBoolNode(){

            // set up test data
            String varName = "x";
            BoolVal value = new BoolVal(true);

            // Simulate declaring variable
            envV.declare(varName, location);
            store.declare(location, value);

            ExprIdentifierNode identifierExpr = new ExprIdentifierNode(varName, 0, 0);
            var result = exprInterpreter.evalExpr(envV, envF, envE, location, identifierExpr, store, imgStore);

            assertTrue(result.getValue0() instanceof BoolVal, "Expected an bool");
            assertEquals(new BoolVal(true), result.getValue0(), "Expected true");
        }

    }


    @Nested
    class ExprListDeclarationNodeTest{

        @Test
        public void ExprListDecl1D(){
            List<ExprNode> exprs = List.of(
                    new ExprIntNode("1",0,0),
                    new ExprIntNode("3",0,0),
                    new ExprIntNode("6",0,0)
                    );

            ExprListDeclaration listDeclaration = new ExprListDeclaration(exprs, 0,0);
            var result = exprInterpreter.evalExpr(envV, envF, envE, location, listDeclaration, store, imgStore);

            ListVal listVal = (ListVal) result.getValue0();
            Store updatedStore = result.getValue1();


            assertTrue(listVal instanceof ListVal, "Expected a ListVal");

            // Check content of ListVal
            List<Val> evaluatedElements = listVal.getValue();
            assertEquals(3, evaluatedElements.size(), "Expected list of size 3");

            assertEquals(1, ((IntVal) evaluatedElements.get(0)).getValue(), "Expected to have 1");
            assertEquals(3, ((IntVal) evaluatedElements.get(1)).getValue(), "Expected to have 3");
            assertEquals(6, ((IntVal) evaluatedElements.get(2)).getValue(), "Expected to have 6");

            // Check store
            Val storedVal = (Val) updatedStore.lookup(location);
            assertNotNull(storedVal, "Expected store to contain a value at location");
            assertTrue(storedVal instanceof ListVal, "Expected stored value to be a ListVal");
            assertEquals(listVal, storedVal, "Expected store to contain the evaluated list");

        }

        @Test
        public void ExprListDecl2D() {
            // First inner list: [1, 2]
            List<ExprNode> innerList1 = List.of(
                    new ExprIntNode("1", 0, 0),
                    new ExprIntNode("2", 0, 1)
            );
            ExprListDeclaration innerExpr1 = new ExprListDeclaration(innerList1, 0, 0);

            // Second inner list: [3, 4]
            List<ExprNode> innerList2 = List.of(
                    new ExprIntNode("3", 0, 2),
                    new ExprIntNode("4", 0, 3)
            );
            ExprListDeclaration innerExpr2 = new ExprListDeclaration(innerList2, 0, 0);

            // Outer list: [[1, 2], [3, 4]]
            List<ExprNode> outerList = List.of(innerExpr1, innerExpr2);
            ExprListDeclaration nestedList = new ExprListDeclaration(outerList, 0, 0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, nestedList, store, imgStore);

            // Top-level result
            ListVal outerListVal = (ListVal) result.getValue0();
            List<Val> outerElements = outerListVal.getValue();
            assertEquals(2, outerElements.size(), "Expected outer list to have 2 elements");

            for (Val val : outerElements) {
                assertTrue(val instanceof ListVal, "Expected each element to be a ListVal");
            }

            // Inner lists
            ListVal firstInner = (ListVal) outerElements.get(0);
            ListVal secondInner = (ListVal) outerElements.get(1);

            assertEquals(2, firstInner.getValue().size(), "Expected first inner to have size 2");
            assertEquals(2, secondInner.getValue().size(), "Expected second innter to have size 2");

            assertEquals(1, ((IntVal) firstInner.getValue().get(0)).getValue(), "Expected 1");
            assertEquals(2, ((IntVal) firstInner.getValue().get(1)).getValue(), "Expected 2");

            assertEquals(3, ((IntVal) secondInner.getValue().get(0)).getValue(), "Expected 3");
            assertEquals(4, ((IntVal) secondInner.getValue().get(1)).getValue(), "Expected 4");

            // Store check
            Val stored = (Val) result.getValue1().lookup(location);
            assertEquals(outerListVal, stored, "Expected the store to contain the outer nested list");
        }


    }


    @Nested
    class ExprListAccessNodeTest{

        @Test
        public void ExprListAccessNode1D(){
            // Set up array
            List<ExprNode> exprs = List.of(
                    new ExprIntNode("1",0,0),
                    new ExprIntNode("3",0,0),
                    new ExprIntNode("6",0,0)
            );

            ExprListDeclaration listDeclaration = new ExprListDeclaration(exprs, 0,0);
            var resultListDecl = ExprInterpreter.evalExpr(envV, envF, envE, location, listDeclaration, store, imgStore);

            ListVal listVal = (ListVal) resultListDecl.getValue0();
            String varName = "myList";
            envV.declare(varName, location);
            store.declare(location, listVal);

            // Acces with index 1
            ExprNode indexExpr = new ExprIntNode("1", 0, 0);
            ExprListAccessNode listAccessNode = new ExprListAccessNode(varName, List.of(indexExpr), 0, 0);

            var resultListAccess = ExprInterpreter.evalExpr(envV, envF, envE, location, listAccessNode, store, imgStore);
            assertTrue(resultListAccess.getValue0() instanceof IntVal, "Expected result to be IntVal");
            assertEquals(3, ((IntVal) resultListAccess.getValue0()).getValue(), "Expected value to be 3");

        }


        @Test
        public void ExprListAccessNode1DOutOfBounds(){
            // Set up array
            List<ExprNode> exprs = List.of(
                    new ExprIntNode("1",0,0),
                    new ExprIntNode("3",0,0),
                    new ExprIntNode("6",0,0)
            );

            ExprListDeclaration listDeclaration = new ExprListDeclaration(exprs, 0,0);
            var resultListDecl = ExprInterpreter.evalExpr(envV, envF, envE, location, listDeclaration, store, imgStore);

            ListVal listVal = (ListVal) resultListDecl.getValue0();
            String varName = "myList";
            envV.declare(varName, location);
            store.declare(location, listVal);

            // Invalid access: index 5 (out of bounds)
            ExprNode invalidIndexExpr = new ExprIntNode("5", 0, 0);
            ExprListAccessNode invalidAccess = new ExprListAccessNode(varName, List.of(invalidIndexExpr), 0, 0);

            Exception exception = assertThrows(RuntimeException.class, () -> {
                exprInterpreter.evalExpr(envV, envF, envE, location, invalidAccess, store, imgStore);
            });

            assertTrue(exception.getMessage().contains("out of bounds"), "Expected an out-of-bounds exception message");



        }



        @Test
        public void ExprListAccessNode2D(){
            // Create nested lists: [ [1, 2], [3, 4] ]
            List<ExprNode> innerList1 = List.of(
                    new ExprIntNode("1", 0, 0),
                    new ExprIntNode("2", 0, 0)
            );
            List<ExprNode> innerList2 = List.of(
                    new ExprIntNode("3", 0, 0),
                    new ExprIntNode("4", 0, 0)
            );

            ExprListDeclaration subList1 = new ExprListDeclaration(innerList1, 0, 0);
            ExprListDeclaration subList2 = new ExprListDeclaration(innerList2, 0, 0);

            // Evaluate sublists
            var resultSubList1 = ExprInterpreter.evalExpr(envV, envF, envE, location, subList1, store, imgStore);
            Store updatedStore = resultSubList1.getValue1();
            ImgStore currentImgStore = resultSubList1.getValue2();
            var resultSubList2 = ExprInterpreter.evalExpr(envV, envF, envE, location, subList2, updatedStore , currentImgStore);

            ListVal evaluatedSubList1 = (ListVal) resultSubList1.getValue0();
            ListVal evaluatedSubList2 = (ListVal) resultSubList2.getValue0();

            // Combine into outer list
            ListVal outerList = new ListVal(List.of(evaluatedSubList1, evaluatedSubList2));

            // Store outer list in environment
            String varName = "matrix";
            envV.declare(varName, location);
            updatedStore = resultSubList2.getValue1();
            updatedStore.declare(location, outerList);

            // Access matrix[1][0], which should be 3
            ExprNode outerIndex = new ExprIntNode("1", 0, 0);  // second row
            ExprNode innerIndex = new ExprIntNode("0", 0, 0);  // first element
            ExprListAccessNode accessNode = new ExprListAccessNode(varName, List.of(outerIndex, innerIndex), 0, 0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, accessNode, updatedStore, imgStore);


            assertTrue(result.getValue0() instanceof IntVal, "Expected IntVal from matrix[1][0]");
            assertEquals(3, ((IntVal) result.getValue0()).getValue(), "Expected value 3 from matrix[1][0]");
        }


        }


//    @Test
//    public void exprTextNodeTest(){
////        ExprNode textExpr = new ExprStringNode("'Hello'", 0, 0);
////        ExprNode expr = new ExprTextNode(textExpr, 0, 0);
////
////        var result = exprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore);
////        ShapeVal shapeVal = (ShapeVal) result.getValue0();
////        Store updatedStore = result.getValue1();
////        ImgStore updatedImgStore = result.getValue2();
////
////        assertTrue(shapeVal instanceof ShapeVal, "Expected StringVal from ExprTextNode evaluation");
//
//    }





}












