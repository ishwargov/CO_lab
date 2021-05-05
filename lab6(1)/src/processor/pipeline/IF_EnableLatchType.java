package processor.pipeline;

public class IF_EnableLatchType {
	
	boolean IF_enable,stall,IF_busy;
	
	public IF_EnableLatchType()
	{
		IF_enable = true;
		stall = true;
	}

	public boolean isIF_enable() {
		return IF_enable;
	}
	public boolean get_stall() {
		return stall;
	}
	public void set_stall(boolean iF_enable) {
		stall = iF_enable;
	}

	public void setIF_enable(boolean iF_enable) {
		IF_enable = iF_enable;
	}
	public void setIF_busy(boolean iF_busy) {
		IF_busy = iF_busy;
	}

}
