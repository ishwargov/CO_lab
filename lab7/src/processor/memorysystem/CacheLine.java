package processor.memorysystem;
import generic.*;
import processor.Clock;
import configuration.Configuration;

public class CacheLine{
    public int tag,data;
    public void CacheLine(){
        this.tag = -1;
        this.data = -1;
    }

}