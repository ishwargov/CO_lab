package generic;

import processor.Clock;
import processor.Processor;
import processor.memorysystem.MainMemory;
import processor.pipeline.RegisterFile;
import java.io.*;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p) throws FileNotFoundException,IOException
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		
		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile) throws FileNotFoundException,IOException
	{
		/*
		 * TODO
		 * 1. load the program into memory according to the program layout described
		 *    in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 *     x0 = 0
		 *     x1 = 65535
		 *     x2 = 65535
		 */
		DataInputStream infile = new DataInputStream(new FileInputStream(assemblyProgramFile));
		MainMemory main_mem = new MainMemory();
		int i=0;
		int pc=infile.readInt();
		for(i=0;i<pc;i++){
			main_mem.setWord(i,infile.readInt());
		}
		try{
			while(true){
				main_mem.setWord(i,infile.readInt());
				i++;
			}
		}catch (EOFException ignored){}
		infile.close();
		processor.setMainMemory(main_mem);
		RegisterFile reg = new RegisterFile();
		reg.setProgramCounter(pc);
		reg.setValue(1,65535);
		reg.setValue(2,65535);
		processor.setRegisterFile(reg);

	}
	
	public static void simulate()
	{	
		int num_inst = 0;
		while(simulationComplete == false)
		{
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			processor.getEXUnit().performEX();
			Clock.incrementClock();
			processor.getMAUnit().performMA();
			Clock.incrementClock();
			processor.getRWUnit().performRW();
			Clock.incrementClock();
			num_inst++;
		}
		
		// TODO
		// set statistics
		//Statistics.setNumberOfInstructions(num_inst);
		//Statistics.setNumberOfCycles(Clock.getCurrentTime());
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
