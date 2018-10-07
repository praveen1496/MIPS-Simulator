package gui;
import java.util.*;
import java.lang.*;
import java.awt.*;

class Decode extends Stage {

    private Vector hazardList;
    
    private Instruction instructionSave;
    
    private int savePC;
    
    public boolean isStall;
    
    public boolean stallflag;
    
    public boolean assumeBranchTaken;
    
    public boolean branchPrediction;
    
    public boolean forwarding;
    
    public Vector branchTable; 

   
    public Decode() {
       super();
       instructionSave = new Instruction("NOP");
       isStall = false;
       assumeBranchTaken = true;   // assume branches always taken (when prediction disabled)
       hazardList = new Vector(3);
       branchTable = new Vector();
       hazardList.addElement(new Integer(0));
       hazardList.addElement(new Integer(0));
       hazardList.addElement(new Integer(0));
    }

  public void step(Vector Instructions, MemoryType regFile, 
                     Memory myMemory, WriteBack myWriteBack,
                     Fetch myFetch, Execute myExecute, ListBoxes lb) {

         String str;

        // if last instruction was stalled, recall stalled instruction

        if (PC==Instructions.size())
           PC = -9;  // this is actually a NOP received from Fetch

        if (isStall == true) {                             
            myInstruction = instructionSave;   
            PC = savePC;
        }
        
        stallflag = false;
        if ( ( ( (myExecute.myInstruction.opcode != 0) && 
               (myExecute.myInstruction.flush == false) &&
               (myInstruction.rs==
                ((Integer) hazardList.elementAt(0) ).intValue() )) ||  
              ( (myMemory.myInstruction.opcode != 0) && 
               (myMemory.myInstruction.flush == false) &&
               (myInstruction.rs==
                ((Integer) hazardList.elementAt(1) ).intValue() )) ||     
              ( (myWriteBack.myInstruction.opcode != 0) && 
               (myWriteBack.myInstruction.flush == false) &&
               (myInstruction.rs==
                ((Integer) hazardList.elementAt(2) ).intValue() )) ) &&
             (myInstruction.opcode != 0) && (myInstruction.flush == false) )
        {
            stallflag = true;
        }

        if ((forwarding==true) && (stallflag==true)) {
           // see if value ready at end of last execute stage (now memory stage)
           if ((myInstruction.rs == myMemory.myInstruction.rd) &&
           		(myInstruction.rs != myExecute.myInstruction.rd)) {
               switch(myMemory.myInstruction.opcode) {
                 case 0:   // NOP
                 case 35:  // LW
                 case 43:  // SW
                 case 70:  // BEQ
                 case 71:  // BNE
                     break;      // ignore these instructions
                 default:
                     myInstruction.rsValue = myMemory.myInstruction.rdValue; // forwarded
                     stallflag = false;
                     myInstruction.forwardRsFlag = true;
                     str = "Result forwarded from ALU: $" + myInstruction.rs+"="+
                           myInstruction.rsValue+".";
                     lb.Messages.add(str, 0);
               }
           } else if ((myInstruction.rs == myWriteBack.myInstruction.rd) &&
           		(myInstruction.rs != myExecute.myInstruction.rd)) {
                 myInstruction.rsValue = myWriteBack.myInstruction.rdValue;  // forwarded
                 stallflag = false;
                 myInstruction.forwardRsFlag = true;
                 str = "Result forwarded from MEM: $" + myInstruction.rs+"="+
                           myInstruction.rsValue+".";
                 lb.Messages.add(str, 0);
           }            
        }
           
        if ( (myInstruction.isImmediate()==false) && (myInstruction.opcode != 35) &&
             ( ( (myExecute.myInstruction.opcode != 0) && 
               (myExecute.myInstruction.flush == false) &&
               (myInstruction.rt==
                    ((Integer) hazardList.elementAt(0) ).intValue() )) ||  
             ( (myMemory.myInstruction.opcode != 0) && 
               (myMemory.myInstruction.flush == false) &&
               (myInstruction.rt==
                    ((Integer) hazardList.elementAt(1) ).intValue() )) ||     
             ( (myWriteBack.myInstruction.opcode != 0) && 
               (myWriteBack.myInstruction.flush == false) &&
               (myInstruction.rt==
                    ((Integer) hazardList.elementAt(2) ).intValue() )) ) && 
             (myInstruction.opcode != 0) && (myInstruction.flush == false) )
        {
            stallflag = true;
        }


        if ((!myInstruction.isImmediate())&&(forwarding==true)&&(stallflag==true)) {
           if ((myInstruction.rt == myMemory.myInstruction.rd) &&
           		(myInstruction.rt != myExecute.myInstruction.rd)) {
               switch(myMemory.myInstruction.opcode) {
                 case 0:   // NOP
                 case 35:  // LW
                 case 43:  // SW
                 case 70:  // BEQ
                 case 71:  // BNE
                     break;      // ignore these instructions
                 default:
                     myInstruction.rtValue = myMemory.myInstruction.rdValue; // forwarded
                     stallflag = false;
                     myInstruction.forwardRtFlag = true;
                     str = "Result forwarded from ALU: $" + myInstruction.rt+"="+
                           myInstruction.rtValue+".";
                     lb.Messages.add(str, 0);
               }
           } else if ((myInstruction.rt == myWriteBack.myInstruction.rd) &&
           		(myInstruction.rt != myExecute.myInstruction.rd)) {
                 myInstruction.rtValue = myWriteBack.myInstruction.rdValue;  // forwarded
                 stallflag = false;
                 myInstruction.forwardRtFlag = true;
                 str = "Result forwarded from MEM: $" + myInstruction.rt+"="+
                        myInstruction.rtValue+".";
                 lb.Messages.add(str, 0);
 
           }            
        }
             

        if (stallflag == true) {
            if (isStall == false) {
                isStall = true;
                instructionSave = myInstruction;
                savePC = PC;
            }
            myInstruction = new Instruction("NOP");     
            myInstruction.rsValue = 0;
            myInstruction.rtValue = 0; 
            PC = -9;
        } else {            
            if (isStall == true) {
                 isStall = false;
                 myInstruction = instructionSave;
            }
            
  
       }        


        if (myInstruction.forwardRsFlag == false) 
            myInstruction.rsValue = regFile.getValue( myInstruction.rs );
        if (myInstruction.forwardRtFlag == false) {       
            if (myInstruction.isImmediate())  
                  // assign immediate value
                  myInstruction.rtValue = myInstruction.immediate;
            else
                  // read from register
                  myInstruction.rtValue = regFile.getValue( myInstruction.rt );
        }


       hazardList.setElementAt( hazardList.elementAt(1), 2);
       hazardList.setElementAt( hazardList.elementAt(0), 1);
       hazardList.setElementAt( new Integer(myInstruction.rd), 0);      

       if (isStall == true) {
             // throw event to tell user a stall has been issued
             str = "Stall issued for instruction "+savePC+".";
             lb.Messages.add(str, 0);
       }
    }

   public String toString() {
      String temp;
 
      if (myInstruction.flush == true) {
           temp = "FLUSHED: \n" + myInstruction + "\n";
           return temp;
      }
 
      if (PC >= 0) { 
         temp = Integer.toString(PC) + ":\n" + myInstruction + "\n" +
             "ALUop1= " + Integer.toString(myInstruction.rsValue) + "\n" +
             "ALUop2= " + Integer.toString(myInstruction.rtValue) + "\n";
      } else {
         temp = myInstruction + "\n";
         if (isStall)
            temp += "Stalled:\n" + Integer.toString(savePC) + ":  " +
                    instructionSave + "\n";
      }

      return temp;
   }
   
}

