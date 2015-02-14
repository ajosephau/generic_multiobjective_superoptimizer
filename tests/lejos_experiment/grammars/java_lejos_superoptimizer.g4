grammar java_lejos_superoptimizer;

statement
    :   complexMotorOperations
    |   multipleExpressions
    |   'if' ' ( ' simpleLogicalOperator ' ) ' ' { ' multipleExpressions ' } '
    |   'if' ' ( ' simpleLogicalOperator ' ) ' ' { ' multipleExpressions ' } ' 'else' ' { ' multipleExpressions  ' } '
    |   'while' ' ( ' simpleLogicalOperator ' ) ' ' { ' multipleExpressions ' } '
    |   'do' ' { ' multipleExpressions ' } ' 'while' ' ( ' simpleLogicalOperator ' ) '
    ;

expression
    :   motorOperations ';'
    |   lightSensorOperations ';'
    ;

multipleExpressions
    :   expression
    |   expression ' ' expression
    ;

lightSensor
    :   'lightSensor'
    ;

lightSensorFunctionsReturningAnInteger
    :   lightSensor '.getHigh()'
    |   lightSensor '.getLow()'
    ;

lightSensorOperations
    :   lightSensor '.calibrateHigh()'
    |   lightSensor '.calibrateLow()'
    |   lightSensor '.setFloodlight( ' booleanValue ' )'
    ;

motor
    :   'Motor.B'
    |   'Motor.C'
    ;

motorOperations
    :   motor '.backward()'
    |   motor '.flt()'
    |   motor '.forward()'
    |   motor '.regulateSpeed( ' booleanValue ' )'
    |   motor '.reverseDirection()'
    |   motor '.shutdown()'
    |   motor '.stop()'
    ;

complexMotorOperations
    :   motor '.rotateTo( ' functionsReturningAnInteger ' )'
    |   motor '.setPower( ' functionsReturningAnInteger ' )'
    |   motor '.setSpeed( ' functionsReturningAnInteger ' )'
    ;

functionsReturningAnInteger
    :   '( ' '( ' logicalOperator ' )' ' ? ' integerValue ' : ' integerValue ' )'
    |   arithmeticOperations
    |   integerValue
    ;

simpleLogicalOperator
    :   variable logicalOperands integerValue
    ;

logicalOperator
    :   simpleLogicalOperator
    |   variable logicalOperands '( ' arithmeticOperations ' )'
    |   variable logicalOperands '( 'compoundArithmeticOperations  ' )'
    ;

compoundArithmeticOperations
    :   '( ' arithmeticOperations ' ) ' arithmeticOperands integerValue
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
    |   'checkValue'
    |   'DEFINED_POWER'
    |   lightSensorFunctionsReturningAnInteger
    ;

variable
    :   'reading'
    ;

arithmeticOperands
    :   (' + '|' - '|' * '|' / '|' % '|' & '|' | ')
    ;

logicalOperands
    :   (' > '|' < '|' == '|' != ')
    ;

LOWER_CASE_CHARACTERS : [a-z]+ ;  // match lower-case identifiers
WHITESPACE : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines, \r (Windows)
