package gui;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.util.*;

/*
 * GUI is the main class for the MIPS Simulator.  It contains all other objects
 * and controls the events between them.
 */
public class GUI extends java.applet.Applet implements ActionListener
{
	/*
	 * The user interface object.
	*/
	public UI usr = new UI();
	/*
	 * The stages interface object
	 */
	public Stages stg = new Stages();
	/**
	 * The listboxes interface object.
	 */
	public ListBoxes lb = new ListBoxes();
	/**
	 * The help object.
	 */
	/**
	 * The vector that holds the instruction objects.
	 */
	public static Vector instr = new Vector();
	/**
	 * The simulator object.
	 */
    public static Simulator sim;
	/*
	 * Booleans used to check if the user has stepped or added an instruction.  
	*/
    private boolean stepped, instrAdded;
	/*
	 * Integer that keeps track of number of cycles in the current execution.
	*/
    public int numCycles = 0;
	/**
	 * Float that keeps track of the cycles per instruction.
	*/
    public double CPI = 0.0;
	/**
	 * Button used when run as an application to quit.
	*/
    public static Button qButton = new Button("Quit");

	/**
	 * The main method provides initializations for GUI when run
	 * as an application.
	*/
    public static void main(String[] args) 
    {
        GUI theGUI = new GUI();
        
        Frame win = new Frame("MIPS Simulator");
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		win.setLayout(gridbag);

		theGUI.buildConstraints(constraints, 0, 0, 1, 1, 100, 90);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		gridbag.setConstraints(theGUI, constraints);
		win.add(theGUI);

        qButton.addActionListener(theGUI);

		theGUI.buildConstraints(constraints, 0, 1, 1, 1, 100, 10);
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.CENTER;
		gridbag.setConstraints(qButton, constraints);
		win.add(qButton);        
                
        theGUI.init();
        win.pack();
        win.setSize(new Dimension(520,520));
        win.setBackground(Color.black);
        win.setVisible(true);

        theGUI.start();
    }
    
	/**
	 * Fills in the parameters for a GridBagConstraint.
	*/
	void buildConstraints(GridBagConstraints gbc, int gx, int gy,
		int gw, int gh, int wx, int wy)
	{
		gbc.gridx = gx;
		gbc.gridy = gy;
		gbc.gridwidth = gw;
		gbc.gridheight = gh;
		gbc.weightx = wx;
		gbc.weighty = wy;
	}
	
	/**
	 * Initializes the GUI object.
	*/
	public void init()
	{
		sim = new Simulator(instr, lb, stg);
		setBackground(Color.CYAN);
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);
		
		// ListBoxes object
		buildConstraints(constraints, 0, 0, 1, 1, 100, 50);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		gridbag.setConstraints(lb, constraints);
		lb.Instructions.addActionListener(this);
		add(lb);
		
		// Stages object
		buildConstraints(constraints, 0, 1, 1, 1, 100, 30);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		gridbag.setConstraints(stg, constraints);
		add(stg);

		// UI object
		buildConstraints(constraints, 0, 2, 1, 1, 100, 20);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		gridbag.setConstraints(usr, constraints);
		usr.tfai.addActionListener(this);
		usr.addinst.addActionListener(this);
		usr.tfrun.addActionListener(this);
		usr.run.addActionListener(this);
		usr.step.addActionListener(this);
		usr.reset.addActionListener(this);
		usr.clear.addActionListener(this);
		usr.edit.addActionListener(this);
		usr.tfedit.addActionListener(this);
		add(usr);
		
		usr.step.setEnabled(false);
		usr.run.setEnabled(false);
		usr.tfrun.setEnabled(false);
		
		validate();
		
		stepped = false;
		instrAdded = false;
		
	}
	

	public Insets getInsets()
	{
		return new Insets(4, 5, 4, 5);
	}
	

	/**
	 * Handles the action events.  These events are caused by
	 * components in the UI (except for qButton).
	 * @see UI
	*/
	public void actionPerformed(ActionEvent e)
	{
		String temp;
		int tempnum, j = 0;
		Instruction tempinstr;
		boolean OK, done;
		
		Object src = e.getSource();
		
		if ((src == usr.addinst) || (src == usr.tfai))
		{
			temp = usr.tfai.getText();
			temp = temp.toUpperCase();
			tempinstr = new Instruction(temp);
			if (tempinstr.valid() == true)
			{
				if(!instrAdded)
				{
					usr.step.setEnabled(true);
					usr.run.setEnabled(true);
					usr.tfrun.setEnabled(true);
					instrAdded = true;
				}
				instr.addElement(tempinstr);
				tempnum = lb.Instructions.getItemCount();
				temp = tempnum + ": " + temp;
				lb.Instructions.add(temp);
				usr.tfai.selectAll();
				usr.tfai.requestFocus();		
			}
			else
				lb.Messages.add(tempinstr.theError(), 0);

		} else if ((src == usr.reset) || (src == usr.clear))
		{
			lb.Messages.removeAll();
            lb.initRF();
            lb.initMemory();
            stg.nCycles.setText("0");
            stg.nInstr.setText("0");
            stg.cpi.setText("0.0");
            numCycles = 0;
            stg.clearAll();
            stepped = false;
			setEnabledUI(true);
			if (src == usr.reset)
			{
            	lb.Messages.add("Program has been reset.", 0);
            	if (instrAdded)
            	{
					usr.step.setEnabled(true);
					usr.run.setEnabled(true);
					usr.tfrun.setEnabled(true);
				}
			}
            else
            {
                lb.Messages.add("Program has been cleared.", 0);
                if (!instr.isEmpty())
                {
                    instr.removeAllElements();
                    lb.Instructions.removeAll();
                }
                instrAdded = false;
				usr.step.setEnabled(false);
				usr.run.setEnabled(false);
				usr.tfrun.setEnabled(false);
            }
			sim = new Simulator(instr, lb, stg);
		} else if ((src == usr.step) || (src == usr.run) || (src == usr.tfrun))
		{
			usr.step.setEnabled(false);
			usr.run.setEnabled(false);
			usr.tfrun.setEnabled(false);
			usr.reset.setEnabled(false);
			usr.clear.setEnabled(false);
			
			done = false;
			
			if (!stepped)
			{
        		sim.enableForwarding(usr.doForwarding());
//        		sim.enableBP(usr.doBP());
				setEnabledUI(false);
				stepped = true;
			}
			
			if (src == usr.step)
				done = sim.step();
			else
			{
				OK = true;
				try{
					j = Integer.parseInt(usr.tfrun.getText());
				} catch (NumberFormatException exc) {
					lb.Messages.add("The run length is invalid.", 0);
					OK = false;
				}
RUNLOOP:		if(OK)
				{
					if(j>0)
					{
						if (!stepped)
						{
        					sim.enableForwarding(usr.doForwarding());
        					sim.enableBP(usr.doBP());
							setEnabledUI(false);
							stepped = true;
						}
						for(tempnum=0;tempnum<j;tempnum++)
						{
							done = sim.step();
							numCycles++;
							if(done)
								break RUNLOOP;
						}
						temp = "Finished running "+j+" steps.";
						lb.Messages.add(temp, 0);
					}
					else
					{
						lb.Messages.add("The run length is invalid.", 0);
					}
				}
			}
			
			numCycles++;
			stg.nCycles.setText(String.valueOf(numCycles));
			j = sim.myWriteBack.getNumberExecutedInstructions();
			stg.nInstr.setText(String.valueOf(j));
			if(j>0)
				CPI = (float)numCycles / (float)j;
			stg.cpi.setText(String.valueOf(CPI));
			
			if(done)
			{
				usr.step.setEnabled(false);
				usr.run.setEnabled(false);
				usr.tfrun.setEnabled(false);
			}
			else
			{
				usr.step.setEnabled(true);
				usr.run.setEnabled(true);
				usr.tfrun.setEnabled(true);
			}
			usr.reset.setEnabled(true);
			usr.clear.setEnabled(true);
			
		}
                else if ((src == usr.edit) || (src == usr.tfedit))
		{
			tempnum = lb.Instructions.getSelectedIndex();
			if(tempnum != -1)
			{
				temp = usr.tfedit.getText();
				temp = temp.toUpperCase();
				tempinstr = new Instruction(temp);
				if (tempinstr.valid() == true)
				{
					instr.setElementAt(tempinstr, tempnum);
					temp = tempnum + ": " + temp;
					lb.Instructions.replaceItem(temp, tempnum);
					usr.tfedit.selectAll();
					usr.tfedit.requestFocus();		
				}
				else
					lb.Messages.add(tempinstr.theError(), 0);
			}
			else
				lb.Messages.add("An instruction  must be selected",0);
		} 
                
                else if (src == qButton)
		{
			System.exit(0);
		}

	}
	

	void setEnabledUI(boolean cond)
	{
		usr.addinst.setEnabled(cond);
		usr.tfai.setEnabled(cond);
		usr.edit.setEnabled(cond);
		usr.branch.setEnabled(cond);
		usr.forward.setEnabled(cond);
		usr.tfedit.setEnabled(cond);
	}
		
}
