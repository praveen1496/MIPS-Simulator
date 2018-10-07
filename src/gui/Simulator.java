package gui;

import gui.Stages;
import java.awt.*;
import java.util.*;

//Class to handle the five stages of execution in pipeline
public class Simulator
{
	
    public static Fetch myFetch;
 
    public static Decode myDecode;
   
    public static Execute myExecute;
   
    public static Memory myMemory;
   
    public static WriteBack myWriteBack;
   
    public static MemoryType mainMemory;
    
    public static MemoryType regFile;
   
    public static ListBoxes lb;
   
    public static Vector Instructions;
    
    public static Stages stg;

    public Simulator(Vector _Instructions,
    	ListBoxes _lb,
    	Stages _stg) {
        Instructions = _Instructions;
        lb = _lb;
        stg = _stg;
        mainMemory = new MemoryType();
        regFile = new MemoryType();
        myFetch = new Fetch();
        myDecode = new Decode();
        myExecute = new Execute();
        myMemory = new Memory();
        myWriteBack = new WriteBack();
    }


    public boolean step() {
    	
    	boolean allNOPs = false;
    	
        // move instructions the pipeline by one stage
        myWriteBack.myInstruction = myMemory.myInstruction;
        myMemory.myInstruction = myExecute.myInstruction;
        myExecute.myInstruction = myDecode.myInstruction;
        myDecode.myInstruction = myFetch.myInstruction;
        myWriteBack.PC = myMemory.PC;
        myMemory.PC = myExecute.PC;
        myExecute.PC = myDecode.PC;
        if (myDecode.isStall == false)
          myDecode.PC = myFetch.PC;
       
        // load next instruction
        myFetch.step( Instructions, myDecode );           
        myDecode.step( Instructions, regFile, myMemory, 
                       myWriteBack, myFetch, myExecute, lb );
        myExecute.step( Instructions, myFetch, myDecode, lb );
        myMemory.step( mainMemory, lb );
        allNOPs = (myFetch.myInstruction.opcode == 0) &&
        		  (myDecode.myInstruction.opcode == 0) &&
        		  (myExecute.myInstruction.opcode == 0) &&
        		  (myMemory.myInstruction.opcode == 0);
        myWriteBack.step( regFile, Instructions, lb, allNOPs );

        // show status of stages
        stg.clearAll();
        stg.fetch.setText( myFetch.toString() );
        stg.decode.setText( myDecode.toString() );
        stg.execute.setText( myExecute.toString() );
        stg.memory.setText( myMemory.toString() );
        stg.wb.setText( myWriteBack.toString() );

        return myWriteBack.programDone();
    }
    
   
    public void enableForwarding(boolean cond)
    {
    	myDecode.forwarding = cond;
    }
    
    public void enableBP(boolean cond)
    {
    	myDecode.branchPrediction = cond;
    }
        
}
