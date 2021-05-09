package generic;

public class MemoryResponseEvent extends Event {

	int value,value2;
	
	public MemoryResponseEvent(long eventTime, Element requestingElement, Element processingElement, int value) {
		super(eventTime, EventType.MemoryResponse, requestingElement, processingElement);
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	public int getValue2() {
		return value2;
	}

	public void setValue(int value) {
		this.value = value;
	}
	public void setValue2(int value) {
		this.value2 = value;
	}
}
