package processor.pipeline;

public class IF_OF_LatchType {
	
	boolean OF_enable,stall,OF_busy;
	int instruction;
	
	public IF_OF_LatchType()
	{
		OF_enable = false;
		stall = true;
	}

	public boolean isOF_enable() {
		return OF_enable;
	}
	public boolean get_stall() {
		return stall;
	}

	public void setOF_enable(boolean oF_enable) {
		OF_enable = oF_enable;
	}

	public void set_stall(boolean oF_enable) {
		stall = oF_enable;
	}

	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}
	public void setOF_busy(boolean oF_busy) {
		OF_busy = oF_busy;
	}
	public boolean isOF_busy() {
		return OF_busy;
	}

}
