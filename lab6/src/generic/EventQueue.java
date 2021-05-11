package generic;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Iterator;
import processor.pipeline.InstructionFetch;

import processor.Clock;

public class EventQueue {
	
	PriorityQueue<Event> queue;
	
	public EventQueue()
	{
		queue = new PriorityQueue<Event>(new EventComparator());
	}
	
	public void addEvent(Event event)
	{
		queue.add(event);
	}

	public void processEvents()
	{	//System.out.println("procesing event");
		while(queue.isEmpty() == false && queue.peek().getEventTime() <= Clock.getCurrentTime())
		{
			Event event = queue.poll();
			event.getProcessingElement().handleEvent(event);
		}
	}
	public void delete_IF() {
		Iterator<Event> itr=queue.iterator();
		Event e;
		while(itr.hasNext()) {
			e = itr.next();
			if(e.requestingElement instanceof InstructionFetch) {
				((InstructionFetch)e.requestingElement).IF_EnableLatch.setIF_busy(false);
				itr.remove();
			}
			if(e.processingElement instanceof InstructionFetch) {
				((InstructionFetch)e.processingElement).IF_EnableLatch.setIF_busy(false);
				itr.remove();
			}
			
		}
	}
}

class EventComparator implements Comparator<Event>
{
	@Override
    public int compare(Event x, Event y)
    {
		if(x.getEventTime() < y.getEventTime())
		{
			return -1;
		}
		else if(x.getEventTime() > y.getEventTime())
		{
			return 1;
		}
		else
		{
			return 0;
		}
    }
}
