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
		int variableA = 0, variableB = 0, variableC = 0;
