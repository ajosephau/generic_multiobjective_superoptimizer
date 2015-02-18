grammar java_lejos_superoptimizer;

statement
    :   multipleExpressions
    |   'if' ' ( ' simpleLogicalOperator ' ) ' ' { ' multipleExpressions ' } '
    |   'if' ' ( ' simpleLogicalOperator ' ) ' ' { ' multipleExpressions ' } ' 'else' ' { ' multipleExpressions  ' } '
    |   'if' ' ( ' logicalOperator ' ) ' ' { ' basicMotorExpressions ' } ' 'else' ' { ' basicMotorExpressions  ' } '
    |   'while' ' ( ' simpleLogicalOperator ' ) ' ' { ' multipleExpressions ' } '
    |   'do' ' { ' multipleExpressions ' } ' 'while' ' ( ' simpleLogicalOperator ' ) '
    |   complexMotorOperations ';'
    ;

multipleExpressions
    :   expression
    |   expression ' ' expression
    ;

expression
    :   motorOperations ';'
    |   lightSensorOperations ';'
    ;

basicMotorExpressions
    :   basicMotorOperations ';'
    |   basicMotorOperations ';' basicMotorOperations ';'
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

basicMotorOperations
    :   motor '.forward()'
    |   motor '.stop()'
    ;

motorOperations
    :   basicMotorOperations
    |   motor '.backward()'
    |   motor '.flt()'
    |   motor '.regulateSpeed( ' booleanValue ' )'
    |   motor '.reverseDirection()'
    |   motor '.shutdown()'
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
    |   variable
    ;

simpleLogicalOperator
    :   variable logicalOperands integerValue
    |   variable logicalOperands variable
    ;

logicalOperator
    :   simpleLogicalOperator
    |   variable logicalOperands '( ' arithmeticOperations ' )'
    |   variable logicalOperands '( ' compoundArithmeticOperations  ' )'
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
    |   'DEFINED_SPEED'
    |   lightSensorFunctionsReturningAnInteger
    ;

variable
    :   'reading'
    |   'checkValue'
    ;

arithmeticOperands
    :   (' + '|' - '|' * '|' / '|' % '|' & '|' | ')
    ;

logicalOperands
    :   (' > '|' < '|' == '|' != ')
    ;

LOWER_CASE_CHARACTERS : [a-z]+ ;  // match lower-case identifiers
WHITESPACE : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines, \r (Windows)
