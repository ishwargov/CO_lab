package processor.memorysystem;
import generic.*;
import processor.Clock;
import processor.Processor;
import configuration.Configuration;

public class Cache implements Element{

    int size,h,t,cur;
    int hit,addr,val;
    long latency;
    CacheLine[] cacheq;
    Processor containingProcessor;
    Element retElement;

    public Cache(int size,long latency,Processor p,Element el){
        this.size = size;
        this.cacheq = new CacheLine[size+1];
        for(int i=0;i<size+1;i++) {
			cacheq[i]=new CacheLine();
		}
        this.h = -1;
        this.t = -1;
        this.cur = 0;
        this.latency = latency;
        this.hit = 0;
        this.val = 0;
        this.containingProcessor = p;
        this.addr = -1;
        this.retElement = el;
    }

    public void cacheRead(int address,MemoryReadEvent e){
        //System.out.printf("cache read addr : %d\n",address);
        if(retElement==containingProcessor.getMAUnit())    
            System.out.printf("load addr : %d\n",address);
        for(int i=0;i<cur;i++){
            if(cacheq[(h+i)%size].tag==address){
                if(retElement==containingProcessor.getMAUnit())     
                    System.out.printf("hit addr : %d ,cache val : %d,mem val: %d\n",address,cacheq[(h+i)%size].data,containingProcessor.getMainMemory().getWord(address));
                //System.out.printf("hit %d\n",address);
                MemoryReadEvent event = (MemoryReadEvent) e;
                MemoryResponseEvent MRE = new MemoryResponseEvent(Clock.getCurrentTime(),this,event.getRequestingElement(),cacheq[(h+i)%size].data);
                MRE.setValue2(0);
                Simulator.getEventQueue().addEvent(MRE);
                hit = 1;
                addr = address;
                return;
            }
        }
        handleCacheMiss(address,e);
    }
    public void cacheWrite(int address,int value){
        val = value;
        addr = address;
        hit=0;
        System.out.printf("addr : %d , val : %d\n",addr,val);
        for(int i=0;i<cur;i++){
            if(cacheq[(h+i)%size].tag==addr){
                hit = 1;
            }
        }
        Simulator.getEventQueue().addEvent(new MemoryWriteEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency ,this,containingProcessor.getMainMemory(),address,value));
    }
    public void handleCacheMiss(int address,Event e){
        hit = 0;
        addr = address;
        Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency ,this,containingProcessor.getMainMemory(),address));
    }
    public void handleResponse(MemoryResponseEvent e){
        MemoryResponseEvent MRE = new MemoryResponseEvent(Clock.getCurrentTime(),this,retElement,e.getValue());
        if(retElement==containingProcessor.getMAUnit())     
            System.out.printf("HRE addr : %d , val : %d\n",addr,e.getValue());
        MRE.setValue2(e.getValue2());
        if(e.getValue2()==0&&hit==0){
            if(cur>=size){
                cacheq[h].tag = addr;
                cacheq[h].data = MRE.getValue();
                h = (h+1)%size; 
                t = (t+1)%size;
            }
            else if(cur==0){
                h = 0;
                t = 0;
                cur++;
                cacheq[t].tag = addr;
                cacheq[t].data = MRE.getValue(); 
            }
            else{
                t = (t+1)%size;
                cur++;
                cacheq[t].tag = addr;
                cacheq[t].data = MRE.getValue(); 
            }
        }
        else if(e.getValue2()==1&&hit==1){
            System.out.printf("addr:%d,val:%d\n",addr,val);
            for(int i=0;i<cur;i++){
                if(cacheq[(h+i)%size].tag==addr){
                    cacheq[(h+i)%size].data = val;
                }
            }
            hit=0;
        }
        Simulator.getEventQueue().addEvent(MRE);
    }

    @Override
	public void handleEvent(Event e) {
        //System.out.printf("current q size: %d\n",cur);
        if(e.getEventType() == Event.EventType.MemoryRead){
            //System.out.printf("cache read\n");
            MemoryReadEvent event = (MemoryReadEvent)e;
            cacheRead(event.getAddressToReadFrom(),event);
        }
        else if(e.getEventType() == Event.EventType.MemoryWrite){
            //System.out.printf("cache write\n");
            MemoryWriteEvent event = (MemoryWriteEvent)e;
            cacheWrite(event.getAddressToWriteTo(),event.getValue());
        }
        else if(e.getEventType() == Event.EventType.MemoryResponse){
            //System.out.printf("cache response\n");
            handleResponse((MemoryResponseEvent)e);
        }
    }
}