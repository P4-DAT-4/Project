package afs.semantic_analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import afs.nodes.event.EventDeclarationNode;
import afs.nodes.event.EventNode;
import afs.nodes.prog.ProgNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import afs.semantic_analysis.exceptions.TypeCheckException;
import afs.semantic_analysis.types.*;
import afs.nodes.def.*;
import afs.nodes.expr.*;
import afs.nodes.stmt.*;
import afs.nodes.type.*;
import setup.ASTGenerator;

import static org.junit.jupiter.api.Assertions.*;


// "./gradlew test" to run the tests

public class TypeCheckerTest extends TypeChecker {

    private TypeChecker typeChecker;
    private Environment env;
    @BeforeEach
    public void setUp() {
        typeChecker = new TypeChecker();
        env = new Environment();
    }
    @Nested
    class DeclarationType {
        @Test
        public void DeclarationNode() {
            // Int
            ExprNode intExpr = new ExprIntNode("1", 1, 1);
            String identifier = "x";
            StmtNode declarationStmt = new StmtDeclarationNode(new TypeIntNode(1, 1), identifier, intExpr, new StmtAssignmentNode(identifier, intExpr, 1, 2), 1, 1);
            AFSType intType = typeChecker.processStmtNode(env, declarationStmt);
            assertEquals(intType, SimpleType.INT);

            // Double
            ExprNode doubleExpr = new ExprDoubleNode("1.0", 1, 1);
            String doubleIdentifier = "y";
            StmtNode doubleDeclarationStmt = new StmtDeclarationNode(new TypeDoubleNode(1, 1), doubleIdentifier, doubleExpr, new StmtAssignmentNode(doubleIdentifier, doubleExpr, 1, 2), 1, 1);
            AFSType doubleType = typeChecker.processStmtNode(env, doubleDeclarationStmt);
            assertEquals(doubleType, SimpleType.DOUBLE);

            // Bool
            ExprNode boolExpr = new ExprBoolNode("true", 1, 1);
            String boolIdentifier = "a";
            StmtNode boolDeclarationStmt = new StmtDeclarationNode(new TypeBoolNode(1, 1), boolIdentifier, boolExpr, new StmtAssignmentNode(boolIdentifier, boolExpr, 1, 2), 1, 1);
            AFSType boolType = typeChecker.processStmtNode(env, boolDeclarationStmt);
            assertEquals(boolType, SimpleType.BOOL);

            // String
            ExprNode stringExpr = new ExprStringNode("Hello", 1, 1);
            String stringIdentifier = "z";
            StmtNode stringDeclarationStmt = new StmtDeclarationNode(new TypeStringNode(1, 1), stringIdentifier, stringExpr, new StmtAssignmentNode(stringIdentifier, stringExpr, 1, 2), 1, 1);
            AFSType stringType = typeChecker.processStmtNode(env, stringDeclarationStmt);
            assertEquals(stringType, SimpleType.STRING);

            // List
            ExprNode listExpr = new ExprListDeclaration(List.of(new ExprIntNode("1", 1, 1), new ExprIntNode("2", 1, 2)), 1, 1);
            String listIdentifier = "list";
            StmtNode listDeclarationStmt = new StmtDeclarationNode(new TypeListNode(new TypeIntNode(1, 1), 1, 1), listIdentifier, listExpr, new StmtAssignmentNode(listIdentifier, listExpr, 1, 2), 1, 1);
            AFSType listType = typeChecker.processStmtNode(env, listDeclarationStmt);
            assertEquals(listType, new ListType(SimpleType.INT));

            // List of lists
            ExprNode listExpr2 = new ExprListDeclaration(List.of(new ExprListDeclaration(List.of(new ExprIntNode("1", 1, 1), new ExprIntNode("2", 1, 2)), 1, 1)), 1, 1);
            String listIdentifier2 = "list2";
            StmtNode listDeclarationStmt2 = new StmtDeclarationNode(new TypeListNode(new TypeListNode(new TypeIntNode(1, 1), 1, 1), 1, 1), listIdentifier2, listExpr2, new StmtAssignmentNode(listIdentifier2, listExpr2, 1, 2), 1, 1);
            AFSType listType2 = typeChecker.processStmtNode(env, listDeclarationStmt2);
            assertEquals(listType2, new ListType(new ListType(SimpleType.INT)));

            // Invalid: Type mismatch
            String invalidIdentifier = "invalidVar";
            ExprNode invalidExpr = new ExprStringNode("Hello", 1, 1);
            StmtNode invalidDeclarationStmt = new StmtDeclarationNode(new TypeIntNode(1, 1), invalidIdentifier, invalidExpr, new StmtAssignmentNode(invalidIdentifier, invalidExpr, 1, 2), 1, 1);
            TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
               typeChecker.processStmtNode(env, invalidDeclarationStmt);
            });
            assertEquals("Type mismatch: expected 'INT', but found 'STRING'", exception.getMessage());


            env.declare("x", SimpleType.INT);

            // Invalid: Already declared
            String alreadyDeclaredIdentifier = "x";
            ExprNode alreadyDeclaredExpr = new ExprIntNode("1", 1, 1);
            var globalDeclaration = new DefDeclarationNode(new TypeIntNode(-1, -1),
                    alreadyDeclaredIdentifier, alreadyDeclaredExpr, null, -1, -1);
            TypeCheckException alreadyDeclaredException = assertThrows(TypeCheckException.class, () -> {
                typeChecker.processDefNode(env, globalDeclaration);
            });
            assertEquals("Global variable 'x' already declared", alreadyDeclaredException.getMessage());
        }

        @Test
        public void FunctionNode() { // TODO

            StmtNode stmt = new StmtAssignmentNode("x", new ExprIntNode("1", 1, 1), 1, 2);

            env.declare("z", new FunctionType(SimpleType.INT));
            env.declare("a", SimpleType.INT);

            DefNode def = new DefDeclarationNode(new TypeIntNode(1, 1), "y", new ExprIntNode("1", 1, 1), new DefVisualizeNode("z", new ArrayList<>(), new EventDeclarationNode("a", "z", new ArrayList<>(), 1, 1), 1, 1),1, 1);

            DefFunctionNode functionNode = new DefFunctionNode(new TypeIntNode(1, 1),"fn", List.of(new Param(new TypeIntNode(1, 1), "x", 1, 1), new Param(new TypeDoubleNode(1, 1), "y", 1, 2)), stmt, def, 1, 1);
            typeChecker.processDefNode(env, functionNode);
            assertEquals(functionNode.getAFSType(), SimpleType.INT);

        }
        @Test
        public void VisualizeNode() {
            // Declare global variable x
            String var = "x";
            env.declare(var, SimpleType.INT);

            // Function setup
            String identifier = "fn";

            Param param1 = new Param(new TypeIntNode(1, 1), "x", 1, 1);
            param1.setType(SimpleType.INT);
            Param param2 = new Param(new TypeDoubleNode(1, 1), "y", 1, 2);
            param2.setType(SimpleType.DOUBLE);

            FunctionType functionType = new FunctionType(SimpleType.INT);
            functionType.addParamType(param1.getAFSType());
            functionType.addParamType(param2.getAFSType());

            System.out.printf("Param type: %s%n", param1.getAFSType());
            System.out.printf("Param type: %s%n", param2.getAFSType());

            env.declare(identifier, functionType);
            List<ExprNode> args = List.of(new ExprIntNode("1", 1, 1), new ExprDoubleNode("2.0", 1, 1));

            // Event Setup
            List<ExprNode> eventArgs = List.of(new ExprIntNode("1", 1, 1), new ExprDoubleNode("2.0", 1, 1));
            EventNode event = new EventDeclarationNode(var, identifier, eventArgs, 1, 1);

            // valid visualizeNode
            DefVisualizeNode visualizeNode = new DefVisualizeNode(identifier, args, event, 1, 1);
            typeChecker.processDefNode(env, visualizeNode);

            // Invalid: No event

            // Invalid: wrong number of arguments

        }
    }
    @Nested
    class EventType {
        @Test
        public void CompositionNode() { // TODO

        }

        @Test
        public void DeclarationNode() { // TODO

        }
    }

    @Nested
    class StatementType {
        @Test
        public void IfNode() {

            // Valid BOOL
            ExprNode condition = new ExprBoolNode("true", 1, 1);

            env.declare("x", SimpleType.INT);
            env.declare("y", SimpleType.INT);

            StmtNode leftStmt = new StmtAssignmentNode("x", new ExprIntNode("1", 1, 1), 1, 2);
            StmtNode rightStmt = new StmtAssignmentNode("y", new ExprIntNode("2", 1, 1), 1, 2);

            StmtNode ifStmt = new StmtIfNode(condition, leftStmt, rightStmt, 1, 1);
            AFSType returnType = typeChecker.processStmtNode(env, ifStmt);
            assertEquals(returnType, null);

            // Invalid type
            ExprNode invalidCondition = new ExprIntNode("1", 1, 1);
            StmtNode invalidIfStmt = new StmtIfNode(invalidCondition, leftStmt, rightStmt, 1, 1);
            TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
                typeChecker.processStmtNode(env, invalidIfStmt);
            });
            assertEquals("Invalid type 'INT': expected 'BOOL'", exception.getMessage());
        }
        @Test
        public void WhileNode() {
            // Valid bool
            ExprNode expr = new ExprBoolNode("true", 1, 1);
            env.declare("x", SimpleType.INT);
            StmtNode stmt = new StmtAssignmentNode("x", new ExprIntNode("1", 1, 1), 1, 2);
            StmtNode whileStmt = new StmtWhileNode(expr, stmt, 1, 1);
            AFSType returnType = typeChecker.processStmtNode(env, whileStmt);
            assertEquals(returnType, null);

            // Invalid type
            ExprNode invalidExpr = new ExprIntNode("1", 1, 1);
            StmtNode invalidWhileStmt = new StmtWhileNode(invalidExpr, stmt, 1, 1);
            TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
                typeChecker.processStmtNode(env, invalidWhileStmt);
            });
            assertEquals("Invalid type 'INT': expected 'BOOL'", exception.getMessage());
        }
        @Test
        public void AssignmentNode() {
            // INT
            env.declare("a", SimpleType.INT);
            String intIdent = "a";
            ExprNode intExpr = new ExprIntNode("1", 1, 1);
            StmtNode intAss = new StmtAssignmentNode(intIdent, intExpr, 1, 2);
            AFSType intReturnType = typeChecker.processStmtNode(env, intAss);
            assertEquals(intReturnType, SimpleType.INT);

            // DOUBLE
            env.declare("b", SimpleType.DOUBLE);
            String doubleIdent = "b";
            ExprNode doubleExpr = new ExprDoubleNode("1", 1, 1);
            StmtNode doubleAss = new StmtAssignmentNode(doubleIdent, doubleExpr, 1, 2);
            AFSType DoubleReturnType = typeChecker.processStmtNode(env, doubleAss);
            assertEquals(DoubleReturnType, SimpleType.DOUBLE);

            // STRING
            env.declare("c", SimpleType.STRING);
            String stringIdent = "c";
            ExprNode stringExpr = new ExprStringNode("1", 1, 1);
            StmtNode stringAss = new StmtAssignmentNode(stringIdent, stringExpr, 1, 2);
            AFSType stringReturnType = typeChecker.processStmtNode(env, stringAss);
            assertEquals(stringReturnType, SimpleType.STRING);

            // BOOL
            env.declare("d", SimpleType.BOOL);
            String boolIdent = "d";
            ExprNode boolExpr = new ExprBoolNode("1", 1, 1);
            StmtNode boolAss = new StmtAssignmentNode(boolIdent, boolExpr, 1, 2);
            AFSType boolReturnType = typeChecker.processStmtNode(env, boolAss);
            assertEquals(boolReturnType, SimpleType.BOOL);

            // function (T x = function);

            // Invalid: type mismatch
            env.declare("e", SimpleType.INT);
            String invalidIdent = "e";
            ExprNode invalidExpr = new ExprStringNode("Hello", 1, 1);
            StmtNode invalidAss = new StmtAssignmentNode(invalidIdent, invalidExpr, 1, 2);
            TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
                typeChecker.processStmtNode(env, invalidAss);
            });
            assertEquals("Type mismatch: expected 'INT', but found 'STRING'", exception.getMessage());
        }
        @Test
        public void DeclarationNode() {
            // Int
            ExprNode intExpr = new ExprIntNode("1", 1, 1);
            String identifier = "x";
            StmtNode declarationStmt = new StmtDeclarationNode(new TypeIntNode(1, 1), identifier, intExpr, new StmtAssignmentNode(identifier, intExpr, 1, 2), 1, 1);
            AFSType intType = typeChecker.processStmtNode(env, declarationStmt);
            assertEquals(intType, SimpleType.INT);

            // Double
            ExprNode doubleExpr = new ExprDoubleNode("1.0", 1, 1);
            String doubleIdentifier = "y";
            StmtNode doubleDeclarationStmt = new StmtDeclarationNode(new TypeDoubleNode(1, 1), doubleIdentifier, doubleExpr, new StmtAssignmentNode(doubleIdentifier, doubleExpr, 1, 2), 1, 1);
            AFSType doubleType = typeChecker.processStmtNode(env, doubleDeclarationStmt);
            assertEquals(doubleType, SimpleType.DOUBLE);

            // Bool
            ExprNode boolExpr = new ExprBoolNode("true", 1, 1);
            String boolIdentifier = "a";
            StmtNode boolDeclarationStmt = new StmtDeclarationNode(new TypeBoolNode(1, 1), boolIdentifier, boolExpr, new StmtAssignmentNode(boolIdentifier, boolExpr, 1, 2), 1, 1);
            AFSType boolType = typeChecker.processStmtNode(env, boolDeclarationStmt);
            assertEquals(boolType, SimpleType.BOOL);

            // String
            ExprNode stringExpr = new ExprStringNode("Hello", 1, 1);
            String stringIdentifier = "z";
            StmtNode stringDeclarationStmt = new StmtDeclarationNode(new TypeStringNode(1, 1), stringIdentifier, stringExpr, new StmtAssignmentNode(stringIdentifier, stringExpr, 1, 2), 1, 1);
            AFSType stringType = typeChecker.processStmtNode(env, stringDeclarationStmt);
            assertEquals(stringType, SimpleType.STRING);

            // Invalid: type mismatch
            String invalidIdentifier = "invalidVar";
            ExprNode invalidExpr = new ExprStringNode("Hello", 1, 1);
            StmtNode invalidDeclarationStmt = new StmtDeclarationNode(new TypeIntNode(1, 1), invalidIdentifier, invalidExpr, new StmtAssignmentNode(invalidIdentifier, invalidExpr, 1, 2), 1, 1);
            TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
                typeChecker.processStmtNode(env, invalidDeclarationStmt);
            });
            assertEquals("Type mismatch: expected 'INT', but found 'STRING'", exception.getMessage());
        }
        @Test
        public void FunctionCallNode() {
            // setup
            String identifier = "fn";

            Param param1 = new Param(new TypeIntNode(1, 1), "x", 1, 1);
            Param param2 = new Param(new TypeDoubleNode(1, 1), "y", 1, 2);

            param1.setType(SimpleType.INT);
            param2.setType(SimpleType.DOUBLE);

            FunctionType functionType = new FunctionType(SimpleType.INT);
            functionType.addParamType(param1.getAFSType());
            functionType.addParamType(param2.getAFSType());

            env.declare(identifier, functionType);

            // Valid function call
            List<ExprNode> args = List.of(new ExprIntNode("1", 1, 1), new ExprDoubleNode("2.0", 1, 1));
            StmtNode functionCallStmt = new StmtFunctionCallNode(identifier, args, 1, 1);
            AFSType returnType = typeChecker.processStmtNode(env, functionCallStmt);
            assertNull(returnType);

            // Invalid: wrong number of arguments
            List<ExprNode> invalidArgs = List.of(new ExprIntNode("1", 1, 1));
            StmtNode invalidFunctionCallStmt = new StmtFunctionCallNode(identifier, invalidArgs, 1, 1);
            TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
                typeChecker.processStmtNode(env, invalidFunctionCallStmt);
            });
            assertEquals("Function 'fn' expected 2 arguments, but got 1", exception.getMessage());


            // Invalid: wrong argument type
            List<ExprNode> invalidArgs2 = List.of(new ExprIntNode("1", 1, 1), new ExprStringNode("Hello", 1, 1));
            StmtNode invalidFunctionCallStmt2 = new StmtFunctionCallNode(identifier, invalidArgs2, 1, 1);
            TypeCheckException exception2 = assertThrows(TypeCheckException.class, () -> {
                typeChecker.processStmtNode(env, invalidFunctionCallStmt2);
            });
            assertEquals("Type mismatch: expected 'DOUBLE', but found 'STRING'", exception2.getMessage());

            // Invalid: Type mismatch
            List<ExprNode> invalidArgs3 = List.of(new ExprIntNode("1", 1, 1), new ExprStringNode("Hello", 1, 1));
            StmtNode invalidFunctionCallStmt3 = new StmtFunctionCallNode(identifier, invalidArgs3, 1, 1);
            TypeCheckException exception3 = assertThrows(TypeCheckException.class, () -> {
                typeChecker.processStmtNode(env, invalidFunctionCallStmt3);
            });
            assertEquals("Type mismatch: expected 'DOUBLE', but found 'STRING'", exception3.getMessage());
        }
        @Test
        public void ReturnNode() {
            // Int
            env.declare("fn", new FunctionType(SimpleType.INT));
            env.declare("return", SimpleType.INT);
            Environment fnEnv = new Environment(env);
            ExprNode expr = new ExprIntNode("1", 1, 1);
            StmtNode returnStmt = new StmtReturnNode(expr, 1, 1);
            AFSType returnType = typeChecker.processStmtNode(fnEnv, returnStmt);
            assertEquals(returnType, SimpleType.INT);

            // Double
            env.declare("fn1", new FunctionType(SimpleType.DOUBLE));
            env.declare("return", SimpleType.DOUBLE);
            Environment fnEnv1 = new Environment(env);
            ExprNode doubleExpr = new ExprDoubleNode("1.0", 1, 1);
            StmtNode doubleReturnStmt = new StmtReturnNode(doubleExpr, 1, 1);
            AFSType doubleReturnType = typeChecker.processStmtNode(fnEnv1, doubleReturnStmt);
            assertEquals(doubleReturnType, SimpleType.DOUBLE);

            // String
            env.declare("fn2", new FunctionType(SimpleType.STRING));
            env.declare("return", SimpleType.STRING);
            Environment fnEnv2 = new Environment(env);
            ExprNode stringExpr = new ExprStringNode("Hello", 1, 1);
            StmtNode stringReturnStmt = new StmtReturnNode(stringExpr, 1, 1);
            AFSType stringReturnType = typeChecker.processStmtNode(fnEnv2, stringReturnStmt);
            assertEquals(stringReturnType, SimpleType.STRING);

            // Bool
            env.declare("fn3", new FunctionType(SimpleType.BOOL));
            env.declare("return", SimpleType.BOOL);
            Environment fnEnv3 = new Environment(env);
            ExprNode boolExpr = new ExprBoolNode("true", 1, 1);
            StmtNode boolReturnStmt = new StmtReturnNode(boolExpr, 1, 1);
            AFSType boolReturnType = typeChecker.processStmtNode(fnEnv3, boolReturnStmt);
            assertEquals(boolReturnType, SimpleType.BOOL);

            // List
            env.declare("fn4", new FunctionType(new ListType(SimpleType.INT)));
            env.declare("return", new ListType(SimpleType.INT));
            Environment fnEnv4 = new Environment(env);
            ExprNode listExpr = new ExprListDeclaration(List.of(new ExprIntNode("1", 1, 1), new ExprIntNode("2", 1, 2)), 1, 1);
            StmtNode listReturnStmt = new StmtReturnNode(listExpr, 1, 1);
            AFSType listReturnType = typeChecker.processStmtNode(fnEnv4, listReturnStmt);
            assertEquals(listReturnType, new ListType(SimpleType.INT));

            // Invalid: type mismatch
            env.declare("fn5", new FunctionType(SimpleType.INT));
            env.declare("return", SimpleType.INT);
            Environment fnEnv5 = new Environment(env);
            ExprNode invalidExpr = new ExprStringNode("Hello", 1, 1);
            StmtNode invalidReturnStmt = new StmtReturnNode(invalidExpr, 1, 1);
            TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
                typeChecker.processStmtNode(fnEnv5, invalidReturnStmt);
            });
            assertEquals("Type mismatch: expected 'INT', but found 'STRING'", exception.getMessage());
        }
        @Test
        public void ListAssignmentNode() {
            // List declaration
            env.declare("List", new ListType(SimpleType.INT));
            env.declare("List2", new ListType(new ListType(SimpleType.DOUBLE)));

            // single dimension
            String identifier = "List";
            List<ExprNode> listExpr = List.of(
                    new ExprIntNode("1", 1, 1)
            );
            ExprNode expr = new ExprIntNode("2", 1, 1);
            StmtNode listAssignmentStmt = new StmtListAssignmentNode(identifier, listExpr, expr, 1, 1);
            AFSType listType = typeChecker.processStmtNode(env, listAssignmentStmt);
            assertEquals(listType, SimpleType.INT);

            // multi dimension int
            String identifier2 = "List2";
            List<ExprNode> listExpr2 = List.of(
                    new ExprIntNode("1", 1, 1),
                    new ExprIntNode("2", 1, 2)
            );
            ExprNode expr2 = new ExprDoubleNode("3.0", 1, 1);
            StmtNode listAssignmentStmt2 = new StmtListAssignmentNode(identifier2, listExpr2, expr2, 1, 1);
            AFSType listType2 = typeChecker.processStmtNode(env, listAssignmentStmt2);
            assertEquals(listType2, SimpleType.DOUBLE);

            // Invalid type mismatch
//            String identifier3 = "List";
//            List<ExprNode> listExpr3 = List.of(
//                    new ExprIntNode("1", 1, 1),
//                    new ExprIntNode("2", 1, 2)
//            );
//            ExprNode expr3 = new ExprStringNode("Hello", 1, 1);
//            StmtNode listAssignmentStmt3 = new StmtListAssignmentNode(identifier3, listExpr3, expr3, 1, 1);
//            TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
//                typeChecker.processStmtNode(env, listAssignmentStmt3);
//            });
//            assertEquals("Cannot access index on 'List' that is not a list.", exception.getMessage());
        }
        @Test
        public void skipNode() {
            // Valid
            StmtNode skipStmt = new StmtSkipNode();
            AFSType returnType = typeChecker.processStmtNode(env, skipStmt);
            assertNull(returnType);

            // Invalid
            // No invalid cases for skip node
        }
    }
    @Nested
    class ExpressionType {
        @Nested
        class BinOpExpr {
            @Test
            public void AddSubMulDiv() {
                // Int
                ExprNode leftExpr = new ExprIntNode("1", 1, 1);
                ExprNode rightExpr = new ExprIntNode("2", 1, 2);
                ExprNode addExpr = new ExprBinopNode(leftExpr, BinOp.ADD, rightExpr, 1, 3);
                AFSType resultType = typeChecker.processExprNode(env, addExpr);
                assertEquals(resultType, SimpleType.INT);

                // Double
                ExprNode leftExpr2 = new ExprDoubleNode("1.0", 1, 1);
                ExprNode rightExpr2 = new ExprDoubleNode("2.0", 1, 2);
                ExprNode addExpr2 = new ExprBinopNode(leftExpr2, BinOp.ADD, rightExpr2, 1, 3);
                AFSType resultType2 = typeChecker.processExprNode(env, addExpr2);
                assertEquals(resultType2, SimpleType.DOUBLE);

                // String
                ExprNode leftExpr3 = new ExprStringNode("AFS", 1, 1);
                ExprNode rightExpr3 = new ExprStringNode("Best", 1, 2);
                ExprNode addExpr3 = new ExprBinopNode(leftExpr3, BinOp.ADD, rightExpr3, 1, 3);
                TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
                    typeChecker.processExprNode(env, addExpr3);
                });
                assertEquals("Invalid type 'STRING': expected 'INT' or 'DOUBLE'", exception.getMessage());

                // invalid: type mismatch
                ExprNode leftExpr4 = new ExprIntNode("1", 1, 1);
                ExprNode rightExpr4 = new ExprStringNode("N1", 1, 2);
                ExprNode addExpr4 = new ExprBinopNode(leftExpr4, BinOp.ADD, rightExpr4, 1, 3);
                TypeCheckException exception2 = assertThrows(TypeCheckException.class, () -> {
                    typeChecker.processExprNode(env, addExpr4);
                });
                assertEquals("Type mismatch: expected 'STRING', but found 'INT'", exception2.getMessage());
            }

            @Test
            public void CONCAT() {
                // String
                ExprNode leftExpr = new ExprStringNode("Top", 1, 1);
                ExprNode rightExpr = new ExprStringNode("Tier", 1, 2);
                ExprNode concatExpr = new ExprBinopNode(leftExpr, BinOp.CONCAT, rightExpr, 1, 3);
                AFSType resultType = typeChecker.processExprNode(env, concatExpr);
                assertEquals(resultType, SimpleType.STRING);

                // Shape TODO

                // List
                ExprNode leftExpr2 = new ExprListDeclaration(List.of(
                        new ExprIntNode("1", 1, 1),
                        new ExprIntNode("2", 1, 2)),
                        1, 1
                );
                ExprNode rightExpr2 = new ExprListDeclaration(List.of(
                        new ExprIntNode("3", 1, 3),
                        new ExprIntNode("4", 1, 4)),
                        1, 1
                );
                ExprNode concatExpr2 = new ExprBinopNode(leftExpr2, BinOp.CONCAT, rightExpr2, 1, 3);
                AFSType resultType2 = typeChecker.processExprNode(env, concatExpr2);
                assertEquals(resultType2, new ListType(SimpleType.INT));

                // Invalid: wrong type
                ExprNode leftExpr3 = new ExprIntNode("1", 1, 1);
                ExprNode rightExpr3 = new ExprIntNode("2", 1, 2);
                ExprNode concatExpr3 = new ExprBinopNode(leftExpr3, BinOp.CONCAT, rightExpr3, 1, 3);
                TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
                    typeChecker.processExprNode(env, concatExpr3);
                });
                assertEquals("Invalid type 'INT': Expected string or shape or list", exception.getMessage());
            }

            @Test
            public void Lt() {
                // Int
                ExprNode leftIntExpr = new ExprIntNode("1", 1, 1);
                ExprNode rightIntExpr = new ExprIntNode("2", 1, 2);
                ExprNode ltExpr = new ExprBinopNode(leftIntExpr, BinOp.LT, rightIntExpr, 1, 3);
                AFSType resultType = typeChecker.processExprNode(env, ltExpr);
                assertEquals(resultType, SimpleType.BOOL);

                // Double
                ExprNode leftDoubleExpr = new ExprDoubleNode("1.0", 1, 1);
                ExprNode rightDoubleExpr = new ExprDoubleNode("2.0", 1, 2);
                ExprNode ltExpr2 = new ExprBinopNode(leftDoubleExpr, BinOp.LT, rightDoubleExpr, 1, 3);
                AFSType resultType2 = typeChecker.processExprNode(env, ltExpr2);
                assertEquals(resultType2, SimpleType.BOOL);

                // String
                ExprNode leftExpr3 = new ExprStringNode("World", 1, 1);
                ExprNode rightExpr3 = new ExprStringNode("Best", 1, 2);
                ExprNode ltExpr3 = new ExprBinopNode(leftExpr3, BinOp.LT, rightExpr3, 1, 3);
                TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
                    typeChecker.processExprNode(env, ltExpr3);
                });
                assertEquals("Invalid type 'STRING': expected 'INT' or 'DOUBLE'", exception.getMessage());

                // invalid: type mismatch
                ExprNode leftExpr4 = new ExprIntNode("1", 1, 1);
                ExprNode rightExpr4 = new ExprStringNode("Hero", 1, 2);
                ExprNode ltExpr4 = new ExprBinopNode(leftExpr4, BinOp.LT, rightExpr4, 1, 3);
                TypeCheckException exception2 = assertThrows(TypeCheckException.class, () -> {
                    typeChecker.processExprNode(env, ltExpr4);
                });
                assertEquals("Type mismatch: expected 'STRING', but found 'INT'", exception2.getMessage());
            }

            @Test
            public void Eq() {
                // Int
                ExprNode leftIntExpr = new ExprIntNode("1", 1,1);
                ExprNode rightIntExpr = new ExprIntNode("2", 1, 2);
                ExprNode eqExpr = new ExprBinopNode(leftIntExpr, BinOp.EQ, rightIntExpr, 1, 3);
                AFSType resultType = typeChecker.processExprNode(env, eqExpr);
                assertEquals(resultType, SimpleType.BOOL);

                // Double
                ExprNode leftDoubleExpr = new ExprDoubleNode("1.0", 1, 1);
                ExprNode rightDoubleExpr = new ExprDoubleNode("2.0", 1, 2);
                ExprNode ltExpr2 = new ExprBinopNode(leftDoubleExpr, BinOp.EQ, rightDoubleExpr, 1, 3);
                AFSType resultType2 = typeChecker.processExprNode(env, ltExpr2);
                assertEquals(resultType2, SimpleType.BOOL);

                // String
                ExprNode leftStringExpr = new ExprStringNode("Hello", 1, 1);
                ExprNode rightStringExpr = new ExprStringNode("World", 1, 2);
                ExprNode eqExpr2 = new ExprBinopNode(leftStringExpr, BinOp.EQ, rightStringExpr, 1, 3);
                AFSType resultType3 = typeChecker.processExprNode(env, eqExpr2);
                assertEquals(resultType3, SimpleType.BOOL);

                // Boolean
                ExprNode leftBoolExpr = new ExprBoolNode("true", 1, 1);
                ExprNode rightBoolExpr = new ExprBoolNode("false", 1, 2);
                ExprNode eqExpr3 = new ExprBinopNode(leftBoolExpr, BinOp.EQ, rightBoolExpr, 1, 3);
                AFSType resultType4 = typeChecker.processExprNode(env, eqExpr3);
                assertEquals(resultType4, SimpleType.BOOL);
            }

            @Test
            public void AND() {
                // Bool
                ExprNode leftExpr = new ExprBoolNode("1", 1, 1);
                ExprNode rightExpr = new ExprBoolNode("0", 1, 2);
                ExprNode andExpr = new ExprBinopNode(leftExpr, BinOp.AND, rightExpr, 1, 3);
                AFSType resultType = typeChecker.processExprNode(env, andExpr);
                assertEquals(resultType, SimpleType.BOOL);

                // Invalid: wrong type
                ExprNode leftExpr2 = new ExprIntNode("1", 1, 1);
                ExprNode rightExpr2 = new ExprIntNode("0", 1, 2);
                ExprNode andExpr2 = new ExprBinopNode(leftExpr2, BinOp.AND, rightExpr2, 1, 3);
                TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
                    typeChecker.processExprNode(env, andExpr2);
                });
                assertEquals("Invalid type 'INT': expected 'BOOL'", exception.getMessage());
            }
        }
        @Nested
        class UnOpExpr {
            @Test
            public void NOT() {
                // Bool
                ExprNode expr = new ExprBoolNode("1", 1, 1);
                ExprNode notExpr = new ExprUnopNode(expr, UnOp.NOT, 1, 2);
                AFSType resultType = typeChecker.processExprNode(env, notExpr);
                assertEquals(resultType, SimpleType.BOOL);

                // Invalid: wrong type
                ExprNode expr2 = new ExprIntNode("1", 1, 1);
                ExprNode notExpr2 = new ExprUnopNode(expr2, UnOp.NOT, 1, 2);
                TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
                    typeChecker.processExprNode(env, notExpr2);
                });
                assertEquals("Invalid type 'INT': expected 'BOOL'", exception.getMessage());
            }

            @Test
            public void NEG() {
                // Int
                ExprNode expr = new ExprIntNode("1", 1, 1);
                ExprNode negExpr = new ExprUnopNode(expr, UnOp.NEG, 1, 2);
                AFSType resultType = typeChecker.processExprNode(env, negExpr);
                assertEquals(resultType, SimpleType.INT);

                // Double
                ExprNode expr2 = new ExprDoubleNode("1.0", 1, 1);
                ExprNode negExpr2 = new ExprUnopNode(expr2, UnOp.NEG, 1, 2);
                AFSType resultType2 = typeChecker.processExprNode(env, negExpr2);
                assertEquals(resultType2, SimpleType.DOUBLE);

                // Invalid: wrong type
                ExprNode expr3 = new ExprStringNode("Best", 1, 1);
                ExprNode negExpr3 = new ExprUnopNode(expr3, UnOp.NEG, 1, 2);
                TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
                    typeChecker.processExprNode(env, negExpr3);
                });
                assertEquals("Invalid type 'STRING': expected 'INT' or 'DOUBLE'", exception.getMessage());
            }
        }
        @Test
        public void IdentifierNode() {
            // Int
            ExprNode identifier = new ExprIdentifierNode("x", 1, 1);
            env.declare("x", SimpleType.INT);
            AFSType returnType = typeChecker.processExprNode(env,identifier);
            assertEquals(returnType, SimpleType.INT);

            // Double
            ExprNode identifier2 = new ExprIdentifierNode("y", 1, 1);
            env.declare("y", SimpleType.DOUBLE);
            AFSType returnType2 = typeChecker.processExprNode(env, identifier2);
            assertEquals(returnType2, SimpleType.DOUBLE);

            // String
            ExprNode identifier3 = new ExprIdentifierNode("z", 1, 1);
            env.declare("z", SimpleType.STRING);
            AFSType returnType3 = typeChecker.processExprNode(env, identifier3);
            assertEquals(returnType3, SimpleType.STRING);

            // Bool
            ExprNode identifier4 = new ExprIdentifierNode("a", 1, 1);
            env.declare("a", SimpleType.BOOL);
            AFSType returnType4 = typeChecker.processExprNode(env, identifier4);
            assertEquals(returnType4, SimpleType.BOOL);

            // Invalid: undefined variable
            ExprNode identifier5 = new ExprIdentifierNode("undefinedVar", 1, 1);
            TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
                typeChecker.processExprNode(env, identifier5);
            });
            assertEquals("Variable 'undefinedVar' not found", exception.getMessage());
        }

        @Test
        public void CurveNode() {
            // Valid: six (n = 6)
            List<ExprNode> validList = List.of(new ExprDoubleNode("1.0", 1, 1), new ExprDoubleNode("2.0", 1, 2), new ExprDoubleNode("3.0", 1, 3), new ExprDoubleNode("4.0", 1, 4), new ExprDoubleNode("5.0", 1, 5), new ExprDoubleNode("6.0", 1, 6));
            ExprCurveNode validCurve = new ExprCurveNode(validList, 1, 1);
            AFSType validRes = typeChecker.processExprNode(env, validCurve);
            assertEquals(validRes, SimpleType.SHAPE);

            // Invalid: Under six  (n < 6)
            List<ExprNode> undersixList = List.of(new ExprDoubleNode("1.0", 1, 1), new ExprDoubleNode("2.0", 1, 2), new ExprDoubleNode("3.0", 1, 3));
            ExprCurveNode underSixCurve = new ExprCurveNode(undersixList, 1, 1);
            TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
                typeChecker.processExprNode(env, underSixCurve);
            });
            assertEquals("Curve must have at least 3 points", exception.getMessage());

            // Invalid: Wrong type
            List<ExprNode> wrongTypeList = List.of(new ExprIntNode("1", 1, 1), new ExprIntNode("2", 1, 2), new ExprIntNode("3", 1, 3), new ExprIntNode("4", 1, 4), new ExprIntNode("5", 1, 5), new ExprIntNode("6", 1, 6));
            ExprCurveNode wrongTypeCurve = new ExprCurveNode(wrongTypeList, 1, 1);
            TypeCheckException exception2 = assertThrows(TypeCheckException.class, () -> {
                typeChecker.processExprNode(env, wrongTypeCurve);
            });
            assertEquals("Invalid type 'INT': expected 'DOUBLE'", exception2.getMessage());
        }

        @Test
        public void FunctionCallNode() { // TODO

        }

        @Test
        public void LineNode() {
            // Valid: two points
            List<ExprNode> validList = List.of(
                    new ExprDoubleNode("1.0", 1, 1),
                    new ExprDoubleNode("2.0", 1, 2),
                    new ExprDoubleNode("2.0", 1, 2),
                    new ExprDoubleNode("2.0", 1, 2)
            );
            ExprLineNode validLine = new ExprLineNode(validList, 1, 1);
            AFSType validRes = typeChecker.processExprNode(env, validLine);
            assertEquals(validRes, SimpleType.SHAPE);

            // Invalid: less than two points
            List<ExprNode> invalidList = List.of(
                    new ExprDoubleNode("1.0", 1, 1),
                    new ExprDoubleNode("2.0", 1, 2)
            );
            ExprLineNode invalidLine = new ExprLineNode(invalidList, 1, 1);
            TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
                typeChecker.processExprNode(env, invalidLine);
            });
            assertEquals("Line must have at least 2 points", exception.getMessage());

            // Invalid: wrong type
            List<ExprNode> wrongTypeList = List.of(
                    new ExprIntNode("1", 1, 1),
                    new ExprIntNode("2", 1, 2),
                    new ExprIntNode("3", 1, 3),
                    new ExprIntNode("4", 1, 4)
            );
            ExprLineNode wrongTypeLine = new ExprLineNode(wrongTypeList, 1, 1);
            TypeCheckException exception2 = assertThrows(TypeCheckException.class, () -> {
                typeChecker.processExprNode(env, wrongTypeLine);
            });
            assertEquals("Invalid type 'INT': expected 'DOUBLE'", exception2.getMessage());
        }
        @Test
        public void ListDeclarationNode() {
            // INT
            List<ExprNode> intExprList = List.of(new ExprIntNode("1", 1, 1), new ExprIntNode("2", 1, 2), new ExprIntNode("3", 1, 3));
            ExprListDeclaration intList = new ExprListDeclaration(intExprList, 1, 1);
            AFSType intRes = typeChecker.processExprNode(env, intList);
            assertEquals(intRes, new ListType(SimpleType.INT));

            // DOUBLE
            List<ExprNode> doubleExprList = List.of(new ExprDoubleNode("1.0", 1, 1), new ExprDoubleNode("2.0", 1, 2), new ExprDoubleNode("3.0", 1, 3));
            ExprListDeclaration doubleList = new ExprListDeclaration(doubleExprList, 1, 1);
            AFSType doubleRes = typeChecker.processExprNode(env, doubleList);
            assertEquals(doubleRes, new ListType(SimpleType.DOUBLE));

            // STRING
            List<ExprNode> stringExprList = List.of(new ExprStringNode("Hello", 1, 1), new ExprStringNode("World", 1, 2), new ExprStringNode("!", 1, 3));
            ExprListDeclaration stringList = new ExprListDeclaration(stringExprList, 1, 1);
            AFSType stringRes = typeChecker.processExprNode(env, stringList);
            assertEquals(stringRes, new ListType(SimpleType.STRING));

            // BOOL
            List<ExprNode> boolExprList = List.of(new ExprBoolNode("true", 1, 1), new ExprBoolNode("false", 1, 2), new ExprBoolNode("true", 1, 3));
            ExprListDeclaration boolList = new ExprListDeclaration(boolExprList, 1, 1);
            AFSType boolRes = typeChecker.processExprNode(env, boolList);
            assertEquals(boolRes, new ListType(SimpleType.BOOL));

            // Stacked list
            List<ExprNode> stackedList = List.of(new ExprListDeclaration(List.of(new ExprIntNode("1", 1, 1), new ExprIntNode("2", 1, 2)), 1, 1), new ExprListDeclaration(List.of(new ExprIntNode("3", 1, 3), new ExprIntNode("4", 1, 4)), 1, 4));
            ExprListDeclaration stackedListExpr = new ExprListDeclaration(stackedList, 1, 1);
            AFSType stackedListRes = typeChecker.processExprNode(env, stackedListExpr);
            assertEquals(stackedListRes, new ListType(new ListType(SimpleType.INT)));

            // Invalid: type mismatch
            List<ExprNode> mixedExprList = List.of(new ExprIntNode("1", 1, 1), new ExprStringNode("Hello", 1, 2), new ExprIntNode("3", 1, 3));
            ExprListDeclaration mixedList = new ExprListDeclaration(mixedExprList, 1, 1);
            TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
                typeChecker.processExprNode(env, mixedList);
            });
            assertEquals("Type mismatch: expected 'INT', but found 'STRING'", exception.getMessage());
        }

        @Test
        public void PlaceNode() {} // TODO

        @Test
        public void TextNode() {
            // Valid: string
            ExprNode textExpr = new ExprStringNode("Hello", 1, 1);
            ExprTextNode textNode = new ExprTextNode(textExpr, 1, 1);
            AFSType textType = typeChecker.processExprNode(env, textNode);
            assertEquals(textType, SimpleType.SHAPE);

            // Invalid type
            ExprNode invalidTextExpr = new ExprIntNode("1", 1, 1);
            ExprTextNode invalidTextNode = new ExprTextNode(invalidTextExpr, 1, 1);
            TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
                typeChecker.processExprNode(env, invalidTextNode);
            });
            assertEquals("Invalid type 'INT': expected 'STRING'", exception.getMessage());
        }

        @Test
        public void ScaleNode() {} // TODO

        @Test
        public void RotateNode() { // TODO
        }

        @Test
        public void ListAccessNode() {
            // Declare list (single dimension)
            env.declare("List", new ListType(SimpleType.INT));
            // Declare list (multi dimension)
            env.declare("List2", new ListType(new ListType(SimpleType.INT)));

            String singleAcc = "List";
            String doubleAcc = "List2";

            // Valid: Single Array single Access
            List<ExprNode> singleArrSingleAcc = List.of(
                    new ExprIntNode("1", 1, 1)
            );
            ExprListAccessNode singlesingleacc = new ExprListAccessNode(singleAcc, singleArrSingleAcc, 1, 1);
            AFSType singlesingleType = typeChecker.processExprNode(env, singlesingleacc);
            assertEquals(singlesingleType, SimpleType.INT);

            // Valid: Single Array multi access
            List<ExprNode> singleArrMultiAcc = List.of(
                    new ExprIntNode("1", 1, 1),
                    new ExprIntNode("2", 1, 1)
            );
            ExprListAccessNode singlemultiacc = new ExprListAccessNode(doubleAcc, singleArrMultiAcc, 1, 1);
            AFSType singlemultiType = typeChecker.processExprNode(env, singlemultiacc);
            assertEquals(singlemultiType, SimpleType.INT);

            // Valid: Multi Array single access
            List<ExprNode> multiArrSingleAcc = List.of(
                    new ExprIntNode("1", 1, 1)
            );
            ExprListAccessNode multiSingleAcc = new ExprListAccessNode(doubleAcc, multiArrSingleAcc, 1, 1);
            AFSType multiSingleType = typeChecker.processExprNode(env, multiSingleAcc);
            assertEquals(multiSingleType, new ListType(SimpleType.INT));

            // Valid: Multi Array multi access
            List<ExprNode> multiArrMultiAcc = List.of(
                    new ExprIntNode("1", 1, 1),
                    new ExprIntNode("2", 1, 1)
            );
            ExprListAccessNode multiMultiAcc = new ExprListAccessNode(doubleAcc, multiArrMultiAcc, 1, 1);
            AFSType multiMultiType = typeChecker.processExprNode(env, multiMultiAcc);
            assertEquals(multiMultiType, SimpleType.INT);

            // Invalid: type mismatch
            List<ExprNode> invalidAcc = List.of(
                    new ExprIntNode("1", 1, 1),
                    new ExprStringNode("Hello", 1, 1)
            );
            ExprListAccessNode invalidAccNode = new ExprListAccessNode(singleAcc, invalidAcc, 1, 1);
            TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
                typeChecker.processExprNode(env, invalidAccNode);
            });
            assertEquals("Invalid type 'STRING': expected 'INT'", exception.getMessage());
        }
    }

    @Nested
    class TypeNode {
        @Test
        public void TypeIntNode() {
            // Int
            TypeIntNode type = new TypeIntNode(1, 1);
            AFSType returnType = typeChecker.processTypeNode(type);
            assertEquals(returnType, SimpleType.INT);
        }

        @Test
        public void TypeBoolNode() {
            TypeBoolNode type = new TypeBoolNode(1, 1);
            AFSType returnType = typeChecker.processTypeNode(type);
            assertEquals(returnType, SimpleType.BOOL);
        }

        @Test
        public void TypeStringNode() {
            TypeStringNode type = new TypeStringNode(1, 1);
            AFSType returnType = typeChecker.processTypeNode(type);
            assertEquals(returnType, SimpleType.STRING);
        }

        @Test
        public void TypeDoubleNode() {
            TypeDoubleNode type = new TypeDoubleNode(1, 1);
            AFSType returnType = typeChecker.processTypeNode(type);
            assertEquals(returnType, SimpleType.DOUBLE);
        }
    }
}
