package huge;

public class MKTestHugeInteger {

	public static void testStringConstruct(String tester, String answer, boolean expectsException) {

		try {
			HugeInteger a = new HugeInteger(tester);
			if(a.toString().equals(answer) && a.getNegative() == false ) {
				System.out.println("Success at " + tester);
			}
			else {
				System.out.println("Failure at " + tester);
			}
		} catch (Exception e) {
			if(expectsException == true) {
				System.out.println("Successful exception at "+ tester);
			}
			else {
				System.out.println("Unwanted exception at "+ tester);
			}
		}
	}
	
	
	public static void testPositiveAdd(String tester1, String tester2, String answer, boolean expectsException) {

		try {
			HugeInteger a = new HugeInteger(tester1);
			HugeInteger b = new HugeInteger(tester2);
			HugeInteger c = a.add(b);
			if(c.toString().equals(answer) && c.getNegative() == false) {
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
	
	
	
	
	public static void main(String[] args) {

		//print to console all test results.
		//this is to test any specific edge cases that the other file missed.
		
		//test string constructor
		testStringConstruct("0000", "0", false);
		testStringConstruct("-0000", "0", false);
		testStringConstruct("1.5", "0", true);
		
		//positive add
		testPositiveAdd("999", "1", "1000", false);
		testPositiveAdd("1", "999", "1000", false);
		testPositiveAdd("1", "0", "1", false);
		testPositiveAdd("0", "1", "1", false);
		testPositiveAdd("0", "0", "0", false);
		
	}

}
