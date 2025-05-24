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

public class Main {
    private final static String usage = "Usage: AFS <filename> [--print <outputfile>]";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println(usage);
            return;
        }

        String inputFile = args[0];
        String printFile = null;

        if (args.length == 3) {
            if ("--print".equals(args[1])) {
                printFile = args[2];
            } else {
                System.out.println(usage);
                return;
            }
        } else if (args.length > 1) {
            System.out.println(usage);
            return;
        }

        try {
            Parser parser = new Parser(new Scanner(inputFile));
            parser.Parse();

            if (parser.hasErrors()) {
                System.out.println("Error parsing file: " + inputFile);
            } else {
                ProgNode program = parser.mainNode;
                TypeChecker typeChecker = new TypeChecker();
                typeChecker.checkProgram(program);

                ReturnChecker returnChecker = new ReturnChecker();
                returnChecker.checkReturn(program);

                ImgStore imgStore = ProgramInterpreter.evalProg(program);
                System.out.println("Imgstore size: " + imgStore.size());

                SVGGenerator.generateToFile(imgStore, 1000, 1000, "test1_");

//                if (printFile != null) {
////                    new RecursiveGraphvizPrinter().print(program, printFile, true);
//                    System.out.println("AST printed to: " + printFile);
//                } else {
//                    // Interpret
//
//                }
            }
        } catch (Exception e) {
            System.out.println("Exception was thrown:");
            e.printStackTrace();
        }
    }
}
