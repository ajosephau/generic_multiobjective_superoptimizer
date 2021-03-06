import lejos.nxt.*;

public class LeJOSFnTest {
    private final static int DEFINED_SPEED = 67;
    private final static int ZERO_VALUE = 2; // 2 chosen as a "zero value" for superoptimizer.

    public static void main(String[] args) {
        try {
            LightSensor lightSensor = new LightSensor(SensorPort.S1);
            int numberOfFailures = 0, checkValue;
            final int OFFSET = 50;
            checkValue = (lightSensor.getHigh() + lightSensor.getLow()) / 2;
            int[] readingValues = {0, checkValue - OFFSET, checkValue, checkValue + OFFSET};
            int[] checkValues = {checkValue, checkValue, checkValue, checkValue};
            boolean result = true;

            setUp();

            for (int i = 0; i < readingValues.length; i++) {
                result = result && checkFunctionForTest(readingValues[i], checkValues[i], lightSensor);
                if (!result) {
                    numberOfFailures++;
                }
            }

            System.out.println(result + ": Number of failures: " + numberOfFailures);

        } catch (Exception e) {
            // no handler - expose to operator via LeJOS firmware.
        }
    }

    private static void setUp() {
        Motor.B.setSpeed(DEFINED_SPEED);
        Motor.C.setSpeed(DEFINED_SPEED);
        Motor.B.forward();
        Motor.C.forward();
    }

    private static boolean checkFunctionForTest(int reading, int checkValue, LightSensor lightSensor) {
        final int STOPPED_MOTOR_STATUS = 3;
        final int FORWARD_MOTOR_STATUS = 1;

        functionForTest(reading, checkValue, lightSensor);

        int motorBSpeed = Motor.B.getSpeed();
        int motorCSpeed = Motor.C.getSpeed();
        boolean isMotorBMoving = Motor.B.isMoving();
        boolean isMotorCMoving = Motor.C.isMoving();
        int motorBMode = Motor.B.getMode();
        int motorCMode = Motor.C.getMode();

        boolean result = true;

        // Check if motor B is still moving, and motor C is moving only if the
        //  conditions in the test are satisfied
        result = result && ((isMotorBMoving == true) && (motorBMode == FORWARD_MOTOR_STATUS) && (motorBSpeed == DEFINED_SPEED));
        if (reading > checkValue) {
            // Motor C should be moving
            result = result && ((isMotorCMoving == true) && (motorCMode == FORWARD_MOTOR_STATUS) && (motorCSpeed == DEFINED_SPEED));
        } else {
            // Motor C should have stopped (could be 'moving' at a 'ZERO_VALUE' speed)
            result = result && (((isMotorCMoving == false) && (motorCMode == STOPPED_MOTOR_STATUS)) || ((isMotorCMoving == true) && (motorCMode == FORWARD_MOTOR_STATUS) && (motorCSpeed == ZERO_VALUE)));
        }

        return result;
    }

    private static void functionForTest(int reading, int checkValue, LightSensor lightSensor) {
        ${statement}
    }
}
