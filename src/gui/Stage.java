package gui;
import java.util.*;
import java.lang.*;
import java.awt.*;

class Stage {
	
   protected Instruction myInstruction;      // instruction object
	
   protected int PC;                         // program counter 
  
   public Stage() {
      myInstruction = new Instruction("NOP");
      PC = -9;  
  }

}
