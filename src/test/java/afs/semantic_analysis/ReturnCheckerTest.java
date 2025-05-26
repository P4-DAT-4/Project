package afs.semantic_analysis;

import afs.nodes.expr.ExprIntNode;
import afs.nodes.expr.ExprNode;
import afs.nodes.stmt.*;
import afs.nodes.type.TypeIntNode;
import afs.nodes.type.TypeNode;
import afs.semantic_analysis.exceptions.UnreachableCodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ReturnCheckerTest extends ReturnChecker{
    private ReturnChecker returnChecker;

    @BeforeEach
    public void setUp() {
        returnChecker = new ReturnChecker();
    }

    @Test
    public void testStmtAssignment() {
        // Arrange
        ExprNode arbitraryExpr = new ExprIntNode("1", -1, -1);
        StmtNode assignmentNode = new StmtAssignmentNode("x", arbitraryExpr, -1, -1);

        // Act
        boolean result = returnChecker.checkStatement(assignmentNode);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testStmtListAssignment() {
        // Arrange
        ExprNode arbitraryExpr = new ExprIntNode("1", -1, -1);
        StmtNode listAssignmentNode = new StmtListAssignmentNode("x", new ArrayList<>() ,arbitraryExpr, -1, -1);

        // Act
        boolean result = returnChecker.checkStatement(listAssignmentNode);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testStmtFunctionCall() {
        // Arrange
        StmtNode functionCall = new StmtFunctionCallNode("x", new ArrayList<>(), -1, -1);

        // Act
        boolean result = returnChecker.checkStatement(functionCall);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testStmtReturn() {
        // Arrange
        ExprNode arbitraryExpr = new ExprIntNode("1", -1, -1);
        StmtNode returnNode = new StmtReturnNode(arbitraryExpr, -1, -1);

        // Act
        boolean result = returnChecker.checkStatement(returnNode);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testStmtSkip() {
        // Arrange
        StmtNode listAssignmentNode = new StmtSkipNode();

        // Act
        boolean result = returnChecker.checkStatement(listAssignmentNode);

        // Assert
        assertFalse(result);
    }

    @Nested
    class testCompStmt {
        @Test
        public void testCompStmt_BothReturn() {
            // Arrange
            ExprNode arbitraryExpr = new ExprIntNode("1", -1, -1);
            StmtNode left = new StmtReturnNode(arbitraryExpr, -1, -1);
            StmtNode right = new StmtReturnNode(arbitraryExpr, -1, -1);
            StmtNode compStmt = new StmtCompositionNode(left, right, -1, -1);

            // Act
            Executable action = () -> returnChecker.checkStatement(compStmt);

            // Assert
            Exception exception = assertThrows(UnreachableCodeException.class, action);
            assertTrue(
                    exception.getMessage().contains("Code after a return is unreachable"),
                    "Expected error as some code is unreachable"
            );
        }

        @Test
        public void testCompStmt_NeitherReturn() {
            // Arrange
            StmtNode left = new StmtSkipNode();
            StmtNode right = new StmtSkipNode();
            StmtNode compStmt = new StmtCompositionNode(left, right, -1, -1);

            // Act
            boolean result = returnChecker.checkStatement(compStmt);

            // Assert
            assertFalse(result);
        }

        @Test
        public void testCompStmt_FirstReturn() {
            // Arrange
            ExprNode arbitraryExpr = new ExprIntNode("1", -1, -1);
            StmtNode left = new StmtReturnNode(arbitraryExpr, -1, -1);
            StmtNode right = new StmtAssignmentNode("x", arbitraryExpr, -1, -1);
            StmtNode compStmt = new StmtCompositionNode(left, right, -1, -1);

            // Act
            Executable action = () -> returnChecker.checkStatement(compStmt);

            // Assert
            Exception exception = assertThrows(UnreachableCodeException.class, action);
            assertTrue(
                    exception.getMessage().contains("Code after a return is unreachable"),
                    "Expected error as some code is unreachable"
            );
        }

        @Test
        public void testCompStmt_SecondReturn() {
            // Arrange
            ExprNode arbitraryExpr = new ExprIntNode("1", -1, -1);
            StmtNode left = new StmtSkipNode();
            StmtNode right = new StmtReturnNode(arbitraryExpr, -1, -1);
            StmtNode compStmt = new StmtCompositionNode(left, right, -1, -1);

            // Act
            boolean result = returnChecker.checkStatement(compStmt);

            // Assert
            assertTrue(result);
        }
    }

    @Nested
    class testDeclarationStmt {
        @Test
        public void testDeclarationStmt_ReturnAfter() {
            // Arrange
            ExprNode arbitraryExpr = new ExprIntNode("1", -1, -1);
            StmtNode returnStmt = new StmtReturnNode(arbitraryExpr, -1, -1);

            TypeNode typeNode = new TypeIntNode(-1, -1);
            StmtNode declarationNode = new StmtDeclarationNode(typeNode, "x", arbitraryExpr, returnStmt, -1, -1);

            // Act
            boolean result = returnChecker.checkStatement(declarationNode);

            // Assert
            assertTrue(result);
        }

        @Test
        public void testDeclarationStmt_NoReturnAfter() {
            // Arrange
            ExprNode arbitraryExpr = new ExprIntNode("1", -1, -1);

            TypeNode typeNode = new TypeIntNode(-1, -1);
            StmtNode declarationNode = new StmtDeclarationNode(typeNode, "x", arbitraryExpr, new StmtSkipNode(), -1, -1);

            // Act
            boolean result = returnChecker.checkStatement(declarationNode);

            // Assert
            assertFalse(result);
        }
    }

    @Nested
    class testIfStmt {
        @Test
        public void testIfStmt_BothReturn() {
            // Arrange
            ExprNode arbitraryExpr = new ExprIntNode("1", -1, -1);
            StmtNode left = new StmtReturnNode(arbitraryExpr, -1, -1);
            StmtNode right = new StmtReturnNode(arbitraryExpr, -1, -1);
            StmtNode ifStmt = new StmtIfNode(arbitraryExpr, left, right, -1, -1);

            // Act
            boolean result = returnChecker.checkStatement(ifStmt);

            // Assert
            assertTrue(result);
        }

        @Test
        public void testIfStmt_NeitherReturn() {
            // Arrange
            ExprNode arbitraryExpr = new ExprIntNode("1", -1, -1);
            StmtNode left = new StmtSkipNode();
            StmtNode right = new StmtSkipNode();
            StmtNode ifStmt = new StmtIfNode(arbitraryExpr, left, right, -1, -1);

            // Act
            boolean result = returnChecker.checkStatement(ifStmt);

            // Assert
            assertFalse(result);
        }

        @Test
        public void testIfStmt_FirstReturn() {
            // Arrange
            ExprNode arbitraryExpr = new ExprIntNode("1", -1, -1);
            StmtNode left = new StmtReturnNode(arbitraryExpr, -1, -1);
            StmtNode right = new StmtSkipNode();
            StmtNode ifStmt = new StmtIfNode(arbitraryExpr, left, right, -1, -1);

            // Act
            boolean result = returnChecker.checkStatement(ifStmt);

            // Assert
            assertFalse(result);
        }

        @Test
        public void testIfStmt_SecondReturn() {
            // Arrange
            ExprNode arbitraryExpr = new ExprIntNode("1", -1, -1);
            StmtNode left = new StmtSkipNode();
            StmtNode right = new StmtReturnNode(arbitraryExpr, -1, -1);
            StmtNode ifStmt = new StmtIfNode(arbitraryExpr, left, right, -1, -1);

            // Act
            boolean result = returnChecker.checkStatement(ifStmt);

            // Assert
            assertFalse(result);
        }
    }

    @Nested
    class testWhileStatement {
        @Test
        public void testWhileStmt_ReturnAfter() {
            // Arrange
            ExprNode arbitraryExpr = new ExprIntNode("1", -1, -1);
            StmtNode returnStmt = new StmtReturnNode(arbitraryExpr, -1, -1);
            StmtNode whileStmt = new StmtWhileNode(arbitraryExpr, returnStmt, -1, -1);

            // Act
            boolean result = returnChecker.checkStatement(whileStmt);

            // Assert
            assertFalse(result);
        }

        @Test
        public void testWhileStmt_NoReturnAfter() {
            // Arrange
            ExprNode arbitraryExpr = new ExprIntNode("1", -1, -1);
            StmtNode whileStmt = new StmtWhileNode(arbitraryExpr, new StmtSkipNode(), -1, -1);

            // Act
            boolean result = returnChecker.checkStatement(whileStmt);

            // Assert
            assertFalse(result);
        }
    }
}
