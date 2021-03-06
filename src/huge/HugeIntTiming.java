package huge;

import java.math.BigInteger;
import java.util.Random;

public class HugeIntTiming {
	
	public static void main(String[] args) {
		
		//TODO: tune maxruns so that total times elapsed is at least 500
		
		int[] nArray = {10,22,47,100,220,470,1000,2200,4700}; //various lengths
		int MAXNUMINTS = 100; //how many sets of largeintegers to make
		//MAXRUN (number of runs per set) will be decided per each operation
		
		//test huge add
		for(int i=0; i<nArray.length; i++) {
			int MAXRUN = 30; 
			HugeInteger huge1;
			HugeInteger huge2;
			HugeInteger huge3;
			long startTime;
			long endTime;
			double runTime = 0.0;
			for(int numInts = 0; numInts < MAXNUMINTS; numInts++){
				huge1 = new HugeInteger(nArray[i]); //creates random integer of n digits
				huge2 = new HugeInteger(nArray[i]); //creates random integer of n digits
				startTime = System.currentTimeMillis();
				for(int numRun=0; numRun < MAXRUN; numRun++){
					huge3 = huge1.add(huge2);
				}
				endTime = System.currentTimeMillis();
				runTime +=(double) (endTime - startTime)/((double) MAXRUN);
			}
			System.out.println("At n= " + nArray[i] + "Huge add total time elapsed: " + runTime);
			runTime = runTime/((double)MAXNUMINTS);
			System.out.println("At n= " + nArray[i] + "Huge add time per run: " + runTime);
		}
			
		//test huge subtract
		for(int i=0; i<nArray.length; i++) {
			int MAXRUN = 30; 
			HugeInteger huge1;
			HugeInteger huge2;
			HugeInteger huge3;
			long startTime;
			long endTime;
			double runTime = 0.0;
			for(int numInts = 0; numInts < MAXNUMINTS; numInts++){
				huge1 = new HugeInteger(nArray[i]); //creates random integer of n digits
				huge2 = new HugeInteger(nArray[i]); //creates random integer of n digits
				startTime = System.currentTimeMillis();
				for(int numRun=0; numRun < MAXRUN; numRun++){
					huge3 = huge1.subtract(huge2);
				}
				endTime = System.currentTimeMillis();
				runTime +=(double) (endTime - startTime)/((double) MAXRUN);
			}
			System.out.println("At n= " + nArray[i] + "Huge subtract total time elapsed: " + runTime);
			runTime = runTime/((double)MAXNUMINTS);
			System.out.println("At n= " + nArray[i] + "Huge subtract time per run: " + runTime);
		}
		
		//test huge multiply
		for(int i=0; i<nArray.length; i++) {
			int MAXRUN = 1; 
			HugeInteger huge1;
			HugeInteger huge2;
			HugeInteger huge3;
			long startTime;
			long endTime;
			double runTime = 0.0;
			for(int numInts = 0; numInts < MAXNUMINTS; numInts++){
				huge1 = new HugeInteger(nArray[i]); //creates random integer of n digits
				huge2 = new HugeInteger(nArray[i]); //creates random integer of n digits
				startTime = System.currentTimeMillis();
				for(int numRun=0; numRun < MAXRUN; numRun++){
					huge3 = huge1.multiply(huge2);
				}
				endTime = System.currentTimeMillis();
				runTime +=(double) (endTime - startTime)/((double) MAXRUN);
			}
			System.out.println("At n= " + nArray[i] + "Huge multiply total time elapsed: " + runTime);
			runTime = runTime/((double)MAXNUMINTS);
			System.out.println("At n= " + nArray[i] + "Huge multiply time per run: " + runTime);
		}
		
		//test huge compare
		for(int i=0; i<nArray.length; i++) {
			int MAXRUN = 5000000; 
			HugeInteger huge1;
			HugeInteger huge2;
			int answer = 0;
			long startTime;
			long endTime;
			double runTime = 0.0;
			for(int numInts = 0; numInts < MAXNUMINTS; numInts++){
				huge1 = new HugeInteger(nArray[i]); //creates random integer of n digits
				huge2 = new HugeInteger(nArray[i]); //creates random integer of n digits
				startTime = System.currentTimeMillis();
				for(int numRun=0; numRun < MAXRUN; numRun++){
					answer = huge1.compareTo(huge2);
				}
				endTime = System.currentTimeMillis();
				runTime +=(double) (endTime - startTime)/((double) MAXRUN);
			}
			System.out.println("At n= " + nArray[i] + "Huge compareTo total time elapsed: " + runTime);
			runTime = runTime/((double)MAXNUMINTS);
			System.out.println("At n= " + nArray[i] + "Huge compareTo time per run: " + runTime);
		}
			
			
		//test big add
		for(int i=0; i<nArray.length; i++) {
			long MAXRUN = 100000;
			BigInteger big1;
			BigInteger big2;
			BigInteger big3;
			long startTime;
			long endTime;
			double runTime = 0.0;
			Random gen = new Random();
			for(int numInts = 0; numInts < MAXNUMINTS; numInts++){
				big1 = new BigInteger(nArray[i], gen);//creates random biginteger between 0 to (n2^2)-1. 
				big2 = new BigInteger(nArray[i], gen);//can even be a low number like 12
				startTime = System.currentTimeMillis();
				for(int numRun=0; numRun < MAXRUN; numRun++){
					big3 = big1.add(big2);
				}
				endTime = System.currentTimeMillis();
				runTime +=(double) (endTime - startTime)/((double) MAXRUN);
			}
			System.out.println("At n= " + nArray[i] + "Big add total time elapsed" + runTime);
			runTime = runTime/((double)MAXNUMINTS);
			System.out.println("At n= " + nArray[i] + "Big add time per run: " + runTime);
		}
		
		//test big subtract
		for(int i=0; i<nArray.length; i++) {
			long MAXRUN = 100000;
			BigInteger big1;
			BigInteger big2;
			BigInteger big3;
			long startTime;
			long endTime;
			double runTime = 0.0;
			Random gen = new Random();
			for(int numInts = 0; numInts < MAXNUMINTS; numInts++){
				big1 = new BigInteger(nArray[i], gen);//creates random biginteger between 0 to (n2^2)-1. 
				big2 = new BigInteger(nArray[i], gen);//can even be a low number like 12
				startTime = System.currentTimeMillis();
				for(int numRun=0; numRun < MAXRUN; numRun++){
					big3 = big1.subtract(big2);
				}
				endTime = System.currentTimeMillis();
				runTime +=(double) (endTime - startTime)/((double) MAXRUN);
			}
			System.out.println("At n= " + nArray[i] + "Big subtract total time elapsed" + runTime);
			runTime = runTime/((double)MAXNUMINTS);
			System.out.println("At n= " + nArray[i] + "Big subtract time per run: " + runTime);
		}
		
		//test big multiply
		for(int i=0; i<nArray.length; i++) {
			long MAXRUN = 10000;
			BigInteger big1;
			BigInteger big2;
			BigInteger big3;
			long startTime;
			long endTime;
			double runTime = 0.0;
			Random gen = new Random();
			for(int numInts = 0; numInts < MAXNUMINTS; numInts++){
				big1 = new BigInteger(nArray[i], gen);//creates random biginteger between 0 to (n2^2)-1. 
				big2 = new BigInteger(nArray[i], gen);//can even be a low number like 12
				startTime = System.currentTimeMillis();
				for(int numRun=0; numRun < MAXRUN; numRun++){
					big3 = big1.multiply(big2);
				}
				endTime = System.currentTimeMillis();
				runTime +=(double) (endTime - startTime)/((double) MAXRUN);
			}
			System.out.println("At n= " + nArray[i] + "Big multiply total time elapsed" + runTime);
			runTime = runTime/((double)MAXNUMINTS);
			System.out.println("At n= " + nArray[i] + "Big multiply time per run: " + runTime);
		}
		
		//test big compare
		for(int i=0; i<nArray.length; i++) {
			long MAXRUN = 9000000;
			BigInteger big1;
			BigInteger big2;
			int answer = 0;
			long startTime;
			long endTime;
			double runTime = 0.0;
			Random gen = new Random();
			for(int numInts = 0; numInts < MAXNUMINTS; numInts++){
				big1 = new BigInteger(nArray[i], gen);//creates random biginteger between 0 to (n2^2)-1. 
				big2 = new BigInteger(nArray[i], gen);//can even be a low number like 12
				startTime = System.currentTimeMillis();
				for(int numRun=0; numRun < MAXRUN; numRun++){
					answer = big1.compareTo(big2);
				}
				endTime = System.currentTimeMillis();
				runTime +=(double) (endTime - startTime)/((double) MAXRUN);
			}
			System.out.println("At n= " + nArray[i] + "Big compareTo total time elapsed" + runTime);
			runTime = runTime/((double)MAXNUMINTS);
			System.out.println("At n= " + nArray[i] + "Big compareTo time per run: " + runTime);
		}


		

	}

}
