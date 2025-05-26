package afs.semantic_analysis;

import afs.nodes.prog.ProgNode;
import afs.semantic_analysis.exceptions.TypeCheckException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import setup.ASTGenerator;

import static org.junit.jupiter.api.Assertions.*;

@Nested
public class TypeCheckerIntegrationTest {
    private TypeChecker typeChecker = new TypeChecker();

    @Test
    public void functionArgumentsValid(){
        String input =
                "int t = 0;" +
                "fn int a(int x, double y, bool z) { " +
                    "x = 3;" +
                    "y = 5.5;"+
                    "z = false;"+
                "}"+
                "visualize a(3, 2.5, false):" +
                    "t do a(3, 2.7, true);";
        ProgNode prog = ASTGenerator.parseProgram(input);
        typeChecker.checkProgram(prog);
    }

    @Test
    public void functionArgumentsInvalid(){
        String var = "x";
        String input =
                "int t = 0;" +
                "fn int a() { " +
                    "x = 3;" +
                "}"+
                "visualize a():" +
                    "t do a();";
        ProgNode prog = ASTGenerator.parseProgram(input);
        TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
            typeChecker.checkProgram(prog);
        });
        assertEquals(String.format("Variable '%s' not found", var), exception.getMessage());
    }

    @Test
    public void functionValidReturn(){
        String input =
            "int t = 0;" +
            "fn int a() { " +
                "return 3;" +
            "}"+
            "visualize a():" +
                "t do a();";
        ProgNode prog = ASTGenerator.parseProgram(input);
        typeChecker.checkProgram(prog);
    }

    @Test
    public void functionInvalidReturn(){
        String input =
            "int t = 0;" +
            "fn int a() { " +
                "return true;" +
            "}"+
            "visualize a():" +
                "t do a();";
        ProgNode prog = ASTGenerator.parseProgram(input);
        TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
            typeChecker.checkProgram(prog);
        });
        assertTrue(exception.getMessage().contains("Type mismatch: expected 'INT'"));
    }

    @Test
    public void functionVoidReturn(){
        String input =
                "int t = 0;" +
                "fn void a() { " +
                    "return true;" +
                "}"+
                "visualize a():" +
                    "t do a();";
        ProgNode prog = ASTGenerator.parseProgram(input);
        TypeCheckException exception = assertThrows(TypeCheckException.class, () -> {
            typeChecker.checkProgram(prog);
        });
        assertTrue(exception.getMessage().contains("Type mismatch: expected 'VOID'"));
    }
}
