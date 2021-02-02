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
		

		//first handle leading negative and remove it if it exists
		String newval = "";
		if(val.charAt(0) == '-') {
			this.negative = true;
			newval = val.substring(1); //new string without leading negative
		}
		else {
			this.negative = false;
			newval = val; //same string
		}
		
		//then remove leading zeroes from newval
		newval = newval.replaceFirst("^0+(?!$)", "");
		
		//now check if there are any illegal characters still in string
		if(newval.matches("[0-9]+") == false) {
			throw new ArithmeticException("There are characters other than 0 to 9");
		}
				
		
		//Trivial case if input "0" and "-0".
		if(newval.matches("^[0]")) {
			this.negative = false;
		}
		//these trivial cases handles 0 and -0 and 0000 and -0000
		//by turning it all into "0", this.negative = false.
		//then it goes through the constructor normally.
		

		
		//now turn processed string into huge integer.
		
		//convert string to chars
		char[] charArr = new char[newval.length()]; //initialize
		newval.getChars(0, newval.length(), charArr, 0);
		
		//now we can copy our stuff to an int array.
		this.intArr = new int[charArr.length]; //initialize
		for(int i=0; i < charArr.length; i++) {
			this.intArr[charArr.length-1-i] = charArr[i];
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
		
		//check to see if you put in a valid number
		if(n<0) {
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
	
	public HugeInteger add(HugeInteger h) {
		
		//basically just use long addition with carry overs
		//starting in ones place and going to millionths etc
		
		//what if this + h makes a bigger array?
		//have the sum array be 1 bigger than the bigger of the two
		//then make another final array, with no unwanted empty spaces. 
		
		//if none are negative
		if(this.negative == false && h.negative == false) {
			
			//dont need to have cases for which one is bigger.
			//just detect the size of the biggest, and make the sum
			//array one bigger.
			int arrMax = Math.max(this.intArr.length, h.intArr.length);
			
			//initialize a large sum array with allowance for overflow
			HugeInteger sum = new HugeInteger(0,false,arrMax+1);

			//This is positive only.
			sum.negative = this.negative;
			
			
			//copy this into sum
			for(int i=0; i<this.intArr.length; i++) {
				sum.intArr[i] = this.intArr[i];
			}
			
			//now do long addition with h
			int carry = 0;
			for(int i=0; i<h.intArr.length; i++) {
				
				int intSum = sum.intArr[i] + h.intArr[i] + carry;
				if(intSum >= 10) {
					carry = 1;
				}
				int remainder = intSum % 10;
				
				sum.intArr[i] = remainder;
			}
			
			//turn into a string to do some leading zero removal
			String output = new String();
			for(int i=sum.intArr.length-1; i>=0; i--) {
				output = output + sum.intArr[i];
			}
			output = output.replaceFirst("^0+(?!$)", "");
			
			//initialize a new final array
			HugeInteger sanitized = new HugeInteger(0,false,output.length());
			
			//copy string into a final array.
			//convert string to chars
			char[] charArray = new char[output.length()]; //initialize
			output.getChars(0, output.length(), charArray, 0);
			
			//now we can copy our stuff to an int array.
			sanitized.intArr = new int[charArray.length]; //initialize
			for(int i=0; i < charArray.length; i++) {
				sanitized.intArr[charArray.length-1-i] = charArray[i];
			}
			
			//Notice that the numbers are still encoded,
			//eg 0 -> 48, 1 -> 49 etc.
			//shift everything down by 48.
			for(int i=0; i < sanitized.intArr.length; i++) {
				sanitized.intArr[i] = sanitized.intArr[i] - 48;
			}
			
			
			return sanitized;
			
			//TODO: fix fails
		}

		//if this only is negative
		else if(this.negative == true && h.negative == false){
			; //deal with this in lab 2
			return h;
		}
		//if h only is negative
		else if(this.negative == false && h.negative == true ){
			; //deal with this in lab 2
			return h;
		}
		
		//if both are negative
		else {
			//use positive addition for double negatives.
			//then just say negative = true.
			return h;
		}
		

	}
	
	public HugeInteger subtract(HugeInteger h) {
		
		//if its two negative numbers, just call add LOL
		
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
			out = out + "-";
		}

		for(int i=this.intArr.length-1; i>=0; i--) {
			out = out + this.intArr[i];
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
		
//		//test string constructor
//		String strInput = "0012";
//		
//		HugeInteger hugey = new HugeInteger(strInput);
//		
//		System.out.println(hugey.negative);
//		System.out.println("heres the toString");
//		System.out.println(hugey.toString());
//		
//		
//		
//		//test int constructor
//		
//		int intInput = 3;
//		
//		HugeInteger hugeass = new HugeInteger(intInput);
//		
//		System.out.println(hugeass.negative);
//		System.out.println("heres the toString too");
//		System.out.println(hugeass.toString());
//		
//		
//
//		//test add by doing it to a hugeinteger already created
//		//eg add hugeass to hugey
//		HugeInteger Hugebruh = hugey.add(hugeass);
//		System.out.println("heres the toString three");
//		System.out.println(Hugebruh.toString());
		
		
	}

}
