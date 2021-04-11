package processor.pipeline;

public class MA_RW_LatchType {
	
	boolean RW_enable;
	int res, rd,opcode;

	public MA_RW_LatchType()
	{
		RW_enable = false;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}
	public void set_rd(int rd){
		this.rd=rd;
	}
	public int get_rd(){
		return rd;
	}	
	public void set_res(int res){
		this.res=res;
	}
	public int get_res(){
		return res;
	}	
	public void set_opcode(int opcode){
		this.opcode=opcode;
	}
	public int get_opcode(){
		return opcode;
	}
}
