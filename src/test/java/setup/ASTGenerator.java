package setup;

import afs.astprinter.Printer;
import afs.astprinter.RecursiveGraphvizPrinter;
import afs.nodes.def.DefNode;
import afs.nodes.event.EventNode;
import afs.nodes.expr.*;
import afs.nodes.prog.ProgNode;
import afs.nodes.stmt.StmtNode;
import afs.nodes.type.TypeNode;
import afs.syntactic_analysis.Parser;
import afs.syntactic_analysis.Scanner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ASTGenerator {
    public static void main(String[] args)  {
        ExprNode exprNode = parseExpr("2+3 * 4");
        System.out.println(exprNode);
    }

    public static ProgNode parseProgram(String input) {
        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        Parser parser = new Parser(new Scanner(stream));
        parser.Parse();
        return parser.mainNode;
    }

    public static StmtNode parseDecl(String input)  {
        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        setup.DECL.Parser parser = new setup.DECL.Parser(new setup.DECL.Scanner(stream));
        parser.Parse();
        return parser.mainNode;
    }

    public static ExprNode parseDeclExpr(String input)  {
        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        setup.DECLEXPR.Parser parser = new setup.DECLEXPR.Parser(new setup.DECLEXPR.Scanner(stream));
        parser.Parse();
        return parser.mainNode;
    }

    public static DefNode parseDef(String input)  {
        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        setup.DEF.Parser parser = new setup.DEF.Parser(new setup.DEF.Scanner(stream));
        parser.Parse();
        return parser.mainNode;
    }

    public static EventNode parseEvent(String input)  {
        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        setup.EVENT.Parser parser = new setup.EVENT.Parser(new setup.EVENT.Scanner(stream));
        parser.Parse();
        return parser.mainNode;
    }

    public static ExprNode parseExpr(String input)  {
        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        setup.EXPR.Parser parser = new setup.EXPR.Parser(new setup.EXPR.Scanner(stream));
        parser.Parse();
        return parser.mainNode;
    }

    public static StmtNode parseStmt(String input)  {
        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        setup.STMT.Parser parser = new setup.STMT.Parser(new setup.STMT.Scanner(stream));
        parser.Parse();
        return parser.mainNode;
    }

    public static TypeNode parseType(String input)  {
        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        setup.TYPE.Parser parser = new setup.TYPE.Parser(new setup.TYPE.Scanner(stream));
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
