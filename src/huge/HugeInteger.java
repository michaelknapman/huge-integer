package huge;

import java.util.Arrays;

public class HugeInteger {

	//private char[] charArr; //array holding chars

	private int[] intArr; //final array.
	private boolean negative; //true if negative. false if positive.
	
	//note, if the integer is 145,
	//intArr's format is [5,4,1].
	
	//constructors
	public HugeInteger(String val) throws ArithmeticException {

		//turn string into huge integer
		
		//convert string to chars
		char[] charArr = new char[val.length()]; //initialize
		val.getChars(0, val.length(), charArr, 0);
		
//		//TODO: fix these checks
//		//if there is anything that isnt a number or negative in first
//		//then throw error
//		//also '0' is invalid in 0th position.
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
		if(charArr[0] == '-') {
			this.negative = true;
		}
		else {
			this.negative = false;
		}
		
		//now we can copy our stuff to an int array.
		//if its negative then we make it one smaller.

		if(this.negative == false) {
			this.intArr = new int[charArr.length]; //initialize
			for(int i=0; i < charArr.length; i++) {
				this.intArr[charArr.length-1-i] = charArr[i];
			}
		}
		else {
			intArr = new int[charArr.length-1]; //initialize
			for(int i=1; i < charArr.length; i++) {
				this.intArr[charArr.length-1-i] = charArr[i];
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
		
		//TODO: required to throw exception if wrong!
		
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
		int theCount = 0; //counter
		while(ncopy > 0) {
			int digit = ncopy%10;
			ncopy = ncopy / 10;
			intArr[theCount] = digit;
			theCount++;
		}
		
	}
	
	//constructor required for add and other stuff
	//usually used for an array of zeroes of some digits length.
	//but you can fill with anything!
	public HugeInteger(int value, int digits) {
		if(value >= 0) {
			this.negative = false;
			intArr = new int[digits];
			Arrays.fill(intArr, value);
		}
		else {
			this.negative = true;
			intArr = new int[digits];
			Arrays.fill(intArr, -value);
		}
	}
	

	/*methods ----------------------------------------------*/
	
	public void add(HugeInteger h) {
		
		//basically just use long addition
		//and carry overs
		//starting in ones place and going to million's or whatever
		
		//wait how to deal with when:
		//h is bigger than this
		//or this becomes bigger by a decimal place when h is added
		//solution: make a third hugeinteger that is 1 bigger than both!
		//add the two to this hugeinteger
		//then make a 4th hugeinteger that is chopped down to size. no zeroes at end.
		//then do at the end:
		//this.intArr = 4thHuge.intArr 
		//account for negatives by using a conditional.
		
		//make a third bigger hugeinteger that we sum into
		//filled with zeroes.
		//then copy bigger array into sum
		if(this.intArr.length >= h.intArr.length) {
			HugeInteger sum = new HugeInteger(0,this.intArr.length+1);
			//copy the bigger array into sum.
			for(int i=0; i<this.intArr.length; i++) {
				sum.intArr[i] = this.intArr[i];
			}
			//copy the negative status into sum
			sum.negative = this.negative;
			
			if(sum.negative == false) {
				//now do long addition with the smaller array.
				//add the two things, modulo, leave remainder in array
				//carry over the modulo if 10 or bigger.
				for(int i=0; i<this.intArr.length; i++) {
					int intSum = sum.intArr[i] + h.intArr[i];
					if(intSum >= 10) {
						sum.intArr[i+1] += 1;
					}
					int remainder = intSum % 10;
					
					sum.intArr[i] = remainder;
				}
			}
			else {
				//now do long subtraction starting from ones position
				//and borrow from the next position
				for(int i=0; i<h.intArr.length; i++) {
					if(sum.intArr[i] - h.intArr[i] < 0) {
						
					}
				}
				
				
			}
			

			
			
		}
		else {
			HugeInteger sum = new HugeInteger(0,h.intArr.length+1);
			//copy the bigger array into sum.
			for(int i=0; i<h.intArr.length; i++) {
				sum.intArr[i] = h.intArr[i];
			}
			
			//copy the negative status into sum
			sum.negative = h.negative;
			
			if(sum.negative == false) {
				//now do long addition with the smaller array.
				//add the two things, modulo, leave remainder in array
				//carry over the modulo if 10 or bigger.
				for(int i=0; i<h.intArr.length; i++) {
					int intSum = sum.intArr[i] + this.intArr[i];
					if(intSum >= 10) {
						sum.intArr[i+1] += 1;
					}
					int remainder = intSum % 10;
					
					sum.intArr[i] = remainder;
				}
			}
			else {
				//now do long subtraction with the smaller array.
				
				
			}
			
			
		}
		
		

		

		
		
		
		
		

		
		//if our sum hugeinteger is longer than required,
		//make a smaller hugeinteger called sanitized
		//HugeInteger sanitized = new HugeInteger(0,); 
		
		//its void, so just make this.stuff = 4tharray.stuff
	}
	
	public HugeInteger subtract(HugeInteger h) {
		
		//returns this - h
		return h; //placeholder
	}
	
	public HugeInteger multiply(HugeInteger h) {
		
		
		//add total number of digits together 
		//this.length * h.length
		//to make the length of the new array
		//it might be a bit too big
		//eg 99 x 100 means 9900 not ***** long.
		//long multiplication 
		
		//you will have to make an array for every addition
		//in long multiplication
		//huge!
		//or use Karatsuba algorithm
		
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
//		String strInput = "1245";
//		
//		HugeInteger hugey = new HugeInteger(strInput);
//		
//		System.out.println(hugey.negative);
//		
//		for(int i=0; i<hugey.intArr.length; i++) {
//			System.out.println(hugey.intArr[i]);
//		}
		
		
		
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
//		

		//test add by doing it to a hugeinteger already created
		//eg add hugeass to hugey
		
		//hugey.add(hugeass);
		//continue testing
		
		
		
	}

}
