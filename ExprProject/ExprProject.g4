grammar ExprProject;

prog
    : importStmt* (defStmt | stmt)* EOF                             #Main
    ;

defStmt
    : 'fn' type ID '('params')'* '{'funcBody'}'                     #FuncDef
    | 'visualize' ID'('ID')' 'with' ID'('ID')'                           #VisualizeDef
    | defShape                                                      #ShapeDef
    ;

defShape
    : 'line' ID ':' '('aexpr','aexpr')' 'to' '('aexpr','aexpr')'    #LineDef
    | 'curve' ID ':' '[''('aexpr','aexpr')'',''('aexpr','aexpr')'','
      '('aexpr','aexpr')'',''('aexpr','aexpr')'']'                  #CurveDef
    | 'text''('aexpr')' rexpr                                       #TextDef
    ;

stmt
    : stmt stmt                                                     #Seq
    | ID '=' aexpr                                                  #VarAssign
    | type ID '=' expr                                              #VarDef
    | ID '[' aexpr ']' '=' aexpr                                    #ListIndexAssign
    | 'skip'                                                        #Skip
    | 'if' bexpr 'then' '{'stmt'}' 'else' '{'stmt'}'                #IfElse
    | 'while' bexpr 'do' '{'stmt'}'                                 #While
    | 'return' expr                                                 #Return
    | 'event' STRING                                                #Event
    ;

aexpr
    : '-' aexpr                                                     #Negate
    | aexpr '+' aexpr                                               #Add
    | aexpr '-' aexpr                                               #Sub
    | aexpr '*' aexpr                                               #Mul
    | aexpr '/' aexpr                                               #Div
    | aexpr '%' aexpr                                               #Mod
    | '(' aexpr ')'                                                 #ParenAexpr
    | ID '[' aexpr ']'                                              #IndexAccessA
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
    | ID '[' aexpr ']'                                              #IndexAccessL
    | ID '[' aexpr ']' '=' aexpr                                    #IndexAssignL
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

rexpr
    : 'pInside' (ID | ID('('exprList')'))?                          #Inside
    | 'pAbove' (ID | ID('('exprList')'))?                           #Above
    | 'pBelow' (ID | ID('('exprList')'))?                           #Below
    | 'pRight' (ID | ID('('exprList')'))?                           #Right
    | 'pLeft' (ID | ID('('exprList')'))?                            #Left
    ;

expr
    : aexpr
    | bexpr
    | lexpr
    | texpr
    | accessexpr
    | vexpr
    | rexpr
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
    | '[' type ']'                                                  #ListType
    | ID                                                            #CustomType
    ;

BOOL: 'true' | 'false' ;
DOUBLE: [0-9]+ '.' [0-9]+ ;
INT: [0-9]+ ;
STRING: '"' (~["\\] | '\\' .)* '"' ;
ID: [a-zA-Z_][a-zA-Z_0-9]* ;

WS: [ \t\r\n]+ -> skip ;
NEWLINE: [\r\n]+ -> skip ;