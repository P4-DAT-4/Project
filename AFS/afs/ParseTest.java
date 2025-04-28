package afs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import afs.astbuilder.nodes.prog.ProgNode;
import afs.astbuilder.parser.ProgParser;
import afs.astprinter.GraphvizVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class ParseTest {
    public static ProgNode buildASTFromFile(Path filePath) throws IOException {
        // Step 1: Read from file
        CharStream input = CharStreams.fromPath(filePath);

        // Step 2: Setup lexer and parser
        AFSLexer lexer = new AFSLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        AFSParser parser = new AFSParser(tokens);

        // Step 3: Parse using entry rule (prog)
        AFSParser.ProgContext progContext = parser.prog();

        // Step 4: Build AST using your visitor
        ProgParser progParser = new ProgParser();
        return progContext.accept(progParser);
    }

    public static void main(String[] args) {
        try {
            Path sourceFile = Path.of("input.txt"); // adjust to your file
            ProgNode root = buildASTFromFile(sourceFile);

            GraphvizVisitor printer = new GraphvizVisitor();

            root.acceptVisit(printer);

            String dotCode = printer.getGraphvizCode();

            // Define the output file path
            File dotFile = new File("output.dot");

            // Write the DOT code to a file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(dotFile))) {
                writer.write(dotCode);
                System.out.println("DOT file written to: " + dotFile.getAbsolutePath());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
