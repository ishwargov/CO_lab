package processor.pipeline;

import processor.Processor;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performEX()
	{
		//TODO
		int opcode=OF_EX_Latch.get_opcode();
		int res;
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
				res=OF_EX_Latch.get_rs1()<OF_EX_Latch.get_rs2();
				break;
			case 15:
				res=OF_EX_Latch.get_rs1()<OF_EX_Latch.get_imm();
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
				res=OF_EX_Latch.get_rs1()+OF_EX_Latch.get_imm();
				break;
			case 24:
				break;
			
		}
	}

}
