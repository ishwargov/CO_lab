package processor.pipeline;

public class EX_MA_LatchType {
	
	boolean MA_enable,stall,MA_busy;
	int rd,pc,res,inst;
	
	public EX_MA_LatchType()
	{
		MA_enable = false;
		MA_busy = false;
		stall = true;
		rd = -1;
	}

	public boolean isMA_enable() {
		return MA_enable;
	}

	public boolean get_stall() {
		return stall;
	}
	public void set_stall(boolean iF_enable) {
		stall = iF_enable;
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
	public void set_opcode(int op){
		this.inst = op;
	}
	public int get_rd(){
		return this.rd;
	}
	public int get_pc(){
		return this.pc;
	}
	public int get_res(){
		return this.res;
	}
	public int get_opcode(){
		return this.inst;
	}

	public void setMA_busy(boolean mA_busy) {
		MA_busy = mA_busy;
	}
	public boolean isMA_busy() {
		return MA_busy;
	}
}
