import lejos.nxt.*;
import lejos.nxt.comm.RConsole;

public class LeJOSPerfScen {
    private final static int DEFINED_SPEED = 67;
    private final static int ZERO_VALUE = 1; // 1 chosen as a "zero value" for superoptimizer.

    public static void main(String[] args) {
        try {
            LightSensor lightSensor = new LightSensor(SensorPort.S1);
            int numberOfFailures = 0, reading = 0, checkValue = 0;
            int[] readingValues = {0, 50,  150, 100};
            int[] checkValues   = {0, 100, 100, 100};
            
            int startTime = 0, finishTime = 0;
		        int timeDifference = 0;
            boolean result = true;

            RConsole.open();
            setUp();
            
            for(int i=0; i<readingValues.length; i++) {
                startTime = (int) System.currentTimeMillis();
                functionForTest(reading, checkValue, lightSensor);
                finishTime = (int) System.currentTimeMillis();
                timeDifference = timeDifference + (finishTime - startTime);
            }
            
            // print average back to console
            RConsole.println(": " + (timeDifference / readingValues.length));
            
            RConsole.close();

        } catch (Exception e) {
            // no handler - expose to operator via LeJOS firmware.
        }

    }
    
    private static void setUp() {
        Motor.B.setSpeed(DEFINED_SPEED);
        Motor.C.setSpeed(DEFINED_SPEED);
    }

    private static void functionForTest(int reading, int checkValue,
                                        LightSensor lightSensor) {
        ${statement}
    }

}
