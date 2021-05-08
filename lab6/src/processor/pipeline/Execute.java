package processor.pipeline;

import processor.Processor;
import generic.*;
import configuration.Configuration;
import processor.Clock;

public class Execute implements Element {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	IF_OF_LatchType IF_OF_Latch;
	int res,rd,opcode;
	long latency;
	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch,IF_OF_LatchType iF_OF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		this.IF_OF_Latch = iF_OF_Latch;
		res = 0;
	}
	
	public void performEX()
	{
		//TODO
		EX_MA_Latch.set_stall(OF_EX_Latch.get_stall());
		OF_EX_Latch.setEX_busy(EX_MA_Latch.isMA_busy());
		if(OF_EX_Latch.isEX_enable()&&OF_EX_Latch.get_stall()){

			if(OF_EX_Latch.isEX_busy()){
				return;
			}

			opcode=OF_EX_Latch.get_opcode();
			System.out.printf("EX %d\n",opcode);
			switch(opcode){
				case 0:
					res=OF_EX_Latch.get_rs1()+OF_EX_Latch.get_rs2();
					break;
				case 1:
					res=OF_EX_Latch.get_rs1()+OF_EX_Latch.get_imm();
					break;
				case 2:
					res=OF_EX_Latch.get_rs1()-OF_EX_Latch.get_rs2();
					break;
				case 3:
					res=OF_EX_Latch.get_rs1()-OF_EX_Latch.get_imm();
					break;
				case 4:
					res=OF_EX_Latch.get_rs1()*OF_EX_Latch.get_rs2();
					break;
				case 5:
					res=OF_EX_Latch.get_rs1()*OF_EX_Latch.get_imm();
					break;
				case 6:
					res=OF_EX_Latch.get_rs1()/OF_EX_Latch.get_rs2();
					break;
				case 7:
					res=OF_EX_Latch.get_rs1()/OF_EX_Latch.get_imm();
					break;
				case 8:
					res=OF_EX_Latch.get_rs1()&OF_EX_Latch.get_rs2();
					break;
				case 9:
					res=OF_EX_Latch.get_rs1()&OF_EX_Latch.get_imm();
					break;
				case 10:
					res=OF_EX_Latch.get_rs1()|OF_EX_Latch.get_rs2();
					break;
				case 11:
					res=OF_EX_Latch.get_rs1()|OF_EX_Latch.get_imm();
					break;
				case 12:
					res=OF_EX_Latch.get_rs1()^OF_EX_Latch.get_rs2();
					break;
				case 13:
					res=OF_EX_Latch.get_rs1()^OF_EX_Latch.get_imm();
					break;
				case 14:
					res=(OF_EX_Latch.get_rs1()<OF_EX_Latch.get_rs2())?1:0;
					break;
				case 15:
					res=(OF_EX_Latch.get_rs1()<OF_EX_Latch.get_imm())?1:0;
					break;
				case 16:
					res=OF_EX_Latch.get_rs1()<<OF_EX_Latch.get_rs2();
					break;
				case 17:
					res=OF_EX_Latch.get_rs1()<<OF_EX_Latch.get_imm();
					break;
				case 18:
					res=OF_EX_Latch.get_rs1()>>>OF_EX_Latch.get_rs2();
					break;
				case 19:
					res=OF_EX_Latch.get_rs1()>>>OF_EX_Latch.get_imm();
					break;
				case 20:
					res=OF_EX_Latch.get_rs1()>>OF_EX_Latch.get_rs2();
					break;
				case 21:
					res=OF_EX_Latch.get_rs1()>>OF_EX_Latch.get_imm();
					break;
				case 22:
					res=OF_EX_Latch.get_rs1()+OF_EX_Latch.get_imm();
					break;
				case 23:
					res=OF_EX_Latch.get_rd()+OF_EX_Latch.get_imm();
					break;
				case 24:
					//res=OF_EX_Latch.get_rd()+OF_EX_Latch.get_imm();
					OF_EX_Latch.set_stall(false);
					IF_OF_Latch.set_stall(false);
					res = -1;
					Simulator.getEventQueue().delete_IF();
					//System.out.println("branch_stall");
					Statistics.setbranchstalls(Statistics.getbranchstalls()+1);
					containingProcessor.getRegisterFile().setProgramCounter((OF_EX_Latch.get_pc()+OF_EX_Latch.get_rd()+OF_EX_Latch.get_imm())%(1<<22));
					break;
				case 25:
					//System.out.printf("%d %d",OF_EX_Latch.get_rd(),OF_EX_Latch.get_rs1());
					if(OF_EX_Latch.get_rd()==OF_EX_Latch.get_rs1()){
						OF_EX_Latch.set_stall(false);
						IF_OF_Latch.set_stall(false);
						Simulator.getEventQueue().delete_IF();
						res = -1;
						//System.out.println("branch_stall");
						Statistics.setbranchstalls(Statistics.getbranchstalls()+1);
						containingProcessor.getRegisterFile().setProgramCounter(OF_EX_Latch.get_pc()+OF_EX_Latch.get_imm());
					}
					break;
				case 26:
					if(OF_EX_Latch.get_rd()!=OF_EX_Latch.get_rs1()){
						OF_EX_Latch.set_stall(false);
						IF_OF_Latch.set_stall(false);
						Simulator.getEventQueue().delete_IF();
						res = -1;
						//System.out.println("branch_stall");
						Statistics.setbranchstalls(Statistics.getbranchstalls()+1);
						containingProcessor.getRegisterFile().setProgramCounter(OF_EX_Latch.get_pc()+OF_EX_Latch.get_imm());
					}
					break;
				case 27:
					if(OF_EX_Latch.get_rd()>OF_EX_Latch.get_rs1()){
						OF_EX_Latch.set_stall(false);
						IF_OF_Latch.set_stall(false);
						Simulator.getEventQueue().delete_IF();
						res = -1;
						//System.out.println("branch_stall");
						Statistics.setbranchstalls(Statistics.getbranchstalls()+1);
						containingProcessor.getRegisterFile().setProgramCounter(OF_EX_Latch.get_pc()+OF_EX_Latch.get_imm());
					}
					break;
				case 28:
					if(OF_EX_Latch.get_rd()<OF_EX_Latch.get_rs1()){
						OF_EX_Latch.set_stall(false);
						IF_OF_Latch.set_stall(false);
						Simulator.getEventQueue().delete_IF();
						res = -1;
						//System.out.println("branch_stall");
						Statistics.setbranchstalls(Statistics.getbranchstalls()+1);
						containingProcessor.getRegisterFile().setProgramCounter(OF_EX_Latch.get_pc()+OF_EX_Latch.get_imm());
					}
					break;
				case 29:
					//end
					break;			
			}
			rd = OF_EX_Latch.get_rd();
			if(opcode<22){
				latency = Configuration.ALU_latency;
				if(opcode>=4&&opcode<=5){
					latency = Configuration.multiplier_latency;
				}
				else if(opcode>=6&&opcode<=7){
					latency = Configuration.divider_latency;
				}
				Simulator.getEventQueue().addEvent(new ExecutionCompleteEvent(Clock.getCurrentTime()+latency,this,this));
				OF_EX_Latch.setEX_busy(true);
				return;
			}
			if(opcode==23){
				EX_MA_Latch.set_rd(OF_EX_Latch.get_rs1());
			}
			else{
				EX_MA_Latch.set_rd(rd);
			}

			EX_MA_Latch.set_pc(OF_EX_Latch.get_pc());

			if(opcode>=24 && opcode<=28){
				EX_IF_Latch.setIF_enable(true);
				EX_MA_Latch.setMA_enable(true);
			}
			else{
				EX_MA_Latch.setMA_enable(true);
			}
			EX_MA_Latch.set_res(res);
			EX_MA_Latch.set_opcode(opcode);

			OF_EX_Latch.setEX_enable(false);
		}

	}
	@Override
	public void handleEvent(Event e) {
		if(EX_MA_Latch.isMA_busy()){
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		}
		else{
			ExecutionCompleteEvent event = (ExecutionCompleteEvent) e;
			System.out.printf("ALU event done \n");

			EX_MA_Latch.set_res(res);
			EX_MA_Latch.set_rd(rd);
			EX_MA_Latch.set_opcode(opcode);

			EX_MA_Latch.setMA_enable(true);
			OF_EX_Latch.setEX_enable(false);
			OF_EX_Latch.setEX_busy(false);
		}
	}

}
