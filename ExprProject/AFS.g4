grammar AFS;

prog
    : importStmt* def* visStmt* EOF                                  #Main
    ;

importStmt
    : 'import' STRING (',' STRING)* 'from' STRING
    ;

visStmt
    : 'visualize' funcCall event*
    ;

event
    : expr 'do' funcCall                                            #EventFunc
    ;

def
    : 'fn' type ID '('params?')' '{'stmt*'}'                        #FnDef
    | 'vis' ID '(' params? ')' '{'stmt*'}'                          #VisDef //Derived from functions in the abstract syntax
    | type ID '=' expr ';'                                          #VarDef
    ;

params
    : type ID (',' type ID)*
    ;

stmt
    : type ID '=' expr ';'                                          #StmtDef
    | expr '=' expr ';'                                             #StmtAss
    | 'if' '('expr')' 'then' '{'stmt*'}' ('else' '{'stmt*'}')?      #StmtIf
    | 'while' '('expr')' 'do' '{'stmt*'}'                           #StmtWhl
    | 'return' expr ';'                                             #StmtRtn
    ;

expr
    : '('expr')'
    | expr '+' expr
    | expr '-' expr
    | expr '*' expr
    | expr '/' expr
    | '-'expr
    | expr '<=' expr
    | expr '>=' expr
    | expr '==' expr
    | expr '<' expr
    | expr '>' expr
    | '!'expr
    | expr '||' expr
    | expr '&&' expr
    | expr'['expr']'
    | '['expr (',' expr)*']'
    | funcCall
    | '('type')'expr
    | expr '++' expr
    | 'text' expr
    | 'line' '['expr*']'
    | 'curve' '['expr*']'
    | 'rotate' expr 'around' expr 'by' expr
    | 'scale' expr 'by' expr
    | 'place' expr 'at' expr
    | ID | INT | BOOL | DOUBLE | STRING
    ;

funcCall
    : ID '(' (expr (',' expr)*)? ')'
    ;

type
    : 'bool'                                                        #BoolType
    | 'int'                                                         #IntType
    | 'string'                                                      #StringType
    | 'double'                                                      #DoubleType
    | 'shape'                                                       #ShapeType
    | '[' type ']'                                                  #ListType
    | 'void'                                                        #VoidType
    ;

BOOL: 'true' | 'false' ;
DOUBLE: [0-9]+ '.' [0-9]+ ;
INT: [0-9]+ ;
STRING: '"' (~["\\] | '\\' .)* '"' ;
ID: [a-zA-Z_][a-zA-Z_0-9]* ;

WS: [ \t\r\n]+ -> skip ;
NEWLINE: [\r\n]+ -> skip ;