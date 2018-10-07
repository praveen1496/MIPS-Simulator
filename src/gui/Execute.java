package gui;
import java.util.*;
import java.lang.*;
import java.awt.*;

class Execute extends Stage {

  
      public Execute() {
           super();
      } 

	
      public void step(Vector Instructions, Fetch myFetch, Decode myDecode, ListBoxes lb) {            
          String str;
 
          switch(myInstruction.opcode) {
            case 24: // MUL -- 8 bit 
                  myInstruction.rdValue = (myInstruction.rsValue & 0x00FF) * 
                  (myInstruction.rtValue & 0x00FF);      
                  break;         
            case 26: // DIV -- 8 bit
                  myInstruction.rdValue = (myInstruction.rsValue & 0x00FF) / 
                  (myInstruction.rtValue & 0x00FF);               
                  break;         
            case 32: // ADD
                  myInstruction.rdValue = myInstruction.rsValue + myInstruction.rtValue;
                  break;         
            case 34: // SUB
                  myInstruction.rdValue = myInstruction.rsValue - myInstruction.rtValue; 
                  break;         
            case 35: // LW
            case 43: // SW
                  // rdValue becomes the memory address with which to load/store. 
                  // For SW, rtValue is the value to store into memory.
                  myInstruction.rdValue = myInstruction.rsValue + myInstruction.immediate; 
                  break;         
            case 50: // SLL              
                  myInstruction.rdValue = myInstruction.rsValue << myInstruction.rtValue;               
                  break;         
            case 51: // SRL              
                 myInstruction.rdValue = myInstruction.rsValue >> myInstruction.rtValue;               
                  break;         
            case 36: // AND               
                  myInstruction.rdValue = myInstruction.rsValue & myInstruction.rtValue;               
                  break;         
            case 37: // OR              
                  myInstruction.rdValue = myInstruction.rsValue | myInstruction.rtValue;               
                  break;         
            case 38: // XOR              
                  myInstruction.rdValue = myInstruction.rsValue ^ myInstruction.rtValue; 
                  break;         
            case 60: // SLT              
                  if (myInstruction.rsValue < myInstruction.rtValue) 
                  	myInstruction.rdValue = 1;
                  else
                        myInstruction.rdValue = 0;               
                  break;         
            case 61: // SLE              
                  if (myInstruction.rsValue <= myInstruction.rtValue) 
                  	myInstruction.rdValue = 1;
                  else
                        myInstruction.rdValue = 0;               
                  break;         
            case 62: // SEQ              
                  if (myInstruction.rsValue == myInstruction.rtValue) 
                  	myInstruction.rdValue = 1;
                  else
                        myInstruction.rdValue = 0;               
                  break;         
            case 63: // SGT              
                  if (myInstruction.rsValue > myInstruction.rtValue) 
                  	myInstruction.rdValue = 1;
                  else
                        myInstruction.rdValue = 0;               
                  break;         
            case 64: // SGE              
                  if (myInstruction.rsValue >= myInstruction.rtValue) 
                  	myInstruction.rdValue = 1;
                  else
                        myInstruction.rdValue = 0;               
                  break;         
            case 70: // BEQ              
            case 71: // BNE 
                  if (((myInstruction.opcode == 71) && (myInstruction.rsValue != myInstruction.rtValue)) 
                      || ((myInstruction.opcode == 70)&&(myInstruction.rsValue == myInstruction.rtValue))){
                          myFetch.PC = myInstruction.immediate - 1; // next instruct will be br addr
                          myFetch.myInstruction.flush = true;
                          myDecode.myInstruction.flush = true;
                  }
                  break;
         }
   }

   public String toString() {
      String temp;
 
      if (myInstruction.flush == true) {
           temp = "FLUSHED: \n" + myInstruction + "\n";
           return temp;
      }
  
      switch(myInstruction.opcode) {
        case 0:
           temp = myInstruction + "\n";
           break;
        case 35:  // LW
        case 43:  // SW
            temp = Integer.toString(PC) + ":\n" + myInstruction + "\n" +
            "Memory\nAddress= " + Integer.toString(myInstruction.rdValue) + "\n";
            break;
        case 70:  // BEQ
            if (myInstruction.rsValue == myInstruction.rtValue)
                temp = Integer.toString(PC) + ":\n" + myInstruction + "\n"+
                       "Result:\nTake branch\n";
            else
                temp = Integer.toString(PC) + ":\n" + myInstruction + "\n"+
                       "Result:\nDon't take\nbranch\n";                  
            break;
        case 71:  // BNE
            if (myInstruction.rsValue != myInstruction.rtValue)
                temp = Integer.toString(PC) + ":\n" + myInstruction + "\n"+
                       "Result:\nTake branch\n";
            else
                temp = Integer.toString(PC) + ":\n" + myInstruction + "\n"+
                       "Result:\nDon't take\nbranch\n";                  
            break;
        default:  // R-type instructions
            temp = Integer.toString(PC) + ":\n" + myInstruction + "\n" +
            "ALUop1= " + Integer.toString(myInstruction.rsValue) + "\n" +
            "ALUop2= " + Integer.toString(myInstruction.rtValue) + "\n" +
            "Result= " + Integer.toString(myInstruction.rdValue) + "\n";
      }
      return temp;
   }

}

