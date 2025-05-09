@echo off

REM Run Coco.jar with arguments
java -jar Coco.jar AFS.ATG -package "syntactic_analysis" -o "..\src\main\java\afs\syntactic_analysis" -package "afs.syntactic_analysis"

REM Remove old parser and scanner if they exist
if exist "..\src\main\java\afs\syntactic_analysis\Parser.java.old" del "..\src\main\java\afs\syntactic_analysis\Parser.java.old"
if exist "..\src\main\java\afs\syntactic_analysis\Scanner.java.old" del "..\src\main\java\afs\syntactic_analysis\Scanner.java.old"
