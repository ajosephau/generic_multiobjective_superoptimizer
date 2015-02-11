grammar java_lejos_superoptimizer;

statement
    :   expression ';'
    |   'if ( ' logicalOperator ' ) { ' expression '; } else { ' expression  '; '
    ;

expression
    :   motorOperations
    ;

returnIntegerOperations
    :   logicalOperator ' ? ' integerValue ' : ' integerValue
    ;

logicalOperator
    :   '(reading > ( ' lightSensorOperations ' + ' lightSensorOperations ' ) / 2)'
    ;

lightSensorOperations
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
    
integerValue
    :   'DEFINED_POWER'
    |   '0'
    ;
LOWER_CASE_CHARACTERS : [a-z]+ ;  // match lower-case identifiers
WHITESPACE : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines, \r (Windows)
