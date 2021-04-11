package processor.pipeline;

import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			//TODO
			int inst = this.IF_OF_Latch.getInstruction();
			int opcode = inst>>>27;
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
					OF_EX_Latch.set_imm((inst<<15)>>>15);
					OF_EX_Latch.set_opcode(opcode);
				}
			}
			else if(opcode==24||opcode==29){ // end jmp
				OF_EX_Latch.set_instype(2);
				OF_EX_Latch.set_rd(containingProcessor.getRegisterFile().getValue(((inst<<5)>>>27)));
				OF_EX_Latch.set_imm((inst<<10)>>>10);
				OF_EX_Latch.set_opcode(opcode);
			}
			else if(opcode==23){	//store
				OF_EX_Latch.set_instype(1);
				OF_EX_Latch.set_rs1(containingProcessor.getRegisterFile().getValue(((inst<<5)>>>27)));
				OF_EX_Latch.set_rd(containingProcessor.getRegisterFile().getValue(((inst<<10)>>>27)));
				OF_EX_Latch.set_imm((inst<<15)>>>15);
				OF_EX_Latch.set_opcode(opcode);
			}
			else{	//bgt beq load
				OF_EX_Latch.set_instype(1);
				OF_EX_Latch.set_rs1(containingProcessor.getRegisterFile().getValue(((inst<<5)>>>27)));
				OF_EX_Latch.set_rd((inst<<10)>>>27);
				OF_EX_Latch.set_imm((inst<<15)>>>15);
				OF_EX_Latch.set_opcode(opcode);
			}
			OF_EX_Latch.set_pc(containingProcessor.getRegisterFile().getProgramCounter()-1);
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}
	}

}
