package gui;
import java.util.*;
import java.awt.*;
import java.lang.*;


public class Instruction
{
	
	public int opcode;
	
	public int rs;
	
	public int rt;
	
	public int rd;
	
	public int immediate;
	
	public int rdValue;
	
	public int rsValue;
	
	public int rtValue;
	
        public String instructionString;
   	
	private boolean ok;
	
	public boolean flush;
	
	public boolean branchTaken;
	
	public boolean wbFlush;
	
        public boolean forwardRsFlag;
    
        public boolean forwardRtFlag;

        private boolean isImmediateInstruction;

	private String error = "The entered instruction is invalid.";
	
	Instruction(String temp)
	{
		StringTokenizer tokens = new StringTokenizer(temp, " ");
		String op = "", t1 = "", t2 = "", t3 = "";
		int switcher;
	
	        rdValue = 0;           
	        rsValue = 0;
	        rtValue = 0;              
	        isImmediateInstruction = false;  	
	        ok = true;                
              instructionString = temp;
              flush = false;     
              branchTaken = true;      
                                       
              forwardRsFlag = false;    
              forwardRtFlag = false;

                // if no-op instruction (aka a stall)
                if (temp.equals("NOP")) {
                  instructionString = "NOP";
                  rd = 0;
                  rt = 0;
                  rs = 0;
                  immediate = 0;
                  opcode = 0;
                  return;
                }

trying:		
		try
		{
			op = tokens.nextToken();
			t1 = tokens.nextToken();
			t2 = tokens.nextToken();
			
			if(t1.length() < 2 || t2.length() < 2)
			{
				ok = false;
				break trying;
			}
			
			if (!op.equals("LW") && !op.equals("SW"))
			{
				t3 = tokens.nextToken();
				if(t3.length() == 0)
				{
					ok = false;
					break trying;
				}
			}
			
			if(op.equals("ADD") || op.equals("SUB") || op.equals("MULT") ||
				op.equals("DIV") || op.equals("AND") || op.equals("OR") ||
                op.equals("XOR") || op.equals("SLL") || op.equals("SRL") || 
                op.equals("SLT") || op.equals("SLE") || op.equals("SGT") || 
                op.equals("SGE") || op.equals("SEQ") )
				switcher = 0;  // R-type instructions
			else if(op.equals("ADDI") || op.equals("ANDI") || op.equals("ORI") || 
                      op.equals("XORI") || op.equals("SUBI") || op.equals("SLLI") ||
                      op.equals("SRLI") )
				switcher = 1;  // I-type instructions
			else if(op.equals("LW") || op.equals("SW"))
				switcher = 2;  // load/store word instructions
            else if(op.equals("BEQ") || op.equals("BNE") )
                switcher = 3;  // branch instructions
			else
			{
				ok = false;
				break trying;
			}
			
			switch (switcher)
			{
				case 0:
					if(!t1.startsWith("$") || !t2.startsWith("$") ||
					    !t1.startsWith("$"))
					{
						ok = false;
						break trying;
					}
					else
					{
						rd = Integer.parseInt(t1.substring(1));
						rs = Integer.parseInt(t2.substring(1));
						rt = Integer.parseInt(t3.substring(1));
						if (rt < 0 || rt > 31 || rs < 0 || rs > 31 || rd < 1 || rd > 31)
						{
							error = "Invalid Instr: register out of bounds";
							ok = false;
							break trying;
						}
						if (op.equals("ADD"))
							opcode = 32;
						else if (op.equals("SUB"))
							opcode = 34;
						else if (op.equals("MULT"))
							opcode = 24;
						else if (op.equals("DIV"))
							opcode = 26;
						else if (op.equals("AND"))
	                        opcode = 36;
                        else if (op.equals("XOR"))
                            opcode = 38;
                        else if (op.equals("SLL"))
                            opcode = 50;
                        else if (op.equals("SRL"))
                            opcode = 51;
                        else if (op.equals("SLT"))
                            opcode = 60;
                        else if (op.equals("SLE"))
                            opcode = 61;
                        else if (op.equals("SEQ"))
                            opcode = 62;
                        else if (op.equals("SGT"))
                            opcode = 63;
                        else if (op.equals("SGE"))
                            opcode = 64;
						else
							opcode = 37; // OR is the only one left
					}
					break;
				case 1:
					if(!t1.startsWith("$") || !t2.startsWith("$"))
					{
						ok = false;
						break trying;
					}
					else
					{
						rd = Integer.parseInt(t1.substring(1));
						rs = Integer.parseInt(t2.substring(1));
						if (rd < 1 || rd > 31 || rs < 0 || rs > 31)
						{
							error = "Invalid Instr: register out of bounds";
							ok = false;
							break trying;
						}
						isImmediateInstruction = true;
						immediate = Integer.parseInt(t3);
						if(op.equals("ADDI")) 
							opcode = 32;
						else if (op.equals("ANDI"))
							opcode = 36;
						else if (op.equals("SUBI"))
							opcode = 34;
						else if (op.equals("XORI"))
							opcode = 38;
						else if (op.equals("SLLI"))
							opcode = 50;
						else if (op.equals("SRLI"))
							opcode = 51;
						else
							opcode = 37; // ORI is the only one left
					}
					break;
				case 2:
					if(!t1.startsWith("$"))
					{
						ok = false;
						break trying;
					}
					else
					{
						rt = Integer.parseInt(t1.substring(1));
						rs = Integer.parseInt(t2.substring(t2.indexOf("$")+1,t2.indexOf(")")));
						if (rt < 1 || rt > 31 || rs < 0 || rs > 31)
						{
							error = "Invalid Instr: register out of bounds";
							ok = false;
							break trying;
						}
						immediate = Integer.parseInt(t2.substring(0,t2.indexOf("(")));
						if (op.equals("LW")) {
							opcode = 35;
                            rd = rt;
						} else
							opcode = 43; // SW is the only one left
					}
					break;
                case 3:
					if(!t1.startsWith("$") || !t2.startsWith("$"))
					{
						ok = false;
						break trying;
					}
					else
					{
						rs = Integer.parseInt(t1.substring(1));
						rt = Integer.parseInt(t2.substring(1));
						if (rs < 1 || rs > 31 || rt < 0 || rt > 31)
						{
							error = "Invalid Instr: register out of bounds";
							ok = false;
							break trying;
						}
						isImmediateInstruction = false;
						immediate = Integer.parseInt(t3);
						if(op.equals("BEQ")) 
							opcode = 70;
						else
							opcode = 71; // BNE is the only one left
					}
					break;                                   
				default:
					ok = false;
					break trying;
			}
		}
		catch(NumberFormatException e)
		{
			ok = false;
		}
		catch(NoSuchElementException e2)
		{
			ok = false;
		}
		catch(StringIndexOutOfBoundsException e3)
		{
			ok = false;
		}
	}
	
	
	public boolean valid()
	{
		return ok;
	}
	
	public String theError()
	{
		return error;
	}
    
    public boolean isImmediate()
    {
        return isImmediateInstruction;
    }
	
    public String toString()
    {
        return (instructionString+"\n");
    }
}
