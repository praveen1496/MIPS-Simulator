package gui;
import java.util.*;
import java.lang.*;


class MemoryType {
	
   private Vector memory;                

   public MemoryType() {
        super();
        memory = new Vector(32);          // 32 words of memory
        int i;
        for(i=0; i<32;i++)                // initialize to all 0's
            memory.addElement( new Integer(0) );
   }

   public int getValue( int address ) {
       return ((Integer) memory.elementAt( address ) ).intValue();
   } 


   public boolean putValue( int value, int address ) {
       if ((address >= 0) && (address< 32)) {
           memory.setElementAt((new Integer(value)), address); 
           return true;
       } else
           return false;
   }

}
