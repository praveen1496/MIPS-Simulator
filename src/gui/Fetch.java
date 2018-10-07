package gui;
import java.util.*;
import java.lang.*;

//Fetch stage 
class Fetch extends Stage {

   private int instructionSize; //number of instructions
    
	
   public Fetch() {
        super();
        PC = -1;  // set program counter so first instruction is PC=0
        instructionSize = 0;
   }

   public void step(Vector Instructions, Decode myDecode) {
      instructionSize = Instructions.size();       


      //Move PC to next instruciton if there is no stall at decode
      if (myDecode.isStall == false) {
          if ((PC < Instructions.size()) && (Instructions.size()>0) )
            PC++;
          if ((PC >= 0) && (PC < Instructions.size()) )
              myInstruction = (Instruction) Instructions.elementAt(PC);
          else {
              myInstruction = new Instruction("NOP");   
          }
          myInstruction.flush = false;  // valid instruction  
       }

   }

	
   public String toString() {
      String temp;

      if (myInstruction.flush == true) {
           temp = "FLUSHED: \n" + myInstruction + "\n";
           return temp;
      }
      if ((PC >= 0) && (PC < instructionSize) )
        temp = Integer.toString(PC) + ":\n" + myInstruction + "\n";
      else
        temp = myInstruction + "\n";
      return temp;
   }

}
