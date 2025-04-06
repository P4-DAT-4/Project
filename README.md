# Project
Assumes Java is installed. If not [Java installation](https://www.java.com/en/download/manual.jsp)

## How to use
1. Write grammar in a .g4 file
2. Run ```antlr4 example.g4```
3. run ```javac -cp ".:path/to/antlr-4.13.2-complete.jar" *.java``` with the right path. The colon should be semicolon on Windows.
4. Setup grun by ```alias grun='java -cp ".:path/to/antlr-4.13.2-complete.jar" org.antlr.v4.gui.TestRig'``` again with the right path
5. Run the parser by ```grun example name -tree```, where ```name``` is the grammar name, given in the .g4 file. hit enter and write the program. Hit ```Ctrl + D``` on mac or ```Ctrl + Z``` on Windows
   - The -gui flag can be added to show the parse tree more visually

### Example Expr
Made a Expr dir
1. Wrote the Expr.g4 file
2. Ran ```antlr4 Expr.g4```
3. Ran ```javac -cp ".:../antlr-4.13.2-complete.jar" *.java```
4. Ran ```alias grun='java -cp ".:../antlr-4.13.2-complete.jar" org.antlr.v4.gui.TestRig'```
5. Ran the parser ```grun Expr prog -tree``` enter wrote ```1+2*3``` and then ```Ctrl + D``` or ```Ctrl + Z```
Got the result ```(prog (expr (expr 1) + (expr (expr 2) * (expr 3))) <EOF>)```.
