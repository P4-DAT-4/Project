package interpreter;

import afs.interpreter.ExprInterpreter;
import afs.interpreter.expressions.*;
import afs.interpreter.expressions.shape.*;
import afs.interpreter.implementations.*;
import afs.interpreter.interfaces.*;
import afs.nodes.expr.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import setup.ASTGenerator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExprInterpreterTest{
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
            ExprBoolNode expr = new ExprBoolNode("false", 0, 0);
            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Val value = result.getValue0();

            assertInstanceOf(BoolVal.class, value, "Expected result to an Bool");
            assertFalse(value.asBool(), "Expected false");

        }

        @Test
        public void ExprDoubleNode(){
            ExprDoubleNode expr = new ExprDoubleNode("8.10", 0, 0);
            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Val value = result.getValue0();

            assertInstanceOf(DoubleVal.class, value, "Expected result to an Double");
            assertEquals(8.10, value.asDouble(), "Expected 8.10");

        }

        @Test
        public void ExprIntNode(){
            ExprIntNode expr = new ExprIntNode("50", 0, 0);
            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Val value = result.getValue0();

            assertInstanceOf(IntVal.class, value, "Expected result to an Integer");
            assertEquals(50,  value.asInt(), "Expected 50");
        }

        @Test
        public void ExprStringNode(){
            ExprStringNode expr = new ExprStringNode("'good'", 0, 0);
            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Val value = result.getValue0();

            assertInstanceOf(StringVal.class, value, "Expected result to an String");
            assertEquals("good",  value.asString(), "Expected good");
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
            Val value = result.getValue0();

            assertInstanceOf(IntVal.class, value, "Expected result to an Integer");
            assertEquals(3,  value.asInt(), "Expected 1 + 3 to equal 3");

        }

        @Test
        public void ExprSubNode(){
            // Arrange
            ExprNode left = new ExprIntNode("1",0,0);
            ExprNode right = new ExprIntNode("2",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.SUB, right,0,0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Val value = result.getValue0();

            assertInstanceOf(IntVal.class, value, "Expected result to an Integer");
            assertEquals(-1,  value.asInt(), "Expected 1 - 2 to equal -1");

        }


        @Test
        public void ExprMultNode(){
            ExprNode left = new ExprDoubleNode("2",0,0);
            ExprNode right = new ExprDoubleNode("2.5",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.MUL, right,0,0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Val value = result.getValue0();

            assertInstanceOf(DoubleVal.class, value, "Expected result to an Double");
            assertEquals(5, value.asDouble(), "Expected 2 + 2.5 to equal 5");

        }


        @Test
        public void ExprDivNode(){
            ExprNode left = new ExprIntNode("10",0,0);
            ExprNode right = new ExprIntNode("2",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.DIV, right,0,0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Val value = result.getValue0();

            assertInstanceOf(IntVal.class, value, "Expected result to an Int");
            assertEquals(5,  value.asInt(), "Expected 10 / 2 to equal 5");

        }


        @Test
        public void ExprLowerThanNode(){
            ExprNode left = new ExprIntNode("3",0,0);
            ExprNode right = new ExprIntNode("4",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.LT, right,0,0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Val value = result.getValue0();

            assertInstanceOf(BoolVal.class, value, "Expected result to an Bool");
            assertTrue(value.asBool(), "Expected 3 < 4 to equal true");
        }


        @Test
        public void ExprEqualNode(){
            ExprNode left = new ExprIntNode("3",0,0);
            ExprNode right = new ExprIntNode("4",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.EQ, right,0,0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Val value = result.getValue0();

            assertInstanceOf(BoolVal.class, value, "Expected result to an Bool, False");
            assertFalse(value.asBool(), "Expected 3 to not equal 4");

        }

        @Test
        public void ExprAndNode(){
            ExprNode left = new ExprBoolNode("false",0,0);
            ExprNode right = new ExprBoolNode("true",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.AND, right,0,0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Val value = result.getValue0();

            assertInstanceOf(BoolVal.class, value, "Expected result to an Bool, False");
            assertFalse(value.asBool(), "Expected false to false AND true");

        }


        @Test
        public void ExprConcatStrNode(){
            ExprNode left = new ExprStringNode("'sne'",0,0);
            ExprNode right = new ExprStringNode("'mand'",0,0);
            ExprNode expr = new ExprBinopNode(left, BinOp.CONCAT, right,0,0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Val value = result.getValue0();

            assertInstanceOf(StringVal.class, value, "Expected result to be String");
            assertEquals("snemand", value.asString(), "Expected 'snemand' from 'sne' ++ 'mand'");
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

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Val listVal = result.getValue0();
            assertInstanceOf(ListVal.class, listVal, "Expected result to be a ListVal");

            List<Val> elements = listVal.asList();

            assertEquals(4, elements.size(), "Expected concatenated list to have 4 elements");


            assertEquals(1, ((IntVal) elements.get(0)).getValue(), "Expected 1");
            assertEquals(2, ((IntVal) elements.get(1)).getValue(), "Expected 2");
            assertEquals(3, ((IntVal) elements.get(2)).getValue(), "Expected 3");
            assertEquals(4, ((IntVal) elements.get(3)).getValue(), "Expected 4");


        }

        @Test
        public void ExprConcatShapeLineNode(){
            // Arrange
            String line1 = "line (0.0, 0.0) to (1.0, 1.0) to (2.0, 2.0)";
            String line2 = "line (2.0, 2.0) to (3.0, 3.0)";
            ExprNode lineExpr1 = ASTGenerator.parseDeclExpr(line1);
            ExprNode lineExpr2 = ASTGenerator.parseDeclExpr(line2);
            ExprNode concat = new ExprBinopNode(lineExpr1, BinOp.CONCAT, lineExpr2, 0, 0);

            // Act
            Val lineVal1 = ExprInterpreter.evalExpr(envV, envF, envE, location, lineExpr1, store, imgStore).getValue0();
            Val lineVal2 = ExprInterpreter.evalExpr(envV, envF, envE, location, lineExpr2, store, imgStore).getValue0();
            Val resultVal = ExprInterpreter.evalExpr(envV, envF, envE, location, concat, store, imgStore).getValue0();
            int lineElements1 = lineVal1.asShape().size();
            int lineElements2 = lineVal2.asShape().size();
            int resultElements = resultVal.asShape().size();

            // Assert
            assertInstanceOf(ShapeVal.class, lineVal1);
            assertInstanceOf(ShapeVal.class, lineVal2);
            assertInstanceOf(ShapeVal.class, resultVal);
            assertEquals(lineElements1 + lineElements2, resultElements);
        }

        public void ExprConcatShapeCurveNode(){
            // Arrange
            String curve1 = "curve (0.0, 0.0) to (1.0, 1.0) to (2.0, 2.0)";
            String curve2 = "curve (2.0, 2.0) to (3.0, 3.0) to (4.0, 4.0) to (5.0, 5.0) to (6.0, 6.0)";
            ExprNode curveExpr1 = ASTGenerator.parseDeclExpr(curve1);
            ExprNode curveExpr2 = ASTGenerator.parseDeclExpr(curve2);
            ExprNode concat = new ExprBinopNode(curveExpr1, BinOp.CONCAT, curveExpr2, 0, 0);

            // Act
            Val lineVal1 = ExprInterpreter.evalExpr(envV, envF, envE, location, curveExpr1, store, imgStore).getValue0();
            Val lineVal2 = ExprInterpreter.evalExpr(envV, envF, envE, location, curveExpr2, store, imgStore).getValue0();
            Val resultVal = ExprInterpreter.evalExpr(envV, envF, envE, location, concat, store, imgStore).getValue0();
            int curveElements1 = lineVal1.asShape().size();
            int curveElements2 = lineVal2.asShape().size();
            int resultElements = resultVal.asShape().size();

            // Assert
            assertInstanceOf(ShapeVal.class, lineVal1);
            assertInstanceOf(ShapeVal.class, lineVal2);
            assertInstanceOf(ShapeVal.class, resultVal);
            assertEquals(curveElements1 + curveElements2, resultElements);
        }
    }

    @Nested
    class ExprUnopNodeTest {

        @Test
        public void ExprNegNode() {
            ExprNode  left = new ExprIntNode("7",0,0);
            ExprNode expr = new ExprUnopNode(left, UnOp.NEG,0,0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Val value = result.getValue0();

            assertInstanceOf(IntVal.class, value, "Expected result to be Integer");
            assertEquals(-7,  value.asInt(), "Expected -7 from 7");

        }


        @Test
        public void ExprNotNode() {
            ExprNode node = new ExprBoolNode("true",0,0);
            ExprNode expr = new ExprUnopNode(node,UnOp.NOT, 0, 0);
            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore );
            Val value = result.getValue0();

            assertInstanceOf(BoolVal.class, value, "Expected result to an Bool, False");
            assertFalse(value.asBool(), "Expected false to neg true");


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
            store.bind(location, value);

            ExprIdentifierNode identifierExpr = new ExprIdentifierNode(varName, 0, 0);
            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, identifierExpr, store, imgStore);

            assertInstanceOf(IntVal.class, result.getValue0(), "Expected an IntVal");
            assertEquals(new IntVal(42), result.getValue0(), "Expected value to be 42");
        }

        @Test
        public void ExprIdentifierStrNode(){

            // set up test data
            String varName = "x";
            String strValue = "test";
            StringVal value = new StringVal(strValue);

            // Simulate declaring variable
            envV.declare(varName, location);
            store.bind(location, value);

            ExprIdentifierNode identifierExpr = new ExprIdentifierNode(varName, 0, 0);
            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, identifierExpr, store, imgStore);

            assertInstanceOf(StringVal.class, result.getValue0(), "Expected an String");
            assertEquals(strValue, result.getValue0().asString(), "Expected 'hej'");
        }

        @Test
        public void ExprIdentifierBoolNode(){

            // set up test data
            String varName = "x";
            BoolVal value = new BoolVal(true);

            // Simulate declaring variable
            envV.declare(varName, location);
            store.bind(location, value);

            ExprIdentifierNode identifierExpr = new ExprIdentifierNode(varName, 0, 0);
            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, identifierExpr, store, imgStore);

            assertInstanceOf(BoolVal.class, result.getValue0(), "Expected an bool");
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
            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, listDeclaration, store, imgStore);

            Val listVal = result.getValue0();

            assertInstanceOf(ListVal.class, listVal, "Expected a ListVal");

            // Check content of ListVal
            List<Val> evaluatedElements = listVal.asList();
            assertEquals(3, evaluatedElements.size(), "Expected list of size 3");

            assertEquals(1, ((IntVal) evaluatedElements.get(0)).getValue(), "Expected to have 1");
            assertEquals(3, ((IntVal) evaluatedElements.get(1)).getValue(), "Expected to have 3");
            assertEquals(6, ((IntVal) evaluatedElements.get(2)).getValue(), "Expected to have 6");
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
            Val outerListVal = result.getValue0();
            List<Val> outerElements = outerListVal.asList();
            assertEquals(2, outerElements.size(), "Expected outer list to have 2 elements");

            for (Val val : outerElements) {
                assertInstanceOf(ListVal.class, val, "Expected each element to be a ListVal");
            }

            // Inner lists
            ListVal firstInner = (ListVal) outerElements.get(0);
            ListVal secondInner = (ListVal) outerElements.get(1);

            assertEquals(2, firstInner.asList().size(), "Expected first inner to have size 2");
            assertEquals(2, secondInner.asList().size(), "Expected second innter to have size 2");

            assertEquals(1, ((IntVal) firstInner.asList().get(0)).getValue(), "Expected 1");
            assertEquals(2, ((IntVal) firstInner.asList().get(1)).getValue(), "Expected 2");

            assertEquals(3, ((IntVal) secondInner.asList().get(0)).getValue(), "Expected 3");
            assertEquals(4, ((IntVal) secondInner.asList().get(1)).getValue(), "Expected 4");
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
            store.bind(location, listVal);

            // Acces with index 1
            ExprNode indexExpr = new ExprIntNode("1", 0, 0);
            ExprListAccessNode listAccessNode = new ExprListAccessNode(varName, List.of(indexExpr), 0, 0);

            var resultListAccess = ExprInterpreter.evalExpr(envV, envF, envE, location, listAccessNode, store, imgStore);
            assertInstanceOf(IntVal.class, resultListAccess.getValue0(), "Expected result to be IntVal");
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
            store.bind(location, listVal);

            // Invalid access: index 5 (out of bounds)
            ExprNode invalidIndexExpr = new ExprIntNode("5", 0, 0);
            ExprListAccessNode invalidAccess = new ExprListAccessNode(varName, List.of(invalidIndexExpr), 0, 0);

            Exception exception = assertThrows(RuntimeException.class, () -> ExprInterpreter.evalExpr(envV, envF, envE, location, invalidAccess, store, imgStore));
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
            updatedStore.bind(location, outerList);

            // Access matrix[1][0], which should be 3
            ExprNode outerIndex = new ExprIntNode("1", 0, 0);  // second row
            ExprNode innerIndex = new ExprIntNode("0", 0, 0);  // first element
            ExprListAccessNode accessNode = new ExprListAccessNode(varName, List.of(outerIndex, innerIndex), 0, 0);

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, accessNode, updatedStore, imgStore);


            assertInstanceOf(IntVal.class, result.getValue0(), "Expected IntVal from matrix[1][0]");
            assertEquals(3, result.getValue0().asInt(), "Expected value 3 from matrix[1][0]");
        }
    }

    @Test
    public void exprTextNodeTest(){
        // Arrange
        String textContent = "Hello";
        String input = String.format("text \"%s\"", textContent);
        ExprNode expr = ASTGenerator.parseDeclExpr(input);

        // Act
        Val result = ExprInterpreter.evalExpr(envV, envF, envE, location, expr, store, imgStore).getValue0();

        // Assert
        assertInstanceOf(ShapeVal.class, result);
        assertInstanceOf(ShapeText.class, result.asShape().getFirst());
        assertEquals(textContent, ((ShapeText)result.asShape().getFirst()).getTextContent());
    }


   //simple forslag til line, curve, place, scale, og rotate

    //to make up for us using floating points in our calculations
    private static final double delta = 1e-6;
    //line
    @Nested
    class ExprLineNodeTests {

        @Test
        void line() {
            // line from (0,0) to (2,3)
            ExprNode line = new ExprLineNode(
                    List.of(
                            new ExprDoubleNode("0", 0, 0),
                            new ExprDoubleNode("0", 0, 0),
                            new ExprDoubleNode("2", 0, 0),
                            new ExprDoubleNode("3", 0, 0)
                    ),
                    0, 0
            );

            var result = ExprInterpreter.evalExpr(envV, envF, envE, location, line, store, imgStore);
            List<Shape> shapes = result.getValue0().asShape();

            assertEquals(1, shapes.size(), "ExprLineNode should produce exactly one ShapeLine");
            ShapeLine sl = (ShapeLine) shapes.get(0);

            List<Point> pts = sl.getPoints();
            assertEquals(2, pts.size(), "ShapeLine.getPoints() must return exactly two points");

            Point p0 = pts.get(0);
            Point p1 = pts.get(1);
            assertEquals(0.0, p0.getX(), delta);
            assertEquals(0.0, p0.getY(), delta);
            assertEquals(2.0, p1.getX(), delta);
            assertEquals(3.0, p1.getY(), delta);
        }

        //curve
        @Nested
        class ExprCurveNodeTests {

            @Test
            void simpleCurve() {
                // cubic Bézier from (0,0) via control (1,2) to (3,4)
                ExprNode curve = new ExprCurveNode(
                        List.of(
                                new ExprDoubleNode("0", 0, 0),
                                new ExprDoubleNode("0", 0, 0),
                                new ExprDoubleNode("1", 0, 0),
                                new ExprDoubleNode("2", 0, 0),
                                new ExprDoubleNode("3", 0, 0),
                                new ExprDoubleNode("4", 0, 0)
                        ),
                        0, 0
                );

                var result = ExprInterpreter.evalExpr(envV, envF, envE, location, curve, store, imgStore);
                ShapeCurve sc = (ShapeCurve) result.getValue0().asShape().get(0);
                List<Point> pts = sc.getPoints();

                assertEquals(3, pts.size(), "ShapeCurve.getPoints() must return three points");
                assertEquals(0.0, pts.get(0).getX(), delta);
                assertEquals(0.0, pts.get(0).getY(), delta);
                assertEquals(1.0, pts.get(1).getX(), delta);
                assertEquals(2.0, pts.get(1).getY(), delta);
                assertEquals(3.0, pts.get(2).getX(), delta);
                assertEquals(4.0, pts.get(2).getY(), delta);
            }
        }

        //place
        @Nested
        class ExprPlaceNodeTests {

            @Test
            void placeLineAtNewCenter() {
                // original line (0,0)->(2,2) with center (1,1)
                ExprNode line = new ExprLineNode(
                        List.of(
                                new ExprDoubleNode("0", 0, 0),
                                new ExprDoubleNode("0", 0, 0),
                                new ExprDoubleNode("2", 0, 0),
                                new ExprDoubleNode("2", 0, 0)
                        ),
                        0, 0
                );
                // place its center at (5,5)
                ExprNode placed = new ExprPlaceNode(
                        line,
                        new ExprDoubleNode("5", 0, 0),
                        new ExprDoubleNode("5", 0, 0),
                        0, 0
                );

                var result = ExprInterpreter.evalExpr(envV, envF, envE, location, placed, store, imgStore);
                ShapeLine sl = (ShapeLine) result.getValue0().asShape().get(0);
                List<Point> pts = sl.getPoints();

                // original center was (1,1) → shift by (+4,+4)
                assertEquals(4.0, pts.get(0).getX(), delta);
                assertEquals(4.0, pts.get(0).getY(), delta);
                assertEquals(6.0, pts.get(1).getX(), delta);
                assertEquals(6.0, pts.get(1).getY(), delta);
            }
        }

        //rotate
        @Nested
        class ExprRotateNodeTests {

            @Test
            void rotateLine90DegAboutOrigin() {
                // line (2,0)->(3,0)
                ExprNode line = new ExprLineNode(
                        List.of(
                                new ExprDoubleNode("2", 0, 0),
                                new ExprDoubleNode("0", 0, 0),
                                new ExprDoubleNode("3", 0, 0),
                                new ExprDoubleNode("0", 0, 0)
                        ),
                        0, 0
                );
                // rotate about (0,0) by 90°
                ExprNode rotated = new ExprRotateNode(
                        line,
                        new ExprDoubleNode("0", 0, 0),
                        new ExprDoubleNode("0", 0, 0),
                        new ExprDoubleNode("90", 0, 0),
                        0, 0
                );

                var result = ExprInterpreter.evalExpr(envV, envF, envE, location, rotated, store, imgStore);
                ShapeLine sl = (ShapeLine) result.getValue0().asShape().get(0);
                List<Point> pts = sl.getPoints();

                // rotation: (2,0)->(0,2), (3,0)->(0,3)
                assertEquals(0.0, pts.get(0).getX(), delta);
                assertEquals(2.0, pts.get(0).getY(), delta);
                assertEquals(0.0, pts.get(1).getX(), delta);
                assertEquals(3.0, pts.get(1).getY(), delta);
            }
        }

        //scale
        @Nested
        class ExprScaleNodeTests {

            @Test
            void scaleLineBy2x2AboutCenter() {
                // line (0,0)->(2,0), center at (1,0)
                ExprNode line = new ExprLineNode(
                        List.of(
                                new ExprDoubleNode("0", 0, 0),
                                new ExprDoubleNode("0", 0, 0),
                                new ExprDoubleNode("2", 0, 0),
                                new ExprDoubleNode("0", 0, 0)
                        ),
                        0, 0
                );
                // scale by (2,2) around its own center
                ExprNode scaled = new ExprScaleNode(
                        line,
                        new ExprDoubleNode("2", 0, 0),
                        new ExprDoubleNode("2", 0, 0),
                        0, 0
                );

                var result = ExprInterpreter.evalExpr(envV, envF, envE, location, scaled, store, imgStore);
                ShapeLine sl = (ShapeLine) result.getValue0().asShape().get(0);
                List<Point> pts = sl.getPoints();

                assertEquals(-1.0, pts.get(0).getX(), delta);
                assertEquals(0.0, pts.get(0).getY(), delta);
                assertEquals(3.0, pts.get(1).getX(), delta);
                assertEquals(0.0, pts.get(1).getY(), delta);
            }
        }
    }

}