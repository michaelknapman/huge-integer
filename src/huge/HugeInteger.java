package huge;

public class HugeInteger {

	private char[] charArr; //array holding chars
	private int[] intArr; //encoded numbers then final array.
	private boolean negative; //true if negative. false if positive.
	
	//constructors
	public HugeInteger(String val) throws ArithmeticException {

		//turn string into huge integer
		
		//convert string to chars
		charArr = new char[val.length()]; //initialize
		val.getChars(0, val.length(), this.charArr, 0);
		
//		//TODO: fix these checks
//		//if there is anything that isnt a number or negative in first
//		//then throw error
		//also '0' is invalid in 0th position.
//		if(this.charArr[0] != '-' 
//		|| this.charArr[0] != '1'
//		|| this.charArr[0] != '2'
//		|| this.charArr[0] != '3'
//		|| this.charArr[0] != '4'
//		|| this.charArr[0] != '5'
//		|| this.charArr[0] != '6'
//		|| this.charArr[0] != '7'
//		|| this.charArr[0] != '8'
//		|| this.charArr[0] != '9') {
//			throw new ArithmeticException("bruh");
//		}
//		
//		//if there are any other characters elsewhere
//		//including a stray negative, then throw error.
//		for(int i=0; i<val.length(); i++) {
//			if(this.charArr[i] != '0'
//			|| this.charArr[i] != '1'
//			|| this.charArr[i] != '2'
//			|| this.charArr[i] != '3'
//			|| this.charArr[i] != '4'
//			|| this.charArr[i] != '5'
//			|| this.charArr[i] != '6'
//			|| this.charArr[i] != '7'
//			|| this.charArr[i] != '8'
//			|| this.charArr[i] != '9') {
//				throw new ArithmeticException("Not a valid HugeInteger");
//			}
//		}
		
		//now that we know its valid, is it negative? 
		if(this.charArr[0] == '-') {
			this.negative = true;
		}
		else {
			this.negative = false;
		}
		
		//now we can copy our stuff to an int array.
		//if its negative then we make it one smaller.

		if(this.negative == false) {
			this.intArr = new int[this.charArr.length]; //initialize
			for(int i=0; i < this.charArr.length; i++) {
				this.intArr[i] = this.charArr[i];
			}
		}
		else {
			intArr = new int[this.charArr.length-1]; //initialize
			for(int i=1; i < this.charArr.length; i++) {
				this.intArr[i-1] = this.charArr[i];
			}
		}
		
		//Notice that the numbers are still encoded,
		//eg 0 -> 48, 1 -> 49 etc.
		//shift everything down by 48.
		for(int i=0; i < this.intArr.length; i++) {
			this.intArr[i] = this.intArr[i] - 48;
		}
		
		//now we have a sanitized HugeInteger in intArr.
		//they are of type integer which means you can do
		//arithmetic operations
		
	}
	
	
	public HugeInteger(int n) {
		
		//used to create a hugeinteger from integer n
	
		int ncopy = n; //copy of n
		
		//is it negative?
		if(ncopy < 0) {
			this.negative = true;
		}
		else if(ncopy >= 0) {
			this.negative = false;
		}
		
		//if its negative, make it the positive complement.
		if(this.negative == true) {
			ncopy = ncopy - 2*ncopy;
		}
		
		//now find the length using a string
		String numstr = String.valueOf(ncopy);
		int templength = numstr.length();
		
		//use length to initialize intArr
		this.intArr = new int[templength];
		
		//extract digits using the modulo stuff from 
		//dr hassan's compeng 2sh4
		//remember to reverse the order so that the numbers arent backwards
		int theCount = 0; //counter
		while(ncopy > 0) {
			int digit = ncopy%10;
			ncopy = ncopy / 10;
			intArr[templength-1-theCount] = digit; //reverse order
			theCount++;
		}
		
		
	}

	/*methods ----------------------------------------------*/
	
	public HugeInteger add(HugeInteger h) {
		
		
		
		
		//return the sum of this + h
		return h; //placeholder
	}
	
	public HugeInteger subtract(HugeInteger h) {
		
		//returns this - h
		return h; //placeholder
	}
	
	public HugeInteger multiply(HugeInteger h) {
		
		//returns product of this * h
		return h; //placeholder
	}
		
	public int compareTo(HugeInteger h) {
		
		//returns -1 if this < h
		//returns 1 if this > h
		//returns 0 if this == h
		
		return 5; //placeholder
	}
		
	public String toString() {
		
		//returns a string representing the digits of the
		//decimal representation of this HugeInteger
		
		return ""; //placeholder 
	}
		
	
		
		
	
	public static void main(String[] args) {

//		//test string constructor
//		String strInput = "-0124";
//		
//		HugeInteger hugey = new HugeInteger(strInput);
//		
//		
//		for(int i=0; i<hugey.charArr.length; i++) {
//			System.out.println(hugey.charArr[i]);
//		}
//		
//		System.out.println(hugey.negative);
//		
//		for(int i=0; i<hugey.intArr.length; i++) {
//			System.out.println(hugey.intArr[i]);
//		}
//		
//		
//		
//		//test int constructor
//		
//		int intInput = -125;
//		
//		HugeInteger hugeass = new HugeInteger(intInput);
//		
//		System.out.println(hugeass.negative);
//		
//		for(int i=0; i<hugeass.intArr.length; i++) {
//			System.out.println(hugeass.intArr[i]);
//		}
		

		//test add by doing it to a hugeinteger already created
		//eg add hugeass to hugey
		
		//hugey.add(hugeass);
		//continue testing
		
		
		
	}

}
