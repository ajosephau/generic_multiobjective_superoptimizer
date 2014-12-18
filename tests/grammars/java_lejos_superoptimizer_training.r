parser grammar java_lejos_superoptimizer_training;

statement
    :   primary '=' statementExpression ';'
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
