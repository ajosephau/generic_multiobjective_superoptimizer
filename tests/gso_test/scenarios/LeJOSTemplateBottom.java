
        return variableA;
    }

	private static void printvalue(String desc, int value) {
		LCD.drawString(desc + ": " + value, 0, 0);
	}

	private static void printvalue(String desc, boolean value) {
		LCD.drawString(desc + ": " + value, 0, 0);
	}
}
