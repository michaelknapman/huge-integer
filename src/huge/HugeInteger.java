package huge;

public class HugeInteger {

	private char[] charArr; //array holding chars
	private int[] intArr; //sanitized numbers!
	private boolean negative; //true if negative. false if positive.
	
	//constructors
	public HugeInteger(String val) throws ArithmeticException {

		//turn string into huge integer
		
		//convert string to chars
		val.getChars(0, val.length(), this.charArr, 0);
		
		//if there is anything that isnt a number or negative in first
		//then throw error
		if(this.charArr[0] != '-' 
		|| this.charArr[0] != '0'
		|| this.charArr[0] != '1'
		|| this.charArr[0] != '2'
		|| this.charArr[0] != '3'
		|| this.charArr[0] != '4'
		|| this.charArr[0] != '5'
		|| this.charArr[0] != '6'
		|| this.charArr[0] != '7'
		|| this.charArr[0] != '8'
		|| this.charArr[0] != '9') {
			throw new ArithmeticException("Not a valid HugeInteger");
		}
		
		//if there are any other characters elsewhere
		//including a stray negative, then throw error.
		for(int i=0; i<val.length(); i++) {
			if(this.charArr[i] != '0'
			|| this.charArr[i] != '1'
			|| this.charArr[i] != '2'
			|| this.charArr[i] != '3'
			|| this.charArr[i] != '4'
			|| this.charArr[i] != '5'
			|| this.charArr[i] != '6'
			|| this.charArr[i] != '7'
			|| this.charArr[i] != '8'
			|| this.charArr[i] != '9') {
				throw new ArithmeticException("Not a valid HugeInteger");
			}
		}
		
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
			for(int i=0; i < charArr.length; i++) {
				this.intArr[i] = charArr[i];
			}
		}
		else if(this.negative == true) {
			for(int i=1; i < charArr.length; i++) {
				this.intArr[i-1] = charArr[i];
			}
		}
		//now we have a sanitized HugeInteger in intArr.
		//they are of type integer which means you can do arithmetic
		
		
		
		
		
		
		
		
		
	}
	
	
	public HugeInteger(int n) {
		
		//used to create a hugeinteger from integer n

		
	}

	
	
	
	//methods
	
	public HugeInteger add(HugeInteger h) {
		
		//return the sum of this + h
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

		//test string constructor
		String strInput = "-322";
		
		HugeInteger hugey = new HugeInteger(strInput);
		
		for(int i=0; i<hugey.intArr.length; i++) {
			System.out.println(hugey.intArr[i]);
		}

		
	}

}
