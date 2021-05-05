package processor.pipeline;

import processor.Processor;
import configuration.Configuration;
public class InstructionFetch {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
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
		if(IF_EnableLatch.isIF_enable() && IF_EnableLatch.get_stall())
		{	
			if(IF_EnableLatch.isIF_busy())
			{
				return;
			}
			//System.out.println("IF ");
			Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency ,this,containingProcessor.getMainMemory(),containingProcessor.getRegisterFile().getProgramCounter()));
			IF_EnableLatch.setIF_busy(true);


			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			//int newInstruction = containingProcessor.getMainMemory().getWord(currentPC);
			//IF_OF_Latch.setInstruction(newInstruction);
			
			
			//IF_OF_Latch.setOF_enable(true);
		}
	}
	@Override
	public void handleEvent(Event e) {
		if(IF_OF_Latch.isOF_busy()){
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		}
		else{
			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			IF_OF_Latch.setInstruction(event.getValue());
			IF_OF_Latch.setOF_enable(true);
			IF_EnableLatch.setIF_enable(false);
			IF_EnableLatch.setIF_busy(false);
		}
	}

}
