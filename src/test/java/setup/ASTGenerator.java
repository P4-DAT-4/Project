package setup;

import afs.nodes.expr.*;
import afs.nodes.prog.ProgNode;
import afs.syntactic_analysis.Parser;
import afs.syntactic_analysis.Scanner;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class ASTGenerator {
    public static ProgNode GenerateFromString(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        Parser parser = new Parser(new Scanner(inputStream));
        parser.Parse();

        return parser.mainNode;
    }

    public static ExprNode litExpr(String value, String type) {
        return switch (type) {
            case "int" -> new ExprIntNode(value, -1, -1);
            case "double" -> new ExprDoubleNode(value, -1, -1);
            case "string" -> new ExprStringNode(value, -1, -1);
            case "bool" -> new ExprBoolNode(value, -1, -1);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    public static ExprNode binOpExpr(ExprNode a, BinOp op, ExprNode b) {
        return new ExprBinopNode(a, op, b, -1, -1);
    }
}
