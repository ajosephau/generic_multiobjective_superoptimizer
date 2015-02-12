grammar java_lejos_superoptimizer;

statement
    :   expression 
    |   'if ( ' logicalOperator ' ) { ' expression ' } else { ' expression  ' }'
    |   'while ( ' logicalOperator ' ) { ' expression ' } '
    ;

expression
    :   motorOperations ';'
    |   lightSensorOperations ';'
    |   '//NO OPERATION'
    ;

lightSensor
    :   'lightSensor'
    ;

lightSensorOperations
    :   lightSensor 'c.alibrateHigh()'
    |   lightSensor '.calibrateLow()'
    |   lightSensor '.setFloodlight(false)'
    |   lightSensor '.setFloodlight(false)'
    ;

lightSensorIntOperations
    :   lightSensor '.getHigh()'
    |   lightSensor '.getLow()'
    ;

motorOperations
    :   motor '.backward()'
    |   motor '.flt()'
    |   motor '.forward()'
    |   motor '.regulateSpeed(true)'
    |   motor '.regulateSpeed(false)'
    |   motor '.reverseDirection()'
    |   motor '.setPower( ' returnIntegerOperations ' )'
    |   motor '.shutdown()'
    |   motor '.stop()'
    ;

motor
    :   'Motor.B'
    |   'Motor.C'
    ;

variable
    :   'reading'
    ;

returnIntegerOperations
    :   '( ( ' logicalOperator ' ) ? ' integerValue ' : ' integerValue ' )'
    |   arithmeticOperations
    |   '( ' integerValue ' ) '
    ;

logicalOperator
    :   variable logicalOperands '( ' arithmeticOperations ' )'
    |   variable logicalOperands '( 'compoundArithmeticOperations  ' )'
    ;

compoundArithmeticOperations
    :    integerValue arithmeticOperands ' ( ' arithmeticOperations ' )'
    |   '( ' arithmeticOperations ' ) ' arithmeticOperands integerValue
    ;

arithmeticOperations
    :   integerValue arithmeticOperands integerValue
    ;

integerValue
    :   'DEFINED_POWER'
    |   lightSensorIntOperations
    |   '2'
    ;

arithmeticOperands
    :   (' + '|' - '|' * '|' / '|' % '|' & '|' | ')
    ;

logicalOperands
    :   (' > '|' < '|' == '|' != ')
    ;

LOWER_CASE_CHARACTERS : [a-z]+ ;  // match lower-case identifiers
WHITESPACE : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines, \r (Windows)
