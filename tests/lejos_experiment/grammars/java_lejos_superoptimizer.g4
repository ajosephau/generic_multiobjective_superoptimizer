grammar java_lejos_superoptimizer;

statement
    :   expression 
    |   expression expression
    |   'if' ' ( ' logicalOperator ' ) ' ' { ' expression ' ' expression ' } '
    |   'if' ' ( ' logicalOperator ' ) ' ' { ' expression ' } ' 'else' ' { ' expression  ' } '
    |   'while' ' ( ' logicalOperator ' ) ' ' { ' expression ' } '
    |   'while' ' ( ' logicalOperator ' ) ' ' { ' expression expression ' } '
    |   'do' ' { ' expression ' } ' 'while' ' ( ' logicalOperator ' ) '
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
    :   lightSensor '.calibrateHigh()'
    |   lightSensor '.calibrateLow()'
    |   lightSensor '.setFloodlight( ' booleanValue ' )'
    ;

lightSensorFunctionsReturningAnInteger
    :   lightSensor '.getHigh()'
    |   lightSensor '.getLow()'
    ;

motorOperations
    :   motor '.backward()'
    |   motor '.flt()'
    |   motor '.forward()'
    |   motor '.regulateSpeed( ' booleanValue ' )'
    |   motor '.reverseDirection()'
    |   motor '.setPower( ' functionsReturningAnInteger ' )'
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

functionsReturningAnInteger
    :   '( ' '( ' logicalOperator ' )' ' ? ' integerValue ' : ' integerValue ' )'
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

booleanValue
    :   'true'
    |   'false'
    ;

integerValue
    :   '2'
    |   'DEFINED_POWER'
    |   lightSensorFunctionsReturningAnInteger
    ;

arithmeticOperands
    :   (' + '|' - '|' * '|' / '|' % '|' & '|' | ')
    ;

logicalOperands
    :   (' > '|' < '|' == '|' != ')
    ;

LOWER_CASE_CHARACTERS : [a-z]+ ;  // match lower-case identifiers
WHITESPACE : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines, \r (Windows)
