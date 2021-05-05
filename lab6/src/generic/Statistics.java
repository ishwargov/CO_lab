package generic;

import java.io.PrintWriter;

public class Statistics {
	
	// TODO add your statistics here
	static int numberOfInstructions;
	static int numberOfCycles;
	static int datastalls;
	static int branchstalls;
	

	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);
			
			writer.println("Number of instructions executed = " + numberOfInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			writer.println("Number of data hazard stalls = " + datastalls);
			writer.println("Number of branch hazard stalls = " + branchstalls);
			// TODO add code here to print statistics in the output file
			
			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}
	
	// TODO write functions to update statistics
	public static void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}

	public static void setNumberOfCycles(int num) {
		Statistics.numberOfCycles = num;
	}
	public static void setdatastalls(int num) {
		Statistics.datastalls = num;
	}
	public static int getdatastalls() {
		return Statistics.datastalls;
	}
	public static int getbranchstalls() {
		return Statistics.branchstalls;
	}
	public static int get_inst() {
		return Statistics.numberOfInstructions;
	}
	public static void setbranchstalls(int num) {
		Statistics.branchstalls = num;
	}
}
