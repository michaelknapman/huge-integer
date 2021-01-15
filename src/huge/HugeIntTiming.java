package huge;

public class HugeIntTiming {

	
	
	
	
	
	
	
	public static void main(String[] args) {
		
		//use java.math.BigInteger class and HugeInteger class

		
		//for fixed n this program should instantiate random integers of size n
		//(n decimal digits)
		//then run each of the operations on the integers
		//measure the time required to perform each operation.
		
		//measuring the time requires System.currentTimeMillis()
		//run the operations many times on a pair of integers.
		//run at least 100 pairs of integers.
		
		//example code
		
		/*
		HugeInteger huge1, huge2, huge3;
		long startTime, endTime;
		double runTime = 0.0;
		for(int numInts = 0; numInts < MAXNUMINTS; numInts++){
			huge1 = new HugeInteger(n); //creates random integer of n digits
			huge2 = new HugeInteger(n); //creates random integer of n digits
			startTime = System.currentTimeMillis();
			for(int numRun=0; numRun < MAXRUN; numRun++){
				huge3 = huge1.add(huge2);
			}
			endTime = System.currentTimeMillis();
			runTime +=(double) (endTime - startTime)/((double) MAXRUN);
		}
		runTime = runTime/((double)MAXNUMINTS);
		...
		*/
		
		//for each of the operations, comparison
		//subtraction, addition, multiplication
		//measure the runnnig time for various values of n
		//eg n = 10, 100, 500, 1000, 5000, 10000
		
		
		
	}

}
