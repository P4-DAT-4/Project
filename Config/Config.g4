grammar Config;

config : statement* EOF ;
statement : key '=' value NEWLINE ;
key : ID ;
value : STRING
	| NUMBER
	| BOOLEAN ;

ID : [a-zA-Z_][a-zA-Z0-9]*;
STRING : '"' (~["\r\n])* '"' ;
NUMBER : [0-9]+ ('.' [0-9]+)? ;
BOOLEAN : 'true' | 'false' ;
NEWLINE : [\r\n]+ ;
WS : [ \t]+ -> skip ;
