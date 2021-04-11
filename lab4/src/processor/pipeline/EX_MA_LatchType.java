package processor.pipeline;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	int rd,pc,res;
	
	public EX_MA_LatchType()
	{
		MA_enable = false;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}
	public void set_rd(int op){
		this.rd = op;
	}
	public void set_pc(int op){
		this.pc = op;
	}
	public void set_res(int op){
		this.res = op;
	}
	public void set_inst(int op){
		this.inst = op;
	}
	public int get_rd(){
		return this.rd;
	}
	public int get_rd(){
		return this.pc;
	}
	public int get_rd(){
		return this.res;
	}
	public int get_rd(){
		return this.inst;
	}
}
