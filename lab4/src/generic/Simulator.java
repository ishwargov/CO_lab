package generic;

import processor.Clock;
import processor.Processor;
import processor.*;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		
		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile)
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
		int pc;
		pc = infile.readInt();
		for(i=0;i<pc;i++){
			main_mem.set_word(i,infile.readInt())
		}
		try{
			while(1){
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

	}
	
	public static void simulate()
	{
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
		}
		
		// TODO
		// set statistics
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
