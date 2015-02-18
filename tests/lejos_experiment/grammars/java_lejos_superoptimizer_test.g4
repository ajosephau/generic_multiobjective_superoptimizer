grammar java_lejos_superoptimizer;

statement
    :   'if ( reading > ( ( lightSensor.getHigh() + lightSensor.getLow() )  / ZERO_VALUE ) )  { Motor.B.forward();Motor.C.forward(); } else { Motor.B.forward();Motor.C.stop(); }'
    |   'Motor.C.setSpeed(((reading > checkValue) ? DEFINED_SPEED : ZERO_VALUE));'
    |   'if (reading > checkValue) { Motor.B.forward();  Motor.C.forward(); } else { Motor.B.forward(); Motor.B.stop(); }'
    ;

LOWER_CASE_CHARACTERS : [a-z]+ ;  // match lower-case identifiers
WHITESPACE : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines, \r (Windows)
