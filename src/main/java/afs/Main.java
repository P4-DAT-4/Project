package afs;

// import afs.astprinter.RecursiveGraphvizPrinter;

import afs.SVGGenerator.SVGGenerator;
import afs.interpreter.ProgramInterpreter;
import afs.interpreter.interfaces.ImgStore;
import afs.nodes.prog.ProgNode;
import afs.semantic_analysis.ReturnChecker;
import afs.semantic_analysis.TypeChecker;
import afs.syntactic_analysis.Parser;
import afs.syntactic_analysis.Scanner;
import org.javatuples.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private final static ArgumentHandler argumentHandler = new ArgumentHandler();

    public static void main(String[] args) {
        setupArguments();

        if (!argumentHandler.parse(args)) {
            argumentHandler.printUsage();
            return;
        }

        // Get input and outputFiles
        String inputFile = argumentHandler.getArg("inputFile", String.class);
        String outputFile = argumentHandler.getArg("outputFiles", String.class);

        // Set width
        Integer width = argumentHandler.getFlagValue("--w", "width", Integer.class);
        if (width == null) {
            width = 1000; // default width
        }

        Integer height = argumentHandler.getFlagValue("--h", "height", Integer.class);
        if (height == null) {
            height = 1000; // default height
        }

        String printFile = argumentHandler.getFlagValue("--print", "printFile", String.class);

        try {
            Parser parser = new Parser(new Scanner(inputFile));
            parser.Parse();

            if (parser.hasErrors()) {
                System.out.println("Error parsing file: " + inputFile);
                return;
            }

            ProgNode program = parser.mainNode;

            TypeChecker typeChecker = new TypeChecker();
            typeChecker.checkProgram(program);

            ReturnChecker returnChecker = new ReturnChecker();
            returnChecker.checkReturn(program);

            ImgStore imgStore = ProgramInterpreter.evalProg(program);
            System.out.println("Imgstore size: " + imgStore.size());

            SVGGenerator.generateToFile(imgStore, width, height, outputFile);

            if (printFile != null) {
                System.out.println("Printing to: " + printFile);
            }
        } catch (Exception e) {
            System.out.println("Exception was thrown:");
            e.printStackTrace();
        }
    }

    private static void setupArguments() {
        argumentHandler.addRequiredArg("inputFile", ArgumentHandler.ArgType.STRING);
        argumentHandler.addRequiredArg("outputFiles", ArgumentHandler.ArgType.STRING);

        argumentHandler.addFlag("--w", List.of(new Pair<>("width", ArgumentHandler.ArgType.INT)));
        argumentHandler.addFlag("--h", List.of(new Pair<>("height", ArgumentHandler.ArgType.INT)));
        argumentHandler.addFlag("--print", List.of(new Pair<>("printFile", ArgumentHandler.ArgType.STRING)));
    }
}

