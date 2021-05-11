package processor.pipeline;
import processor.pipeline.RegisterFile;
import org.graalvm.compiler.lir.Opcode;

import generic.Simulator;
import generic.Statistics;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch,IF_OF_LatchType iF_OF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
	}
	
	public void performRW()
	{	
		if(MA_RW_Latch.isRW_enable()&&MA_RW_Latch.get_stall())
		{	
			//TODO
			int opcode=MA_RW_Latch.get_opcode();
			Statistics.setNumberOfInstructions(Statistics.get_inst()+1);
			// if instruction being processed is an end instruction, remember to call Simulator.setSimulationComplete(true);
			int res=MA_RW_Latch.get_res();
			int rd=MA_RW_Latch.get_rd();
			System.out.printf("RW %d\n",opcode);
			if(opcode==29){
				IF_EnableLatch.setIF_enable(false);
				MA_RW_Latch.setRW_enable(false);
				Simulator.setSimulationComplete(true);
				System.out.println("Program Ended");
			}
			if(opcode<=22){
				RegisterFile registerFile=containingProcessor.getRegisterFile();
				registerFile.setValue(rd, res);
				containingProcessor.setRegisterFile(registerFile);
			}
			MA_RW_Latch.setRW_enable(false);
			MA_RW_Latch.set_rd(-2);
			IF_EnableLatch.setIF_enable(true);
			//System.out.printf("opcode : %d\n",opcode);
			//containingProcessor.printState(0,8);
		}
		else if(!MA_RW_Latch.get_stall()){
			IF_EnableLatch.set_stall(true);
			IF_EnableLatch.setIF_enable(true);
			IF_OF_Latch.set_stall(true);
		}
		
		IF_EnableLatch.setIF_enable(true);
	}

}
