package afs.semantic_analysis;

import afs.nodes.prog.ProgNode;
import afs.semantic_analysis.exceptions.TypeCheckException;
import afs.semantic_analysis.exceptions.UnreachableCodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.ASTGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReturnCheckerIntegrationTest extends ReturnChecker {

    @Test
    public void validReturns() {
        String input =
            "int t = 0;" +
            "fn int a() { " +
                "return true;" +
            "}"+
            "visualize a():" +
                "t do a();";
        ProgNode prog = ASTGenerator.GenerateFromString(input);
        checkReturn(prog);
    }

    @Test
    public void validReturnsComp() {
        String input =  "int t = 0;" +
                        "fn int a() { " +
                            "int x = 2;" +
                            "return x;" +
                        "}"+
                        "visualize a():" +
                            "t do a();";
        ProgNode prog = ASTGenerator.GenerateFromString(input);
        checkReturn(prog);
    }

    @Test
    public void invalidReturnsComp() {
        String input =  "int t = 0;\n" +
                "fn int a() { \n" +
                    "return 2;\n" +
                    "return 3;\n" +
                "}\n" +
                "visualize a():\n" +
                    "t do a();\n";
        ProgNode prog = ASTGenerator.GenerateFromString(input);
        UnreachableCodeException exception = assertThrows(UnreachableCodeException.class, () -> {
            checkReturn(prog);
        });
        assertEquals("Code after a return is unreachable: line 1, column 33", exception.getMessage());
    }
}
