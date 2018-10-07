package gui;
import java.util.*;
import java.lang.*;
import java.awt.*;


class Memory extends Stage {
	
    private int memAddr; 

    public Memory() {
         super();
         memAddr = -1;
    }

    public void step(MemoryType mainMemory, ListBoxes lb ) { 
          String str;
            
          memAddr = myInstruction.rdValue;  
      
          if (myInstruction.flush == true)
          {
          	myInstruction.wbFlush = true;
             return;
          }
          else
          	myInstruction.wbFlush = false;

          if (myInstruction.opcode == 43) {  // SW 
               if (mainMemory.putValue( myInstruction.rtValue, memAddr )) {
                   if (memAddr < 10)
                     str = "MEM0"+memAddr+": "+myInstruction.rtValue;
                   else
                     str = "MEM"+memAddr+": "+myInstruction.rtValue;                  
                  lb.Memory.replaceItem(str, memAddr);
               }
          }
                  
               
          if (myInstruction.opcode == 35)  // LW 
               myInstruction.rdValue = mainMemory.getValue( memAddr );                   
    }

   public String toString() {
      String temp;
   
      if (myInstruction.flush == true) {
           temp = "FLUSHED: \n" + myInstruction + "\n";
           return temp;
      }

      switch(myInstruction.opcode) {
        case 0:  // NOP
            temp = myInstruction + "\n";
            break;
        case 35:  // LW
            temp = Integer.toString(PC) + ":\n" + myInstruction + "\n" +
            "Load Word:\nAddress= " + Integer.toString(memAddr) + "\n" +
            "Value= " + Integer.toString(myInstruction.rdValue) + "\n";
            break;
        case 43:  // SW
            temp = Integer.toString(PC) + ":\n" + myInstruction + "\n" +
            "Save Word:\nAddress= " + Integer.toString(memAddr) + "\n" +
            "Value= " + Integer.toString(myInstruction.rtValue) + "\n";
            break;
        default:  // R-type instructions
            temp = Integer.toString(PC) + ":\n" + myInstruction + "\n";
                   
      }
      return temp;
   }

}
