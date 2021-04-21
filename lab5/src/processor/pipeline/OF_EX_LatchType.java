package processor.pipeline;

public class OF_EX_LatchType {
	
	boolean EX_enable,stall;
	int rs1,rs2,rd,imm,pc,type,opcode;
	
	public OF_EX_LatchType()
	{
		EX_enable = false;
		stall = true;
		rd = -1;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}
	public boolean get_stall() {
		return stall;
	}
	public void set_stall(boolean iF_enable) {
		stall = iF_enable;
	}
	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}
	public void set_instype(int op){
		this.type = op;
	}
	public int get_instype(int op){
		return type;
	}
	public void set_rs1(int op){
		this.rs1 = op;
	}
	public void set_rs2(int op){
		this.rs2 = op;
	}
	public void set_rd(int op){
		this.rd = op;
	}
	public void set_imm(int op){
		this.imm = op;
	}
	public void set_pc(int op){
		this.pc = op;
	}
	public int get_imm(){
		return imm;
	}
	public int get_rs1(){
		return rs1;
	}
	public int get_rs2(){
		return rs2;
	}
	public int get_rd(){
		return rd;
	}
	public int get_pc(){
		return pc;
	}
	public void set_opcode(int op){
		this.opcode = op;
	}
	public int get_opcode(){
		return(opcode);
	}

}
