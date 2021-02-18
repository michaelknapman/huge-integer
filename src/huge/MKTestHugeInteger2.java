package huge;

public class MKTestHugeInteger2 {
	
	public static void testAdd(String tester1, String tester2, String answer, boolean expectsException) {

		try {
			HugeInteger a = new HugeInteger(tester1);
			HugeInteger b = new HugeInteger(tester2);
			HugeInteger c = a.add(b);
			if(c.toString().equals(answer) && a.getNegative() == false) {
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

		//add
		testAdd("1002", "-3", "999", false);
		
		//subtract
		
		//multiplication
		
		//compareTo
		
	}

}
