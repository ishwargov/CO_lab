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
		if(OF_EX_Latch.isEX_enable()){
			//System.out.println("EX ");
			int opcode=OF_EX_Latch.get_opcode();
			int res=0;
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
					containingProcessor.getRegisterFile().setProgramCounter((OF_EX_Latch.get_pc()+OF_EX_Latch.get_rd()+OF_EX_Latch.get_imm())%(1<<22));
					break;
				case 25:
					//System.out.printf("%d %d",OF_EX_Latch.get_rd(),OF_EX_Latch.get_rs1());
					if(OF_EX_Latch.get_rd()==OF_EX_Latch.get_rs1()){
						containingProcessor.getRegisterFile().setProgramCounter(OF_EX_Latch.get_pc()+OF_EX_Latch.get_imm());
					}
					break;
				case 26:
					if(OF_EX_Latch.get_rd()!=OF_EX_Latch.get_rs1()){
						containingProcessor.getRegisterFile().setProgramCounter(OF_EX_Latch.get_pc()+OF_EX_Latch.get_imm());
					}
					break;
				case 27:
					if(OF_EX_Latch.get_rd()>OF_EX_Latch.get_rs1()){
						containingProcessor.getRegisterFile().setProgramCounter(OF_EX_Latch.get_pc()+OF_EX_Latch.get_imm());
					}
					break;
				case 28:
					if(OF_EX_Latch.get_rd()<OF_EX_Latch.get_rs1()){
						containingProcessor.getRegisterFile().setProgramCounter(OF_EX_Latch.get_pc()+OF_EX_Latch.get_imm());
					}
					break;
				case 29:
					//end
					break;			
			}
			EX_MA_Latch.set_res(res);
			if(opcode==23){
				EX_MA_Latch.set_rd(OF_EX_Latch.get_rs1());
			}
			else{	
				EX_MA_Latch.set_rd(OF_EX_Latch.get_rd());
			}
			EX_MA_Latch.set_pc(OF_EX_Latch.get_pc());
			EX_MA_Latch.set_opcode(opcode);
			if(opcode>=24 && opcode<=28){
				EX_IF_Latch.setIF_enable(true);
				EX_MA_Latch.setMA_enable(true);
			}
			else{
				EX_MA_Latch.setMA_enable(true);
			}
			OF_EX_Latch.setEX_enable(false);
		}
	}

}
