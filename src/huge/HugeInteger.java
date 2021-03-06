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
		
		//shouldnt do karatsuba if short, wikipedia recommends 10
		//long multiplication!
		if(this.intArr.length<10 || this.intArr.length<10) {
			
			//init. should be as long as this + h lengths.
			HugeInteger prod = new HugeInteger(0,false,this.intArr.length+h.intArr.length);

			int counter = 0;
			
			//use both an index and a counter
			for(int i=0; i<this.intArr.length; i++, counter++) {
				int carry = 0;
				//also init a hugeinteger to contain the thing you have to add together
				HugeInteger sub_prod = new HugeInteger(0,false,counter + h.intArr.length + 1);
				
				//now loop to create all of your things to add together.
				for(int j=0; j<h.intArr.length+1; j++) {
					int index_prod = carry;
					if(j<h.intArr.length) {
						index_prod += (this.intArr[i] * h.intArr[j]); 
					}
					if(index_prod >= 10) {
						carry = (index_prod - (index_prod%10))/10;
					}
					else {
						carry = 0;
					}
					//make it so that it isnt bigger than 9
					sub_prod.intArr[counter+j] = index_prod%10;
				}
				//sum together your sub prods to get your proper prod (unsanitized)
				prod = prod.add(sub_prod);			
			}
			
			//sanitize your prod into sanitized.... copy and paste from subtract lol
			
			//now copy to string
			String output = new String();
			for(int i=prod.intArr.length-1; i>=0; i--) {
				output = output + prod.intArr[i];
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
			if(this.negative==h.negative) {
				sanitized.negative=false;
			}
			else {
				sanitized.negative=true;
			}
			
			return sanitized;
			
		}
		
		//now do karatsuba from wikipedia
		int M = Math.max(this.intArr.length, h.intArr.length);
		//N is half the largest length, round up.
		int N = (M/2)+(M%2);
		
		//now find a and b and c and d for karatsuba
		//remember that...
		//if this is 5689
		//if h is 1234
		//a is 56
		//b is 78
		//c is 12
		//d is 34
		//in our arrays...
		//this = [8,7,6,5]
		//h = [4,3,2,1]
		//a = [6,5]
		//b = [8,7]
		//c = [2,1]
		//d = [4,3]
		
		//initialize b and a to use when this.length < h length and it is smaller
		//than half of h's length 
		//this is so that when you do length-N, there isnt an indexoutofbounds
		HugeInteger b = new HugeInteger(0,false,1);
		HugeInteger a = this;
		
		//turn b to be x/10^N when this's length > N
		//turn a to be x-b when this's length > N
		if(this.intArr.length-N > 0) {
			b = new HugeInteger(0,this.negative, this.intArr.length-N);
			for(int i=0; i<b.intArr.length; i++) {
				b.intArr[i] = this.intArr[N+i];
			}
			a = new HugeInteger(0,this.negative,N+1);
			for(int i=0; i<N; i++) {
				a.intArr[i] = this.intArr[i];
			}
		}
		
		//initialize d and c to use when h's length < this's length and 
		//it is < half of this's length
		HugeInteger d = new HugeInteger(0,false,1);
		HugeInteger c = h;
		
		//turn d to be x/10^NN when h's length > N
		//turn c to be x-b when h's length > N
		if(h.intArr.length-N > 0) {
			
			d = new HugeInteger(0,h.negative,h.intArr.length-N);
			for(int i=0; i<d.intArr.length; i++) {
				d.intArr[i] = h.intArr[N+i];
			}
			
			c = new HugeInteger(0,h.negative,N+1);
			for(int i=0; i<N; i++) {
				c.intArr[i] = h.intArr[i];
			}
		}
		
		//remove leading zeroes in a to get asanitized
		//again copypaste from subtract...
		
		String outputA = new String();
		for(int i=a.intArr.length-1; i>=0; i--) {
			outputA = outputA + a.intArr[i];
		}
		outputA = outputA.replaceFirst("^0+(?!$)","");
		HugeInteger aSanitized = new HugeInteger(0,a.negative,outputA.length());
		
		//copy string into a final array.
		//convert string to chars
		char[] charArrayA = new char[outputA.length()]; //initialize
		outputA.getChars(0, outputA.length(), charArrayA, 0);
		//now we can copy our stuff to a new sanitized HugeInteger
		aSanitized.intArr = new int[charArrayA.length]; //initialize
		for(int i=0; i < charArrayA.length; i++) {
			aSanitized.intArr[charArrayA.length-1-i] = charArrayA[i];
		}
		//Notice that the numbers are still ascii encoded,
		//eg 0 -> 48, 1 -> 49 etc.
		//shift everything down by 48.
		for(int i=0; i < aSanitized.intArr.length; i++) {
			aSanitized.intArr[i] = aSanitized.intArr[i] - 48;
		}

		//now do the same for c.
		
		String outputC = new String();
		for(int i=c.intArr.length-1; i>=0; i--) {
			outputC = outputC + c.intArr[i];
		}
		outputC = outputC.replaceFirst("^0+(?!$)","");
		HugeInteger cSanitized = new HugeInteger(0,c.negative,outputC.length());
		
		//copy string into a final array.
		//convert string to chars
		char[] charArrayC = new char[outputC.length()]; //initialize
		outputC.getChars(0, outputC.length(), charArrayC, 0);
		//now we can copy our stuff to a new sanitized HugeInteger
		cSanitized.intArr = new int[charArrayC.length]; //initialize
		for(int i=0; i < charArrayC.length; i++) {
			cSanitized.intArr[charArrayC.length-1-i] = charArrayC[i];
		}
		//Notice that the numbers are still ascii encoded,
		//eg 0 -> 48, 1 -> 49 etc.
		//shift everything down by 48.
		for(int i=0; i < cSanitized.intArr.length; i++) {
			cSanitized.intArr[i] = cSanitized.intArr[i] - 48;
		}
		
		//from wikipedia, calculate the terms and add them together, z notation
		//eqn is z = z0 + z1*10^N + z2*10^(2*N)
		
		//first term z0 = a*c
		HugeInteger z0 = aSanitized.multiply(cSanitized);
		
		//third term z2 = b*d
		HugeInteger z2 = b.multiply(d);
		
		//second term z1 = (a+b)*(b+d) - z0 - z2
		HugeInteger z1_sub1 = (aSanitized.add(b)).multiply(cSanitized.add(d));
		HugeInteger z1 = (z1_sub1.subtract(z0)).subtract(z2);
		
		//multiply by 10^N
		//pads with half a length worth of zeroes
		HugeInteger z1_term = new HugeInteger(0,z1.negative,z1.intArr.length+N);
		for(int i=0; i<z1.intArr.length;i++) {
			z1_term.intArr[N+i] = z1.intArr[i];
		}
		
		//multiply by 10^(N*2)
		//pads with a full length of zeroes//
		HugeInteger z2_term = new HugeInteger(0,z2.negative,z2.intArr.length+(N*2));
		for(int i=0; i<z2.intArr.length; i++) {
			z2_term.intArr[N*2+i] = z2.intArr[i];
		}
		
		//You need to catch any occasions where z2 is 0 
		//in this case the z2_term (which you will add) must be 0.
		if(z2.intArr.length == 1 && z2.intArr[0] == 0) {
			z2_term = z2;
		}
		
		//sum all terms
		HugeInteger z = (z0.add(z1_term)).add(z2_term);
		return z;
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
	
	//additional method, get negative since its private??
	public Boolean getNegative() {
		if(this.negative == true) {
			return true;
		}
		else {
			return false;
		}
	}
		
	
	public static void main(String[] args) {
		//empty because my testcases are elsewhere
	}

}
