package huge;

public class MKTestHugeInteger2 {
	
	public static void testAdd(String tester1, String tester2, String expectedAnswer, boolean expectedNegative, boolean expectsException) {

		try {
			HugeInteger a = new HugeInteger(tester1);
			HugeInteger b = new HugeInteger(tester2);
			HugeInteger c = a.add(b);
			if(c.toString().equals(expectedAnswer) && c.getNegative() == expectedNegative) {
				System.out.println("Success at " + tester1 + " and " + tester2);
			}
			else {
				System.out.println("Failure at "+ tester1 + " and " + tester2);
			}
		} catch (Exception e) {
			if(expectsException == true) {
				System.out.println("Successful exception at " + tester1 + " and " + tester2);
			}
			else {
				System.out.println("Unwanted exception at "+ tester1 + " and " + tester2);
			}
		}
	}
	
	public static void testSubtract(String tester1, String tester2, String expectedAnswer, boolean expectedNegative, boolean expectsException) {

		try {
			HugeInteger a = new HugeInteger(tester1);
			HugeInteger b = new HugeInteger(tester2);
			HugeInteger c = a.subtract(b);
			if(c.toString().equals(expectedAnswer) && c.getNegative() == expectedNegative) {
				System.out.println("Success at " + tester1 + " and " + tester2);
			}
			else {
				System.out.println("Failure at "+ tester1 + " and " + tester2);
			}
		} catch(Exception e) {
			if(expectsException == true) {
				System.out.println("Successful exception at " + tester1 + " and " + tester2);
			}
			else {
				System.out.println("Unwanted exception at "+ tester1 + " and " + tester2);
			}
		}
	}
	
	public static void testCompareTo(String tester1, String tester2, int answer, boolean expectsException) {
		
		try {
			HugeInteger a = new HugeInteger(tester1);
			HugeInteger b = new HugeInteger(tester2);
			int c = a.compareTo(b);
			if(c == answer) {
				System.out.println("Success at " + tester1 + " and " + tester2);
			}
			else {
				System.out.println("Failure at "+ tester1 + " and " + tester2);
			}
		} catch(Exception e) {
			if(expectsException == true) {
				System.out.println("Successful exception at " + tester1 + " and " + tester2);
			}
			else {
				System.out.println("Unwanted exception at "+ tester1 + " and " + tester2);
			}
		}
	}
	
	public static void testMultiply(String tester1, String tester2, String expectedAnswer, boolean expectedNegative, boolean expectsException) {
		try {
			HugeInteger a = new HugeInteger(tester1);
			HugeInteger b = new HugeInteger(tester2);
			HugeInteger c = a.multiply(b);
			if(c.toString().equals(expectedAnswer) && c.getNegative() == expectedNegative) {
				System.out.println("Success at " + tester1 + " and " + tester2);
			}
			else {
				System.out.println("Failure at "+ tester1 + " and " + tester2);
			}
		} catch(Exception e) {
			if(expectsException == true) {
				System.out.println("Successful exception at " + tester1 + " and " + tester2);
			}
			else {
				System.out.println("Unwanted exception at "+ tester1 + " and " + tester2);
			}
		}
	}
	
	
	public static void main(String[] args) {

		//print to console all test results.
		//this is to test any specific edge cases that the other file missed.

		//add
		testAdd("0", "-0", "0", false, false);
		
		//subtract
		testSubtract("-0", "-0", "0", false, false);
		
		//compareTo
		testCompareTo("0","-0",0,false);
		
		//multiplication
		testMultiply("0","-0","0",false,false);

		
	}

}
