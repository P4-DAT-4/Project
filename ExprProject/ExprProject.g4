grammar ExprProject;

prog
    : 'import' stringList 'from' ID                                 #ImportStmt
    | defStmt*                                                      #FromDef
    | stmt*                                                         #FromDef
    | expr*                                                         #ExprStmt
    ;

defStmt
    : type ID '=' expr                                              #VarDef
    | 'fn' type ID '('params')'* '{'stmt'}'                         #FuncDef
    | 'line' ID ':' '('expr','expr')' 'to' '('expr','expr')'        #LineDef
    | 'curve' ID ':' '[''('expr','expr')'',''('expr','expr')'','
      '('expr','expr')'',''('expr','expr')'']'                      #CurveDef
    ;

stmt
    : stmt  stmt                                                    #Seq
    | ID '=' expr                                                   #VarAssign
    | type ID '=' expr                                              #VarDefStmt
    | expr '[' expr ']' '=' expr                                             #IndexAccessStmt
    | 'skip'                                                        #Skip
    | 'if' expr 'then' stmt 'else' stmt                             #IfElse
    | 'while' expr 'do' stmt                                        #While
    | 'return' expr                                                 #Return
    ;

expr
    : '-' expr                                                      #Negate
    | '!' expr                                                      #Not
    | expr '.' ID                                                   #FieldAccess
    | expr '[' expr ']'                                             #IndexAccess
    | '[' exprList ']'                                              #ListExpr
    | 'rotate' expr 'around' expr 'by' expr                         #RotateExpr
    | 'move' expr 'to' expr                                         #MoveExpr
    | 'offset' expr 'by' expr                                       #OffsetExpr
    | 'scale' expr 'by' expr                                        #ScaleExpr
    | expr '*' expr                                                 #Mul
    | expr '/' expr                                                 #Div
    | expr '%' expr                                                 #Mod
    | expr '+' expr                                                 #Add
    | expr '-' expr                                                 #Sub
    | expr '<' expr                                                 #Less
    | expr '==' expr                                                #Equal
    | expr '||' expr                                                #Or
    | expr '&&' expr                                                #And
    | expr '|' expr                                                 #BitOr
    | expr '&' expr                                                 #BitAnd
    | '(' expr ')'                                                  #ParenExpr
    | ID                                                            #Var
    | BOOL                                                          #Bool
    | INT                                                           #Int
    | DOUBLE                                                        #Double
    | STRING                                                        #String
    ;

params
    : type ID (',' type ID)*                                        #ParamList
    |                                                               #Empty
    ;

stringList
    : STRING (',' STRING)*
    ;

exprList
    : expr (',' expr)*
    ;

type
    : 'bool'                                                        #BoolType
    | 'int'                                                         #IntType
    | 'string'                                                      #StringType
    | 'double'                                                      #DoubleType
    | ID '->' type                                                  #FunctionType
    | '[' type ']'                                                  #ListType
    | ID                                                            #CustomType
    ;

prop
    : 'given' type ID
    | 'default' type ID
    | 'optional' type ID
    ;

BOOL: 'true' | 'false' ;
DOUBLE: [0-9]+ '.' [0-9]+ ;
INT: [0-9]+ ;
STRING: '"' (~["\\] | '\\' .)* '"' ;
ID: [a-zA-Z_][a-zA-Z_0-9]* ;

WS: [ \t\r\n]+ -> skip ;
NEWLINE: [\r\n]+ -> skip ;