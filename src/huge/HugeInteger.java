package huge;

//import java.lang.Math;
//good for double bruh = Math.pow(2,5);

public class HugeInteger {

	//private int placeholder; //placeholder
	
	
	//constructors
	
	public HugeInteger(String val) {
		
		//used to create a hugeinteger from a decimal string
		//needs to account for minus sign.
		//must throw an exception if other characters used.
		
		//this.placeholder = stuff
		
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
