grammar MiniLang;

// Parser rules
program : declaration* EOF ;

declaration : funcDecl
	| statement
	;

funcDecl : 'func' ID '(' paramList? ')' block ;

paramList : ID (',' ID)* ;

statement : ifStmt
	| assignStmt
	| returnStmt
	| funcCallStmt
	;

ifStmt : 'if' '(' condition ')' block ('else' block)? ;

block : '{' statement* '}' ;

condition : expr relop expr ;

expr : expr ('+' | '-') expr
	| expr ('*' | '/') expr
	| ID
	| NUMBER
	| funcCall
	| '(' expr')'
	;

assignStmt : ID '=' expr ';' ;

returnStmt : 'return' expr ';' ;

funcCallStmt : funcCall ';' ;

funcCall : ID '(' argList? ')' ;

argList : expr (',' expr)* ;

// Lexer rules
relop : '>' | '<' | '==' | '!=' | '>=' | '<=' ;

ID : [a-zA-Z_][a-zA-Z0-9_]* ;
NUMBER : [0-9]+ ;
NEWLINE : [\r\n]+ -> skip ;
WS : [ \t]+ -> skip ;
