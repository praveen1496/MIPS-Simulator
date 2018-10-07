package gui;
import java.util.*;
import java.lang.*;
import java.awt.*;


class WriteBack extends Stage {
	
     private boolean done;
     
     public int numInstructions;

     public WriteBack() {
           super(); 
           done = false;
           numInstructions = 0; 
     }

     public void step(MemoryType regFile, Vector Instructions, ListBoxes lb, boolean allNOPs) {

         String str;
         
         if (myInstruction.wbFlush == true) 
             return;
         
         if (myInstruction.opcode != 0)
             numInstructions++;
                      
         switch( myInstruction.opcode) {
            case 0:   // NOP
            case 43:  // SW
            case 70:  // BEQ
            case 71:  // BNE
                break;
            default:
                 // write to dest register (if not $0)
                 if ((myInstruction.rd != 0) && (myInstruction.opcode != 0))
                      if (myInstruction.flush == false) {
                    		regFile.putValue( myInstruction.rdValue,myInstruction.rd );
                    		if (myInstruction.rd < 10)
                       			str = "R0"+myInstruction.rd+": "+myInstruction.rdValue;
                    		else
                       			str = "R"+myInstruction.rd+": "+myInstruction.rdValue;
                    		lb.RF.replaceItem(str, myInstruction.rd);
                  		}
         }

         // check to see if this is last instruction
         if ((PC == (Instructions.size()-1)) && allNOPs) {
            done = true;
            lb.Messages.addItem("Program done.", 0);
         }
     }

   public boolean programDone() 
   {
       return done;
   }


   public String toString() {
      String temp;
  
      if (myInstruction.wbFlush == true) {
           temp = "FLUSHED: \n" + myInstruction + "\n";
           return temp;
      }
 
      switch(myInstruction.opcode) {
            case 0:  // NOP
                temp = myInstruction + "\n";
                break;
            case 43:  // SW
            case 70:  // BEQ
            case 71:  // BNE
                temp = Integer.toString(PC) + ":\n" + myInstruction + "\n";
                break;
            default:
                if ((myInstruction.rd != 0) && (myInstruction.opcode != 0)) { 
                    temp = Integer.toString(PC) + ":\n" + myInstruction + "\n" +
                    "Wrote $" + Integer.toString(myInstruction.rd) + 
                    "= "+ Integer.toString(myInstruction.rdValue) + "\n";
                } else
                    temp = Integer.toString(PC) + ":\n" + myInstruction + "\n";

      }
      return temp;
   }

   public int getNumberExecutedInstructions()
   {
   		return numInstructions;
   }
   	

}  
