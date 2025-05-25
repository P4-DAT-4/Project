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

    public static StmtNode parseDeclBlock(String input)  {
        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        setup.DECLBLOCK.Parser parser = new setup.DECLBLOCK.Parser(new setup.DECLBLOCK.Scanner(stream));
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

    public static StmtNode parseStmtBlock(String input)  {
        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        setup.STMTBLOCK.Parser parser = new setup.STMTBLOCK.Parser(new setup.STMTBLOCK.Scanner(stream));
        parser.Parse();
        return parser.mainNode;
    }

    public static TypeNode parseType(String input)  {
        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        setup.TYPE.Parser parser = new setup.TYPE.Parser(new setup.TYPE.Scanner(stream));
        parser.Parse();
        return parser.mainNode;
    }
}
