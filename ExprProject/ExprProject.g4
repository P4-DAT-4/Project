grammar ExprProject;

prog
    : importStmt* (defStmt | stmt)* EOF                             #Main
    ;

defStmt
    : 'fn' type ID '('params')'* '{'funcBody'}'                     #FuncDef
    | defShape                                                      #ShapeDef
    ;

defShape
    : 'line' ID ':' '('aexpr','aexpr')' 'to' '('aexpr','aexpr')'    #LineDef
    | 'curve' ID ':' '[''('aexpr','aexpr')'',''('aexpr','aexpr')'','
      '('aexpr','aexpr')'',''('aexpr','aexpr')'']'                  #CurveDef
    ;

stmt
    : stmt stmt                                                     #Seq
    | ID '=' aexpr                                                  #VarAssign
    | type ID '=' expr                                              #VarDef
    | 'skip'                                                        #Skip
    | 'if' bexpr 'then' stmt 'else' stmt                            #IfElse
    | 'while' bexpr 'do' stmt                                       #While
    | 'return' expr                                                 #Return
    | lexpr                                                         #LexprStmt
    ;

aexpr
    : '-' aexpr                                                     #Negate
    | aexpr '+' aexpr                                               #Add
    | aexpr '-' aexpr                                               #Sub
    | aexpr '*' aexpr                                               #Mul
    | aexpr '/' aexpr                                               #Div
    | aexpr '%' aexpr                                               #Mod
    | '(' aexpr ')'                                                 #ParenAexpr
    | lexpr                                                         #ListExprAexpr
    | INT                                                           #Int
    | DOUBLE                                                        #Double
    | ID                                                            #Var
    ;

bexpr
    : '!' bexpr                                                     #Not
    | bexpr '||' bexpr                                              #Or
    | bexpr '&&' bexpr                                              #And
    | (aexpr | lexpr | accessexpr | vexpr) '=='
      (aexpr | lexpr | accessexpr | vexpr)                          #Equal
    | (aexpr | lexpr | accessexpr | vexpr) '<'
      (aexpr | lexpr | accessexpr | vexpr)                          #Less
    | '(' bexpr ')'                                                 #ParenBexpr
    | BOOL                                                          #Bool
    | ID                                                            #BoolVar
    ;

lexpr
    : '[' exprList ']'                                              #ListExpr
    | ID '[' aexpr ']'                                              #IndexAccess
    | ID '[' aexpr ']' '=' aexpr                                    #IndexAssign
    ;

texpr
    : 'place' '('aexpr','aexpr')'                                   #PlaceExpr
    | 'rotate' aexpr 'around' aexpr 'by' aexpr                      #RotateExpr
    | 'scale' aexpr 'by' aexpr                                      #ScaleExpr
    | 'visualize' ID 'with' ID                                      #VisualizeExpr
    ;

accessexpr
    : ID '.' ID                                                     #FieldAccess
    | ID '(' exprList ')'                                           #FuncCall
    | ID '(' ')'                                                    #FuncCallNoArgs
    | ID '.' ID '(' exprList ')'                                    #MethodCall
    | ID '.' ID '(' ')'                                             #MethodCallNoArgs
    | ID '.' ID '=' aexpr                                           #FieldAssign
    | ID '.' ID '=' ID '(' ')'                                      #FieldAssignNoArgs
    ;

vexpr
    : ID                                                            #VarExpr
    | STRING                                                        #String
    ;

expr
    : aexpr
    | bexpr
    | lexpr
    | texpr
    | accessexpr
    | vexpr
    ;

importStmt
    : 'import' stringList 'from' ID
    ;

params
    : type ID (',' type ID)*                                        #ParamList
    |                                                               #EmptyParams
    ;

funcBody
    : (stmt | defShape)*
    ;

stringList
    : STRING (',' STRING)*
    ;

exprList
    : aexpr (',' aexpr)*
    ;

type
    : 'bool'                                                        #BoolType
    | 'int'                                                         #IntType
    | 'string'                                                      #StringType
    | 'double'                                                      #DoubleType
    //| ID '->' type                                                  #FunctionType
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