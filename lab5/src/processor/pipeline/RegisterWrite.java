package processor.pipeline;
import processor.pipeline.RegisterFile;
import org.graalvm.compiler.lir.Opcode;

import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch,IF_OF_LatchType iF_OF_Latch,OF_EX_LatchType oF_EX_Latch,EX_MA_LatchType eX_MA_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
	}
	
	public void performRW()
	{
		if(MA_RW_Latch.isRW_enable())
		{	
			//TODO
			//System.out.println("RW ");
			// if instruction being processed is an end instruction, remember to call Simulator.setSimulationComplete(true);
			int opcode=MA_RW_Latch.get_opcode();
			int res=MA_RW_Latch.get_res();
			int rd=MA_RW_Latch.get_rd();
			if(opcode==29){
				IF_EnableLatch.setIF_enable(false);
				MA_RW_Latch.setRW_enable(false);
				Simulator.setSimulationComplete(true);
				//System.out.println("Program Ended");
			}
			if(opcode<=22){
				RegisterFile registerFile=containingProcessor.getRegisterFile();
				registerFile.setValue(rd, res);
				containingProcessor.setRegisterFile(registerFile);
			}
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
			
			IF_OF_Latch.setOF_Enable(true);
		}
	}

}
