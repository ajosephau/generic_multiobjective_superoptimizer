grammar java_lejos_superoptimizer;

statement
    :   expression 
    |   'if ( ' logicalOperator ' ) { ' expression ' } else { ' expression  ' }'
    ;

expression
    :   motorOperations ';'
    ;

returnIntegerOperations
    :   '(' '(' logicalOperator ')' ' ? ' integerValue ' : ' integerValue ')'
    |   '(' integerValue ')'
    ;

logicalOperator
    :   variable ('>'|'<'|'=='|'!=') arithmeticOperations
    ;

arithmeticOperations
    :   ' ( ' integerValue ('*'|'/'|'%'|'+') integerValue ' ) '
    |   ' ( ' arithmeticOperations ('*'|'/'|'%'|'+') integerValue ' ) '
    ;

lightSensorIntOperations
    :   lightSensor '.getHigh()'
    |   lightSensor '.getLow()'
    ;

lightSensor
    :   'lightSensor'
    ;
    
motorOperations
    :   motor '.forward()'
    |   motor '.stop()'
    |   motor '.setPower( ' returnIntegerOperations ')'
    ;
    
motor
    :   'Motor.B'
    |   'Motor.C'
    ;
    
variable
    :   'reading'
    ;
    
integerValue
    :   'DEFINED_POWER'
    |   lightSensorIntOperations
    |   '0'
    |   '2'
    ;
LOWER_CASE_CHARACTERS : [a-z]+ ;  // match lower-case identifiers
WHITESPACE : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines, \r (Windows)
