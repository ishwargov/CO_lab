package processor.pipeline;

import processor.Processor;
import processor.memorysystem.MainMemory;
import generic.*;
import processor.Clock;
import configuration.Configuration;

public class MemoryAccess implements Element{
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	IF_OF_LatchType IF_OF_Latch;

	int rd,res,opcode;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch,IF_OF_LatchType iF_OF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_OF_Latch = iF_OF_Latch;
	}
	
	public void performMA()
	{
		//TODO
		MA_RW_Latch.set_stall(EX_MA_Latch.get_stall());
		if(EX_MA_Latch.isMA_enable()&&EX_MA_Latch.get_stall()){
			//System.out.println("MA ");
			if(EX_MA_Latch.isMA_busy())
			{
				return;
			}
			//System.out.println("MA not busy");
			rd=EX_MA_Latch.get_rd();
			res=EX_MA_Latch.get_res();
			opcode=EX_MA_Latch.get_opcode();

			if(opcode==22||opcode==23){
				MainMemory mainMemory=containingProcessor.getMainMemory();
				if(opcode==22){
					System.out.printf("Load MA addr : %d\n",res);
					Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency,this,containingProcessor.getMainMemory(),res));
					EX_MA_Latch.setMA_busy(true);
					return;
				}
				if(opcode==23){
					System.out.printf("store %d  %d\n",res,rd);
					Simulator.getEventQueue().addEvent(new MemoryWriteEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency ,this,containingProcessor.getMainMemory(),res,rd));
					EX_MA_Latch.setMA_busy(true);
					return;
				}
			}
			if(opcode>=23&&opcode<29&&res==-1){
				IF_OF_Latch.set_stall(true);
			}
			MA_RW_Latch.set_rd(rd);
			EX_MA_Latch.set_rd(-2);
			MA_RW_Latch.set_res(res);
			MA_RW_Latch.set_opcode(opcode);
			EX_MA_Latch.setMA_enable(false);
			MA_RW_Latch.setRW_enable(true);
		}
	}
	@Override
	public void handleEvent(Event e) {
		/*if(EX_MA_Latch.isMA_busy()){
			//System.out.println("MA event incremented");
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		}
		else{*/
			MemoryResponseEvent event = (MemoryResponseEvent) e;

			//System.out.println("MA event done");
			MA_RW_Latch.set_rd(rd);
			EX_MA_Latch.set_rd(-2);
			MA_RW_Latch.set_res(event.getValue());
			MA_RW_Latch.set_opcode(opcode);
			EX_MA_Latch.setMA_enable(false);
			EX_MA_Latch.setMA_busy(false);
			MA_RW_Latch.setRW_enable(true);
			
		//}
	}
	//(IF,enable,stall,busy)
}
