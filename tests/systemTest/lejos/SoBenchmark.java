import lejos.nxt.*;

public class SoBenchmark
{
    //private static int DEFINED_POWER = 33;

    public static void main( String[] args )
    {
        int int1,int2,int3;
		String const_string1, const_string2;

		int1=1;
		int2=2;
		int3=3;
		const_string1 = "";
		const_string2 = "";

		int1 = testFunction(int1,int2,int3);
                printvalue("int1", int1);
                printvalue("int2", int2);
		printvalue("int3", int3);
		printvalue("const_string1", const_string1);
		printvalue("const_string2", const_string2);

		int1=0;
		int2=0;
		int3=0;
		const_string1 = "";
		const_string2 = "";

		int1 = testFunction(int1,int2,int3);
                printvalue("int1", int1);
                printvalue("int2", int2);
		printvalue("int3", int3);
		printvalue("const_string1", const_string1);
		printvalue("const_string2", const_string2);

		int1=4;
		int2=5;
		int3=6;
		const_string1 = "";
		const_string2 = "";

		int1 = testFunction(int1,int2,int3);
                printvalue("int1", int1);
                printvalue("int2", int2);
		printvalue("int3", int3);
		printvalue("const_string1", const_string1);
		printvalue("const_string2", const_string2);
	}

	private static int testFunction(int int1,int int2,int int3)
	{
		String const_string1, const_string2;
		//int1 = int2 + int3;
int1 = int3 + int1;
		return int1;
	}

    private static void printvalue(String desc, int value)
    {
        LCD.drawString( "-------------", 0, 0 );
        LCD.drawString( desc + ": " + value, 0, 0 );
        LCD.drawString( "-------------", 0, 0 );
    }

    private static void printvalue(String desc, String value)
    {
        LCD.drawString( "-------------", 0, 0 );
        LCD.drawString( desc + ": " + value, 0, 0 );
        LCD.drawString( "-------------", 0, 0 );
    }
}
