package gui;
import java.awt.*;

/**
 * The UI class contains the components involving user interaction with the simulator.
 */
public class UI extends Panel
{
	// To step through one cycle
	Button step = new Button("Step");
	
        // to step through number of cycles as entered in the textfield
	Button run = new Button("Run");
	
        // Text field to enter the number of cycles to run
	TextField tfrun = new TextField();

        // To reset the simulator
        Button reset = new Button("Reset");
	
        //To add new instruction entered in the text field
        Button addinst = new Button("Add Instruction");
	
        //Text field to enter new instruction
	TextField tfai = new TextField();
	
        // to clear all entered instruction
	Button clear = new Button("Clear Instructions");
	
        // To chnage highlighted instruciton to the instruction entered in the text field
	Button edit = new Button("Change Instruction");
	
        //To set branch prediction
	Checkbox branch = new Checkbox("Branch Prediction");
	
        //To set forwarding
	Checkbox forward = new Checkbox("Forwarding");
	
        
	TextField tfedit = new TextField();

	/**
	 * performs the layout for the object.
	 */
	UI()
	{
		setLayout(new GridLayout(4, 4, 5, 5));
		
		Canvas filler1 = new Canvas();
		Canvas filler2 = new Canvas();
		Canvas filler3 = new Canvas();
		
		add(filler1);
		add(forward);
		add(branch);
		branch.setEnabled(false);
		add(filler2);
		
		add(reset);
		add(step);
		add(run);
		add(tfrun);
		
		add(clear);
		add(addinst);
		add(tfai);
		
		add(filler3);
		add(edit);
		add(tfedit);
	}
	
	/**
	 * returns a boolean indicating whether forwarding has been checked.
	 */
	public boolean doForwarding()
	{
		return forward.getState();
	}
	
	/**
	 * returns a boolean indicating whether branch prediction has been checked (unused).
	 */
	public boolean doBP()
	{
		return branch.getState();
	}
	
}
