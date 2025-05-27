package afs.semantic_analysis;

import afs.nodes.prog.ProgNode;
import afs.semantic_analysis.exceptions.MissingReturnException;
import afs.semantic_analysis.exceptions.UnreachableCodeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import setup.ASTGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class ReturnCheckerIntegrationTest extends ReturnChecker {

    @Test
    public void functionReturns_ShouldNotThrow() {
        // Arrange
        String input =
            "int t = 0;" +
            "fn int a() { " +
                "return true;" +
            "}"+
            "visualize a():" +
                "t do a();";
        ProgNode prog = ASTGenerator.parseProgram(input);

        // Act
        Executable action = () -> checkReturn(prog);

        // Assert
        assertDoesNotThrow(action);
    }

    @Test
    public void stmtCompReturnSecond_ShouldNotThrow() {
        // Arrange
        String input =  "int t = 0;" +
                        "fn int a() { " +
                            "int x = 2;" +
                            "return x;" +
                        "}"+
                        "visualize a():" +
                            "t do a();";
        ProgNode prog = ASTGenerator.parseProgram(input);

        // Act
        Executable action = () -> checkReturn(prog);

        // Assert
        assertDoesNotThrow(action);
    }

    @Test
    public void stmtCompReturnFirst_ShouldThrow() {
        // Arrange
        String input =  "int t = 0;" +
                        "fn int a() { " +
                            "return x;" +
                            "int x = 2;" +
                        "}"+
                        "visualize a():" +
                            "t do a();";
        ProgNode prog = ASTGenerator.parseProgram(input);

        // Act
        Executable action = () -> checkReturn(prog);

        // Assert
        assertThrows(UnreachableCodeException.class, action, "Expected an UnreachableCodeException");
    }

    @Test
    public void stmtCompBothReturn_ShouldThrow() {
        // Arrange
        String input =  "int t = 0;\n" +
                "fn int a() { \n" +
                    "return 2;\n" +
                    "return 3;\n" +
                "}\n" +
                "visualize a():\n" +
                    "t do a();\n";
        ProgNode prog = ASTGenerator.parseProgram(input);

        // Act
        Executable action = () -> checkReturn(prog);

        // Assert
        assertThrows(UnreachableCodeException.class, action, "Expected an UnreachableCodeException");
    }

    @Test
    public void stmtIfBothReturn_ShouldNotThrow() {
        // Arrange
        String input =
                "int t = 0;" +
                "fn int a() { " +
                    "if (true) then {" +
                        "return 1;" +
                    "} else {" +
                        "return 2;" +
                    "}" +
                "}"+
                "visualize a():" +
                    "t do a();";
        ProgNode prog = ASTGenerator.parseProgram(input);

        // Act
        Executable action = () -> checkReturn(prog);

        // Assert
        assertDoesNotThrow(action);
    }

    @Test
    public void stmtIfMissingReturnAfter_ShouldThrow() {
        // Arrange
        String input =
                "int t = 0;" +
                "fn int a() { " +
                    "if (true) then {" +
                        "return 1;" +
                    "}" +
                "}"+
                "visualize a():" +
                "t do a();";
        ProgNode prog = ASTGenerator.parseProgram(input);

        // Act
        Executable action = () -> checkReturn(prog);

        // Assert
        assertThrows(MissingReturnException.class, action, "Expected a MissingReturnException");
    }

    @Test
    public void stmtIfMissingReturnInElse_ShouldThrow() {
        // Arrange
        String input =
                "int t = 0;" +
                "fn int a() { " +
                    "if (true) then {" +
                        "return 1;" +
                    "} else {" +
                    "}" +
                "}"+
                "visualize a():" +
                "t do a();";
        ProgNode prog = ASTGenerator.parseProgram(input);

        // Act
        Executable action = () -> checkReturn(prog);

        // Assert
        assertThrows(MissingReturnException.class, action, "Expected a MissingReturnException");
    }

    @Test
    public void stmtIfMissingReturnInIf_ShouldThrow() {
        // Arrange
        String input =
                "int t = 0;" +
                "fn int a() { " +
                    "if (true) then {" +
                    "} else {" +
                        "return 2;" +
                    "}" +
                "}" +
                "visualize a():" +
                "t do a();";
        ProgNode prog = ASTGenerator.parseProgram(input);

        // Act
        Executable action = () -> checkReturn(prog);

        // Assert
        assertThrows(MissingReturnException.class, action, "Expected a MissingReturnException");
    }

    @Test
    public void stmtIfMissingBothReturn_ShouldThrow() {
        // Arrange
        String input =
                "int t = 0;" +
                "fn int a() { " +
                    "if (true) then {" +
                    "} else {" +
                    "}" +
                "}" +
                "visualize a():" +
                    "t do a();";
        ProgNode prog = ASTGenerator.parseProgram(input);

        // Act
        Executable action = () -> checkReturn(prog);

        // Assert
        assertThrows(MissingReturnException.class, action, "Expected a MissingReturnException");
    }

    @Test
    public void stmtIfReturnAfter_ShouldNotThrow() {
        // Arrange
        String input =
                "int t = 0;" +
                "fn int a() { " +
                    "if (true) then {" +
                    "} else {" +
                    "}" +
                    "return 1;" +
                "}" +
                "visualize a():" +
                    "t do a();";
        ProgNode prog = ASTGenerator.parseProgram(input);

        // Act
        Executable action = () -> checkReturn(prog);

        // Assert
        assertDoesNotThrow(action);
    }
}
