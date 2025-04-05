# Project

## How to use?
1. Write grammer in a .g4 file. In this example the Expr.g4 file
2. Run ```antlr4``` with the .g4 file. In this example ```antlr4 Expr.g4```
3. Run ```javac *.java```
4. Setup grun ```alias grun='java -cp ".:antlr-4.12.2-complete.jar" org.antlr.v4.gui.TestRig'
5. Run the parser with grun: ```grun Expr prog -tree``` hit enter write the program and hit ```CMD + D``` on Mac or ```Ctrl + Z``` on Windows


