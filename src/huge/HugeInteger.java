package huge;

import java.util.Arrays;
//import java.util.Random; //used for random
import java.util.concurrent.ThreadLocalRandom; //used for random

public class HugeInteger {

	//private char[] charArr; //array holding chars

	private int[] intArr; //final array.
	private boolean negative; //true if negative. false if positive.

	
	//constructors
	public HugeInteger(String val) throws ArithmeticException {
		
		//Trivial case if "0".
		if(val == "0") {
			this.intArr = new int[0];
			this.negative = false;
			return;
		}
		
		
		//first handle leading negative and remove it if it exists
		char firstChar = val.charAt(0);
		String newval = "";
		if(firstChar == '-') {
			this.negative = true;
			newval = val.substring(1); //new string without leading negative
		}
		
		//then remove leading zeroes from newval
		newval.replaceFirst("^0+(?!$)", "");
		
		//now check if there are any illegal characters still in string
		if(newval.matches("\\d+") == true) {
			throw new ArithmeticException("There are non numeric characters in the string.");
		}
		
		//now turn processed string into huge integer.
		
		//First step, convert string to chars
		char[] charArr = new char[newval.length()]; //initialize
		newval.getChars(0, newval.length(), charArr, 0);
		
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
	
	
	public HugeInteger(int n) throws ArithmeticException {
		
		//used to create a hugeinteger from a random integer of size n
	
		//first create the random integer
		//due to the possibility of being supplied n = 10000, int wont work
		//we need to directly randomize each array element
		//except for the final array element, which cannot be 0
		//also randomize the +ve or -ve.
		
		//first check to see if you put in a valid number
		
		//trivial case if input length 0
		if(n == 0) {
			this.intArr = new int[0];
			this.negative = false;
			return;
		}
		//if less than 0 length
		else if(n<0) {
			throw new ArithmeticException("A random integer can't have negative length.");
		}
		else if(n>100000) {
			throw new ArithmeticException("That is like way too long, above 100000 digits in length.");
		}
		else {
			//Random true or false
			this.negative = Math.random() < 0.5;
			
			//create an empty int array of length n
			this.intArr = new int[n];
			
			//loop through each entry except leading and set to a random integer, 0 to 10
			for(int i=0; i<n-1; i++) {
				this.intArr[i] = ThreadLocalRandom.current().nextInt(0, 9 + 1); //from 0 to 9. also +1 because convention
			}
			//now for the leading entry, set between 1 to 9
			this.intArr[n-1] = ThreadLocalRandom.current().nextInt(1, 9 + 1);
		}
	}
	
	//constructor required for add and other stuff
	//usually used for an array of zeroes of some digits length.
	//but you can fill with anything!
	public HugeInteger(int value, boolean neg, int digits) throws ArithmeticException {
		
		//if less than 0 length
		if(digits<0) {
			throw new ArithmeticException("An integer can't have negative length.");
		}
		else if(digits>100000) {
			throw new ArithmeticException("That is like way too long, above 100000 digits in length.");
		}
		else if(value<0) {
			throw new ArithmeticException("Value can't be negative. Use the boolean for that.");
		}
		else {
			this.negative = neg;
			
			intArr = new int[digits];
			Arrays.fill(intArr, value);
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
		//then make a 4th hugeinteger that is chopped down to size. no leading zeroes.
		//then do at the end:
		//this.intArr = 4thHuge.intArr 
		//account for negatives by using a conditional.
		
		//make a third bigger hugeinteger that we sum into
		//filled with zeroes.
		//then copy bigger array into sum
		if(this.intArr.length >= h.intArr.length) {
			HugeInteger sum = new HugeInteger(0,false,this.intArr.length+1);
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
			HugeInteger sum = new HugeInteger(0,false,h.intArr.length+1);
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
		//decimal representation of this HugeInteger in proper order
		
		//initialize our output string
		String out = new String();
		out = "";
		
		//handle negative
		if(this.negative == true) {
			out = out + "-" + "\n";
		}
		
		//append each entry.
		for(int i=this.intArr.length-1; i>=0; i--) {
			out = out + this.intArr[i] + "\n";
		}
		
		return out; 
	}
		
	
	public static void main(String[] args) {

		//remember that if you supply "-1245"
		//intArr's format is [5,4,2,1].
		//printed, will look like
		//true
		//5
		//4
		//2
		//1
		
		//test string constructor
		String strInput = "1245";
		
		HugeInteger hugey = new HugeInteger(strInput);
		
		System.out.println(hugey.negative);
		
		for(int i=0; i<hugey.intArr.length; i++) {
			System.out.println(hugey.intArr[i]);
		}
		
		System.out.println("heres the toString");
		System.out.println(hugey.toString());
		
		
		
//		//test int constructor
//		
//		int intInput = 44;
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
