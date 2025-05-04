#!/bin/bash

java -jar Coco.jar AFS.ATG -package "syntactic_analysis" -o "../src/main/java/afs/syntactic_analysis" -package "afs.syntactic_analysis"

# Remove old parser if it exists
[ -e "../src/main/java/afs/syntactic_analysis/Parser.java.old" ] && rm "../src/main/java/afs/syntactic_analysis/Parser.java.old"

# Remove old scanner if it exists
[ -e "../src/main/java/afs/syntactic_analysis/Scanner.java.old" ] && rm "../src/main/java/afs/syntactic_analysis/Scanner.java.old"
