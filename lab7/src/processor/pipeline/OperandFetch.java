package processor.pipeline;

import processor.Processor;
import generic.Simulator;
import generic.Statistics;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	IF_EnableLatchType IF_EnableLatch;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch,IF_EnableLatchType iF_EnableLatch,EX_MA_LatchType eX_MA_Latch,MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
	}
	
	public void performOF()
	{	if(!IF_EnableLatch.isIF_busy())
			OF_EX_Latch.set_stall(IF_OF_Latch.get_stall());
		IF_OF_Latch.setOF_busy(OF_EX_Latch.isEX_busy());
		if(IF_OF_Latch.isOF_enable() && IF_OF_Latch.get_stall())
		{
			//TODO
			int inst = this.IF_OF_Latch.getInstruction();
			int opcode = inst>>>27;
			//System.out.printf("OF %d\n",opcode);
			//System.out.printf("%d ",opcode);
			int rd1 = EX_MA_Latch.get_rd();
			int rd2 = MA_RW_Latch.get_rd();
			int opcode1 = EX_MA_Latch.get_opcode();
			int opcode2 = MA_RW_Latch.get_opcode();
			if(!(opcode1>=0&&opcode1<=22))
				rd1 = -1;
			if(!(opcode2>=0&&opcode2<=22))
				rd2 = -1;
			if(((inst<<5)>>>27)==rd1 && EX_MA_Latch.get_stall()|| ((inst<<5)>>>27)==rd2 && MA_RW_Latch.get_stall() || ((inst<<10)>>>27) == rd1 && EX_MA_Latch.get_stall()  || ((inst<<10)>>>27) == rd2 && MA_RW_Latch.get_stall() ){
				//System.out.printf("rd1 = %d, rd2 = %d 1:%b,2:%b,3:%b,4:%b \n",rd1,rd2,((inst<<5)>>>27)==rd1 && EX_MA_Latch.get_stall(), ((inst<<5)>>>27)==rd2 && MA_RW_Latch.get_stall() , ((inst<<10)>>>27) == rd1 && EX_MA_Latch.get_stall(),((inst<<10)>>>27) == rd2 && MA_RW_Latch.get_stall());
				//System.out.println("data stalled\n");
				Statistics.setdatastalls(Statistics.getdatastalls()+1);
				IF_EnableLatch.set_stall(false);
				IF_OF_Latch.set_stall(false);
				OF_EX_Latch.set_stall(false);
			}
			else{
				OF_EX_Latch.set_stall(true);
				if(opcode>=0&&opcode<=21){
					if(opcode%2==0){ //add sub mul
						OF_EX_Latch.set_instype(0); 
						OF_EX_Latch.set_rs1(containingProcessor.getRegisterFile().getValue(((inst<<5)>>>27)));
						OF_EX_Latch.set_rs2(containingProcessor.getRegisterFile().getValue(((inst<<10)>>>27)));
						OF_EX_Latch.set_rd((inst<<15)>>>27);
						OF_EX_Latch.set_opcode(opcode);
					}
					else{	// addi subi muli
						OF_EX_Latch.set_instype(1); 
						OF_EX_Latch.set_rs1(containingProcessor.getRegisterFile().getValue(((inst<<5)>>>27)));
						OF_EX_Latch.set_rd((inst<<10)>>>27);
						OF_EX_Latch.set_imm((inst<<15)>>15);
						OF_EX_Latch.set_opcode(opcode);
					}
				}
				else if(opcode==24||opcode==29){ // end jmp
					OF_EX_Latch.set_instype(2);
					OF_EX_Latch.set_rd(containingProcessor.getRegisterFile().getValue(((inst<<5)>>>27)));
					OF_EX_Latch.set_imm((inst<<10)>>10);
					OF_EX_Latch.set_opcode(opcode);
				}
				else if(opcode==22){	//load
					OF_EX_Latch.set_instype(1);
					OF_EX_Latch.set_rs1(containingProcessor.getRegisterFile().getValue(((inst<<5)>>>27)));
					OF_EX_Latch.set_rd((inst<<10)>>>27);
					OF_EX_Latch.set_imm((inst<<15)>>15);
					OF_EX_Latch.set_opcode(opcode);
				}
				else{	//bgt beq store
					OF_EX_Latch.set_instype(1);
					OF_EX_Latch.set_rs1(containingProcessor.getRegisterFile().getValue(((inst<<5)>>>27)));
					OF_EX_Latch.set_rd(containingProcessor.getRegisterFile().getValue(((inst<<10)>>>27)));
					OF_EX_Latch.set_imm((inst<<15)>>15);
					OF_EX_Latch.set_opcode(opcode);
				}
				OF_EX_Latch.set_pc(containingProcessor.getRegisterFile().getProgramCounter()-1);
				IF_OF_Latch.setOF_enable(false);
				OF_EX_Latch.setEX_enable(true);
			}
		}
	}

}
