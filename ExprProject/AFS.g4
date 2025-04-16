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
    : '('expr')'                                                    #ParenExpr
    | expr '+' expr                                                 #AddExpr
    | expr '-' expr                                                 #SubExpr
    | expr '*' expr                                                 #MulExpr
    | expr '/' expr                                                 #DivExpr
    | '-'expr                                                       #NegExpr
    | expr '<=' expr                                                #LEqExpr
    | expr '>=' expr                                                #GEqExpr
    | expr '==' expr                                                #EqExpr
    | expr '<' expr                                                 #LsExpr
    | expr '>' expr                                                 #GtExpr
    | '!'expr                                                       #NotExpr
    | expr '||' expr                                                #OrExpr
    | expr '&&' expr                                                #AndExpr
    | expr'['expr']'                                                #ListIndexExpr
    | '['expr (',' expr)*']'                                        #ListExpr
    | funcCall                                                      #FuncCallExpr
    | '('type')'expr                                                #TypeCastExpr
    | expr '++' expr                                                #ConcatExpr
    | 'text' expr                                                   #TextExpr
    | 'line' '['expr*']'                                            #LineExpr
    | 'curve' '['expr*']'                                           #CurveExpr
    | 'rotate' expr 'around' expr 'by' expr                         #RotateExpr
    | 'scale' expr 'by' expr                                        #ScaleExpr
    | 'place' expr 'at' expr                                        #PlaceExpr
    | ID                                                            #ID
    | INT                                                           #Int
    | BOOL                                                          #Bool
    | DOUBLE                                                        #Double
    | STRING                                                        #String
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