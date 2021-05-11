package processor.pipeline;

import processor.Processor;
import configuration.Configuration;
import generic.*;
import processor.Clock;

public class InstructionFetch implements Element{
	
	Processor containingProcessor;
	public IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performIF()
	{	//IF_OF_Latch.set_stall(IFEnable_Latch.get_stall());
		//IF_EnableLatch.setIF_busy(IF_OF_Latch.isOF_busy());
		if(IF_EnableLatch.isIF_enable() && IF_EnableLatch.get_stall())
		{	
			if(IF_EnableLatch.isIF_busy())
			{
				return;
			}
			System.out.println("IF ");
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime() + Configuration.L1i_latency,this,containingProcessor.getL1i(),containingProcessor.getRegisterFile().getProgramCounter()));
			
			IF_EnableLatch.setIF_busy(true);

			//int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
			//IF_OF_Latch.setInstruction(newInstruction);
			
			
			//IF_OF_Latch.setOF_enable(true);
		}
	}
	@Override
	public void handleEvent(Event e) {
		if(IF_OF_Latch.isOF_busy()||!IF_OF_Latch.get_stall()||!IF_OF_Latch.get_completed()){
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		}
		else{
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			IF_OF_Latch.setInstruction(event.getValue());
			System.out.printf("IF event done %d\n",event.getValue()>>>27);
			IF_OF_Latch.setOF_enable(true);
			IF_OF_Latch.set_completed(false);
			//IF_EnableLatch.setIF_enable(false);
			IF_EnableLatch.setIF_busy(false);
		}
	}

}
