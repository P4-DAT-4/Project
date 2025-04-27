grammar AFS;

@header{
package afs;
}

prog
    : def* visStmt EOF                                              #Main
    ;

visStmt
    : 'visualize' funcCall '{'event+'}'
    ;

event
    : expr 'do' funcCall ';'                                        #EventFunc
    ;

def
    : 'fn' type ID '('params?')' impBlock                           #FnDef
    | 'img' ID '(' params? ')' declBlock                            #VisDef //Derived from functions in the abstract syntax
    | type ID '=' expr ';'                                          #VarDef
    ;

params
    : type ID (',' type ID)*
    ;

declStmt
    : type ID '=' declExpr ';'                                      #DeclStmtDef
    | 'if' '(' declExpr ')' 'then' declBlock ('else' declBlock)?    #DeclStmtIf
    | funcCall ';'                                                  #DeclStmtFuncCall
    ;

declBlock
    : '{' declStmt* '}'
    ;

impStmt
    : type ID '=' expr ';'                                          #ImpStmtDef
    | ID '=' expr ';'                                               #ImpStmtAss // imp only
    | ID listAccess '=' expr ';'                                    #ImpStmtListAss // imp only
    | 'if' '(' expr ')' 'then' impBlock ('else' impBlock)?          #ImpStmtIf
    | 'while' '('expr')' 'do' impBlock                              #ImpStmtWhl // imp only
    | 'return' expr ';'                                             #ImpStmtRtn // imp only
    | funcCall ';'                                                  #ImpStmtFuncCall
    ;

impBlock
    : '{' impStmt* '}'
    ;

declExpr
    : expr                                                          #ImpExpr
    | 'place' expr 'at' '('expr','expr')'                           #PlaceExpr //decl only 'place line (0,0) to (1,1) at (2,2);' -> nej 'place X at (2,2);' -> ja
    | 'scale' expr 'by' expr                                        #ScaleExpr //decl only
    | 'rotate' expr 'around' expr 'by' expr                         #RotateExpr //decl only
    | 'text' expr                                                   #TextExpr //decl only
    | 'line' '('expr','expr')' ('to' '('expr','expr')')*            #LineExpr //decl only
    | 'curve' '('expr','expr')' ('to' '('expr','expr')')*           #CurveExpr //decl only
    ;

expr
    : '('expr')'                                                    #ParenExpr
    | ID listAccess                                                 #ListIndexExpr
    | '['expr (',' expr)*']'                                        #ListExpr
    | funcCall                                                      #FuncCallExpr
    | '-'expr                                                       #NegExpr
    | '!'expr                                                       #NotExpr
    | expr '*' expr                                                 #MulExpr
    | expr '/' expr                                                 #DivExpr
    | expr '+' expr                                                 #AddExpr
    | expr '-' expr                                                 #SubExpr
    | expr '++' expr                                                #ConcatExpr
    | expr '<=' expr                                                #LEqExpr
    | expr '>=' expr                                                #GEqExpr
    | expr '==' expr                                                #EqExpr
    | expr '!=' expr                                                #NEgExpr
    | expr '<' expr                                                 #LsExpr
    | expr '>' expr                                                 #GtExpr
    | expr '||' expr                                                #OrExpr
    | expr '&&' expr                                                #AndExpr
    | ID                                                            #ID
    | INT                                                           #Int
    | BOOL                                                          #Bool
    | DOUBLE                                                        #Double
    | STRING                                                        #String
    ;

listAccess
    : ('['expr']')+
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