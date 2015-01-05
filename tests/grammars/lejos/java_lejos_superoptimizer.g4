grammar java_lejos_superoptimizer;

statement
    :   'if' parExpression statement ('else' statement)?
    |   'while' parExpression statement
    |   'do' statement 'while' parExpression ';'
    |   'switch' parExpression '{' switchBlockStatementGroup* switchLabel* '}'
    |   'return' expression? ';'
    |   primary '=' expression ';'
    ;

parExpression
    :   '(' expression ')'
    ;

switchBlockStatementGroup
    :   switchLabel+ statement+
    ;

switchLabel
    :   'case' constantExpression ':'
    |   'default' ':'
    ;

expression
    :   primary
    |   expression ('++' | '--')
    |   ('+'|'-'|'++'|'--') expression
    |   ('~'|'!') expression
    |   expression ('*'|'/'|'%') expression
    |   expression ('+'|'-') expression
    |   expression ('<' '<' | '>' '>' '>' | '>' '>') expression
    |   expression ('<=' | '>=' | '>' | '<') expression
    |   expression ('==' | '!=') expression
    |   expression '&' expression
    |   expression '^' expression
    |   expression '|' expression
    |   expression '&&' expression
    |   expression '||' expression
    |   expression '?' expression ':' expression
    ;

primary
    :   'variableA'
    |   'variableB'
    |   'null'
    ;

constantExpression
    :   'case1'
    |   'case2'
    |   'case3'
    ;

LOWER_CASE_CHARACTERS : [a-z]+ ;  // match lower-case identifiers
WHITESPACE : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines, \r (Windows)
