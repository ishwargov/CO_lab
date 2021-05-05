package generic;

import processor.Clock;
import processor.Processor;
import processor.memorysystem.MainMemory;
import processor.pipeline.RegisterFile;
import java.io.*;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	static int num_inst;
	static EventQueue eventQueue;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p) throws FileNotFoundException,IOException
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		num_inst = 0;
		simulationComplete = false;
		eventQueue = new EventQueue();
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
		while(simulationComplete == false){
			processor.getRWUnit().performRW();
			processor.getMAUnit().performMA();
			processor.getEXUnit().performEX();
			eventQueue.processEvents();
			processor.getOFUnit().performOF();
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			num_inst++;
		}
		
		// TODO
		// set statistics
		Statistics.setNumberOfCycles((int)Clock.getCurrentTime());
	}
	public static int get_num_inst(){
		return (num_inst);
	}
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
	public static EventQueue getEventQueue ()
	{
		return eventQueue ;
	}
}
