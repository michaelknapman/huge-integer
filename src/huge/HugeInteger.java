package huge;

//import java.lang.Math;
//good for double bruh = Math.pow(2,5);

public class HugeInteger {

	private byte[] NumArr; //array holding the hugeinteger
	private String[] StringArr; //intermediate characters.
	private boolean negative; //true if negative. false if positive.
	private int digits; //how many digits long
	

	//in order to implement hugemungus, we can probably just do a
	//phat array of integers 0-9.
	//the array should only be as long as the thing that you put in it
	//eg 2543 should make a 4 long array
	//eg "42" should make a 2 long array
	
	//but how do we handle negatives? 
	//probably should just have a flag if negative.
	//if - * + then - etc
	//also if the -ve has more digits than the +ve, output is -ve
	//etc
	
	//how to add?
	//loop from the right digit and move left
	//take note of any carry's 
	//keep on moving left
	
	
	//constructors
	public HugeInteger(String val) {
		
		//used to create a hugeinteger from a decimal string
		//needs to account for minus sign.
		//must throw an exception if other characters used.
		
		//what is length?
		this.digits = val.length(); //need to -1 for '\0'? idk
		
		//is it negative?
		//parse string for a -ve sign
		//this.negative = 0 if positive, = 1 if negative.
		this.negative = val.contains("-");
		
		//split string into chars
		//"" is a regex meaning split every character. 
		this.StringArr = val.split("");
		
		//now convert everything in StringArr to NumArr
		
		
		
	}
	
	
	public HugeInteger(int n) {
		
		//used to create a hugeinteger from integer n
		
		//this.placeholder = stuff
		
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

		
		System.out.println("hello world");
		
		int VirginInt = 3+4;
		
		int ChadInt = 2*4;
		
		System.out.println(VirginInt);
		System.out.println(ChadInt);

		
		
	}

}
