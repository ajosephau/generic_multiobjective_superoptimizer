grammar java_lejos_superoptimizer_training ;

statement
    :   primary '=' expression ';'
    ;

expression
    :   primary
    |   expression ('+'|'-') expression
    ;

primary
    :   'variableA'
    |   'variableB'
    |   'null'
    ;

LOWER_CASE_CHARACTERS : [a-z]+ ;  // match lower-case identifiers
WHITESPACE : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines, \r (Windows)
