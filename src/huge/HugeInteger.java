package huge;

import java.util.Arrays;
//import java.util.Random; //used for random
import java.util.concurrent.ThreadLocalRandom; //used for random

public class HugeInteger {

	private int[] intArr; //final array.
	private boolean negative; //true if negative. false if positive.
	
	/*constructors ----------------------------------------------*/
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
		} 		//trivial case handles 0 and -0 and 0000 and -0000
		
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
		
	}
	
	
	public HugeInteger(int n) throws ArithmeticException {
		
		//check to see if you put in a valid number
		if(n<0) {
			throw new ArithmeticException("A random integer can't have negative length.");
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
	public HugeInteger(int value, boolean neg, int digits) throws ArithmeticException {
		
		//if less than 0 length
		if(digits<0) {
			throw new ArithmeticException("An integer can't have negative length, additional constructor");
		}
		else if(value<0) {
			throw new ArithmeticException("Value can't be negative. Use the boolean for that, additional constructor");
		}
		else {
			this.negative = neg;
			
			intArr = new int[digits];
			Arrays.fill(intArr, value);
		}


	}
	

	/*methods ----------------------------------------------*/
	
	public HugeInteger add(HugeInteger h) {
		
		//trivial case when both are 0
		if(this.intArr.length == 1 && h.intArr.length == 1 && this.intArr[0] == 0 && h.intArr[0] == 0) {
			this.negative = false;
			return this;
		}
		//trivial case when this is 0
		if(this.intArr.length == 1 && this.intArr[0] == 0) {
			return h;
		}
		//trivial case when h is 0
		else if(h.intArr.length == 1 && h.intArr[0] == 0) {
			return this;
		}

		
		//trivial cases. if different sign, flip sign and call subtract.
		if(this.negative == false && h.negative == true){ 
			h.negative = false;
			return this.subtract(h);
		}
		else if(this.negative == true && h.negative == false) {
			h.negative = true;
			return this.subtract(h);
		}
		
		else { 	//if signs are equal.
			
			//find the largest magnitude length
			int arrMax = Math.max(this.intArr.length, h.intArr.length);
			
			//initialize a large sum array with allowance for overflow
			HugeInteger sum = new HugeInteger(0,false,arrMax+1);
			
			//copy this into sum
			for(int i=0; i<this.intArr.length; i++) {
				sum.intArr[i] = this.intArr[i];
			}
			
			//now do long addition with h
			int carry = 0;
			for(int i=0; i<sum.intArr.length; i++) {
				
				int intSum = carry;
				
				if(i < this.intArr.length) {
					intSum = intSum + this.intArr[i];
				}
				if(i < h.intArr.length) {
					intSum = intSum + h.intArr[i];
				}
				
								
				if(intSum >= 10) {
					carry = 1;
				}
				else {
					carry = 0;
				}
				
				//remainder
				sum.intArr[i] = intSum % 10;
			}
			
			//now copy to string
			String output = new String();
			for(int i=sum.intArr.length-1; i>=0; i--) {
				output = output + sum.intArr[i];
			}

			//do leading zero removal 
			output = output.replaceFirst("^0+(?!$)", "");
			
			//initialize a new final array
			HugeInteger sanitized = new HugeInteger(0,false,output.length());
			
			//copy string into a final array.
			//convert string to chars
			char[] charArray = new char[output.length()]; //initialize
			output.getChars(0, output.length(), charArray, 0);
			
			//now we can copy our stuff to a new sanitized HugeInteger
			sanitized.intArr = new int[charArray.length]; //initialize
			for(int i=0; i < charArray.length; i++) {
				sanitized.intArr[charArray.length-1-i] = charArray[i];
			}
			
			//Notice that the numbers are still ascii encoded,
			//eg 0 -> 48, 1 -> 49 etc.
			//shift everything down by 48.
			for(int i=0; i < sanitized.intArr.length; i++) {
				sanitized.intArr[i] = sanitized.intArr[i] - 48;
			}
			
			//finally set the negative to the proper value
			sanitized.negative = this.negative;
						
			return sanitized;
		}
		

		

	}
	
	public HugeInteger subtract(HugeInteger h) {
		
		//trivial case when both are 0
		if(this.intArr.length == 1 && h.intArr.length == 1 && this.intArr[0] == 0 && h.intArr[0] == 0) {
			this.negative = false;
			return this;
		}
		//trivial case when this is 0
		else if(this.intArr.length == 1 && this.intArr[0] == 0) {
			h.negative = !h.negative;
			return h;
		}
		//trivial case when h is 0
		else if(h.intArr.length == 1 && h.intArr[0] == 0) {
			return this;
		}
		
		//trivial cases. if different sign, flip sign and call add.
		if(this.negative == false && h.negative == true) {
			h.negative = false;
			return this.add(h);
		}
		else if(this.negative == true && h.negative == false) {
			h.negative = true;
			return this.add(h);
		}
		
		
		
		//find which has bigger magnitude: this or h.
		boolean thisbiggerorequal = true;
		if(this.intArr.length > h.intArr.length) {
			thisbiggerorequal = true;
		}
		else if(this.intArr.length < h.intArr.length) {
			thisbiggerorequal = false;
		}
		else { //same length
			//loop, if everything is equal then its true.
			for(int i=this.intArr.length-1;i>=0;i--) {
				if(this.intArr[i] > h.intArr[i]) {
					thisbiggerorequal = true;
					break;
				}
				else if(this.intArr[i] < h.intArr[i]) {
					thisbiggerorequal = false;
					break;
				}
				else { //if this.intArr[i] == h.intArr[i]
					;//continue looping
				}
			}
		}
		
		//if this>=h then do this-h
		if(thisbiggerorequal == true) {
			
			//initialize a sum. we will trim it down later
			HugeInteger sum = new HugeInteger(0,this.negative,this.intArr.length+1);

			//copy this into sum
			for(int i=0; i<this.intArr.length; i++) {
				sum.intArr[i] = this.intArr[i];
			}
			
			//now do long subtraction
			int carrydown = 0;
			for(int i=0; i<sum.intArr.length; i++) {
				
				int elemInt = carrydown;
				
				if(i < this.intArr.length) {
					elemInt = elemInt + this.intArr[i];
				}
				if(i < h.intArr.length) {
					elemInt = elemInt - h.intArr[i];
				}
				
				if(elemInt < 0) {
					carrydown = -1;
					elemInt = elemInt + 10;
				}
				else {
					carrydown = 0;
				}
				
				sum.intArr[i] = elemInt;
			}
			
			//now copy to string
			String output = new String();
			for(int i=sum.intArr.length-1; i>=0; i--) {
				output = output + sum.intArr[i];
			}

			//do leading zero removal 
			output = output.replaceFirst("^0+(?!$)", "");
			
			
			//Trivial case if output is -0 or 0
			if(output.matches("^[0]")) {
				this.negative = false;
			}
			
			//initialize a new final array
			HugeInteger sanitized = new HugeInteger(0,false,output.length());
			
			//copy string into a final array.
			//convert string to chars
			char[] charArray = new char[output.length()]; //initialize
			output.getChars(0, output.length(), charArray, 0);
			
			//now we can copy our stuff to a new sanitized HugeInteger
			sanitized.intArr = new int[charArray.length]; //initialize
			for(int i=0; i < charArray.length; i++) {
				sanitized.intArr[charArray.length-1-i] = charArray[i];
			}
			
			//Notice that the numbers are still ascii encoded,
			//eg 0 -> 48, 1 -> 49 etc.
			//shift everything down by 48.
			for(int i=0; i < sanitized.intArr.length; i++) {
				sanitized.intArr[i] = sanitized.intArr[i] - 48;
			}
			
			//finally set the negative to the proper value
			sanitized.negative = this.negative;
			
			return sanitized;
			
		}
		//if this<h then do -(h-this) to avoid having to deal with subtracting a larger number
		else {
			//initialize a sum. we will trim it down later
			HugeInteger sum = new HugeInteger(0,!h.negative,h.intArr.length+1);
			
			//copy h into sum
			for(int i=0; i<h.intArr.length; i++) {
				h.intArr[i] = h.intArr[i];
			}
			
			//now do long subtraction
			int carrydown = 0;
			for(int i=0; i<sum.intArr.length; i++) {
				
				int elemInt = carrydown;
				
				if(i < h.intArr.length) {
					elemInt = elemInt + h.intArr[i];
				}
				if(i <this.intArr.length) {
					elemInt = elemInt - this.intArr[i];
				}
				
				if(elemInt < 0) {
					carrydown = -1;
					elemInt = elemInt + 10;
				}
				else {
					carrydown = 0;
				}
				
				sum.intArr[i] = elemInt;
			}
			
			//now copy to string
			String output = new String();
			for(int i=sum.intArr.length-1; i>=0; i--) {
				output = output + sum.intArr[i];
			}

			//do leading zero removal 
			output = output.replaceFirst("^0+(?!$)", "");
			
			
			//Trivial case if output is -0 or 0
			if(output.matches("^[0]")) {
				this.negative = false;
			}
			
			//initialize a new final array
			HugeInteger sanitized = new HugeInteger(0,false,output.length());
			
			//copy string into a final array.
			//convert string to chars
			char[] charArray = new char[output.length()]; //initialize
			output.getChars(0, output.length(), charArray, 0);
			
			//now we can copy our stuff to a new sanitized HugeInteger
			sanitized.intArr = new int[charArray.length]; //initialize
			for(int i=0; i < charArray.length; i++) {
				sanitized.intArr[charArray.length-1-i] = charArray[i];
			}
			
			//Notice that the numbers are still ascii encoded,
			//eg 0 -> 48, 1 -> 49 etc.
			//shift everything down by 48.
			for(int i=0; i < sanitized.intArr.length; i++) {
				sanitized.intArr[i] = sanitized.intArr[i] - 48;
			}
			
			//finally set the negative to the proper value
			sanitized.negative = !this.negative;
			
			return sanitized;
		}

	}
	
	public HugeInteger multiply(HugeInteger h) {
		

		//trivial case, multiply by zero
		if(this.intArr.length == 1 && this.intArr[0] == 0 ) {
			this.negative = false;
			return this;
		}
		if(h.intArr.length == 1 && h.intArr[0] == 0) {
			h.negative = false; 
			return h;
		}
		
		//sum length = this length + h length in worst case.
		HugeInteger sum = new HugeInteger(0,false,this.intArr.length+h.intArr.length);
		
		//handle negatives
		if(this.negative == h.negative) {
			sum.negative = false;
		}
		else {
			sum.negative = true;
		}

		
		
		//basically, split the hugeinteger into two hugeintegers.
		//then do the ac, bd operations and stuff
		//TODO: call karatsuba??
		

		

		
		return h; //placeholder
	}
	
	public long karatsuba(HugeInteger this, HugeInteger h) {
		
		int maxlength = Math.max(this.intArr.length, h.intArr.length);
		
		if(maxlength<2) {
			return this.intArr[0] * h.intArr[0];
		}
		
		maxlength = (maxlength / 2) + (maxlength % 2);
		
//		long b = this >> maxlength;
//		long a = this - (b << maxlength);
//		long d = h >> maxlength;
//		long c = h - (d << maxlength);
//		
//		long ac = karatsuba(a,c);
//		long bd = karatsuba(b,d);
//		long abcd = karatsuba(a + b,c + d);
		
		
		return 5; // ac + (abcd - ac - bd << m) + (bd << 2 * m);
	}
		
	public int compareTo(HugeInteger h) {
		
		//returns -1 if this < h
		//returns 1 if this > h
		//returns 0 if this == h
		
		//first compare signs
		if(this.negative == false && h.negative == true) {
			return 1;
		}
		else if(this.negative == true && h.negative == false) {
			return -1;
		}
		else if(this.negative == false && h.negative == false) {
			//now compare lengths 
			if(this.intArr.length < h.intArr.length){
				return -1;
			}
			else if(this.intArr.length > h.intArr.length) {
				return 1;
			}
			else { //this.intArr.length == h.intArr.length
				//they are same length, check the magnitudes
				for(int i=this.intArr.length-1; i>=0; i--) {
					if(this.intArr[i] < h.intArr[i]) {
						return -1;
					}
					else if(this.intArr[i] > h.intArr[i]) {
						return 1;
					}
					else {
						;
					}
				}
			}
		}
		else { //both negative
			//now compare lengths 
			if(this.intArr.length < h.intArr.length){
				return 1;
			}
			else if(this.intArr.length > h.intArr.length) {
				return -1;
			}
			else {
				//they are same length, check the magnitudes
				for(int i=this.intArr.length-1; i>=0; i--) {
					if(this.intArr[i] < h.intArr[i]) {
						return 1;
					}
					else if(this.intArr[i] > h.intArr[i]) {
						return -1;
					}
					else {
						;
					}
				}
			}
		}
		return 0;
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
	
	//additional method, get negative since its private
	public Boolean getNegative() {
		if(this.negative == true) {
			return true;
		}
		else {
			return false;
		}
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
//		String strInput = "9999";
//		
//		HugeInteger hugey = new HugeInteger(strInput);
//		
//		System.out.println(hugey.negative);
//		System.out.println("heres the toString");
//		System.out.println(hugey.toString());
//		
//		
//		String bruv = "9999";
//		
//		HugeInteger ayy = new HugeInteger(bruv);
//		
//		System.out.println(ayy.negative);
//		System.out.println("heres the toString");
//		System.out.println(ayy.toString());
//		
//		
//		//test int constructor
//		
//		int intInput = 4;
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
//		HugeInteger Hugebruh = hugey.add(ayy);
//		System.out.println("heres the toString three");
//		System.out.println(Hugebruh.toString());
		
		
	}

}
