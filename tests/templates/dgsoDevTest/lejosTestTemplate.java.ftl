import lejos.nxt.*;

public class LeJOSBenchmark {
	private final static int DEFINED_VALUE = 66;

	private final static int ZERO_VALUE = 0;

	public static void main(String[] args) {
		int reading, checkValue, normalSpeed, zeroSpeed, returnValue;
		boolean booleanValue;

		reading = 0;
		checkValue = 0;
		normalSpeed = DEFINED_VALUE;
		zeroSpeed = ZERO_VALUE;
		booleanValue = false;

		returnValue = testFunction(reading, checkValue, normalSpeed, zeroSpeed,
				booleanValue);
		printvalue("reading", reading);
		printvalue("checkValue", checkValue);
		printvalue("normalSpeed", normalSpeed);
		printvalue("zeroSpeed", zeroSpeed);
		printvalue("returnValue", returnValue);
		printvalue("booleanValue", booleanValue);
		LCD.drawString("-------------", 0, 0);

		reading = 50;
		checkValue = 100;
		normalSpeed = DEFINED_VALUE;
		zeroSpeed = ZERO_VALUE;
		booleanValue = false;

		returnValue = testFunction(reading, checkValue, normalSpeed, zeroSpeed,
				booleanValue);
		printvalue("reading", reading);
		printvalue("checkValue", checkValue);
		printvalue("normalSpeed", normalSpeed);
		printvalue("zeroSpeed", zeroSpeed);
		printvalue("returnValue", returnValue);
		printvalue("booleanValue", booleanValue);
		LCD.drawString("-------------", 0, 0);

		reading = 150;
		checkValue = 100;
		normalSpeed = DEFINED_VALUE;
		zeroSpeed = ZERO_VALUE;
		booleanValue = false;

		returnValue = testFunction(reading, checkValue, normalSpeed, zeroSpeed,
				booleanValue);
		printvalue("reading", reading);
		printvalue("checkValue", checkValue);
		printvalue("normalSpeed", normalSpeed);
		printvalue("zeroSpeed", zeroSpeed);
		printvalue("returnValue", returnValue);
		printvalue("booleanValue", booleanValue);
		LCD.drawString("-------------", 0, 0);

		reading = 100;
		checkValue = 100;
		normalSpeed = DEFINED_VALUE;
		zeroSpeed = ZERO_VALUE;
		booleanValue = false;

		returnValue = testFunction(reading, checkValue, normalSpeed, zeroSpeed,
				booleanValue);
		printvalue("reading", reading);
		printvalue("checkValue", checkValue);
		printvalue("normalSpeed", normalSpeed);
		printvalue("zeroSpeed", zeroSpeed);
		printvalue("returnValue", returnValue);
		printvalue("booleanValue", booleanValue);
		LCD.drawString("-------------", 0, 0);

		reading = 0;
		checkValue = 0;
		normalSpeed = DEFINED_VALUE;
		zeroSpeed = ZERO_VALUE;
		booleanValue = true;

		returnValue = testFunction(reading, checkValue, normalSpeed, zeroSpeed,
				booleanValue);
		printvalue("reading", reading);
		printvalue("checkValue", checkValue);
		printvalue("normalSpeed", normalSpeed);
		printvalue("zeroSpeed", zeroSpeed);
		printvalue("returnValue", returnValue);
		printvalue("booleanValue", booleanValue);
		LCD.drawString("-------------", 0, 0);

		reading = 50;
		checkValue = 100;
		normalSpeed = DEFINED_VALUE;
		zeroSpeed = ZERO_VALUE;
		booleanValue = true;

		returnValue = testFunction(reading, checkValue, normalSpeed, zeroSpeed,
				booleanValue);
		printvalue("reading", reading);
		printvalue("checkValue", checkValue);
		printvalue("normalSpeed", normalSpeed);
		printvalue("zeroSpeed", zeroSpeed);
		printvalue("returnValue", returnValue);
		printvalue("booleanValue", booleanValue);
		LCD.drawString("-------------", 0, 0);

		reading = 150;
		checkValue = 100;
		normalSpeed = DEFINED_VALUE;
		zeroSpeed = ZERO_VALUE;
		booleanValue = true;

		returnValue = testFunction(reading, checkValue, normalSpeed, zeroSpeed,
				booleanValue);
		printvalue("reading", reading);
		printvalue("checkValue", checkValue);
		printvalue("normalSpeed", normalSpeed);
		printvalue("zeroSpeed", zeroSpeed);
		printvalue("returnValue", returnValue);
		printvalue("booleanValue", booleanValue);
		LCD.drawString("-------------", 0, 0);

		reading = 100;
		checkValue = 100;
		normalSpeed = DEFINED_VALUE;
		zeroSpeed = ZERO_VALUE;
		booleanValue = true;

		returnValue = testFunction(reading, checkValue, normalSpeed, zeroSpeed,
				booleanValue);
		printvalue("reading", reading);
		printvalue("checkValue", checkValue);
		printvalue("normalSpeed", normalSpeed);
		printvalue("zeroSpeed", zeroSpeed);
		printvalue("returnValue", returnValue);
		printvalue("booleanValue", booleanValue);
	}

	private static int testFunction( int reading, int checkValue,
			int normalSpeed, int zeroSpeed, boolean booleanValue )
    {
        int returnValue = 0;

//if ( reading > checkValue ) {
//    returnValue = normalSpeed;
//}
//else {
//    returnValue = zeroSpeed;
//}

${statement}

        return returnValue;
    }

	private static void printvalue(String desc, int value) {
		LCD.drawString(desc + ": " + value, 0, 0);
	}

	private static void printvalue(String desc, boolean value) {
		LCD.drawString(desc + ": " + value, 0, 0);
	}
}
