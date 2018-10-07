
package gui;
import java.awt.*;
import java.lang.String;



public class ListBoxes extends Panel
{

        static List Instructions = new List();

	static List RF = new List();

	static List Memory = new List();

	static List Messages = new List();
	Canvas dim = new Canvas();
	

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

//to set the layout and components of the object
	ListBoxes()
	{
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);
		
		// Instructions label
		buildConstraints(constraints, 0, 0, 1, 1, 45, 10);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.WEST;
		Label Linst = new Label("Instructions:", Label.LEFT);
		gridbag.setConstraints(Linst, constraints);
		add(Linst);
		
		// Invisible button used as a separator...
		buildConstraints(constraints, 1, 0, 1, 1, 10, 10);
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;
		gridbag.setConstraints(dim, constraints);
		add(dim);

		// Register File label
		buildConstraints(constraints, 2, 0, 1, 1, 45, 10);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.WEST;
		Label Lrf = new Label("Register File:", Label.LEFT);
		gridbag.setConstraints(Lrf, constraints);
		add(Lrf);

		// Instructions listbox
		buildConstraints(constraints, 0, 1, 1, 1, 45, 40);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.WEST;
		gridbag.setConstraints(Instructions, constraints);
		add(Instructions);
		
		// RF listbox
		buildConstraints(constraints, 2, 1, 1, 1, 45, 40);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.WEST;
		gridbag.setConstraints(RF, constraints);
		initRF();
		add(RF);
		
		// Memory label
		buildConstraints(constraints, 0, 2, 1, 1, 45, 10);
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;
		Label Lmem = new Label("Memory:", Label.LEFT);
		gridbag.setConstraints(Lmem, constraints);
		add(Lmem);

		// Messages label
		buildConstraints(constraints, 2, 2, 1, 1, 45, 10);
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;
		Label Lmsg = new Label("Messages:", Label.LEFT);
		gridbag.setConstraints(Lmsg, constraints);
		add(Lmsg);

		// Memory listbox
		buildConstraints(constraints, 0, 3, 1, 1, 45, 40);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.WEST;
		gridbag.setConstraints(Memory, constraints);
		initMemory();
		add(Memory);

		// Messages listbox
		buildConstraints(constraints, 2, 3, 1, 1, 45, 40);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.WEST;
		gridbag.setConstraints(Messages, constraints);
		add(Messages);
	}
	
//function to initialize register list
	public void initRF()
	{
		int i;
		String temp;
		
		RF.removeAll();
		
		for(i=0;i<10;i++)
		{
			temp = "R0" + i + ": 0";
			RF.addItem(temp);
		}
		for(i=10;i<32;i++)
		{
			temp = "R" + i + ": 0";
			RF.addItem(temp);
		}
	}

        //function to initialize memory 
	public void initMemory()
	{
		int i;
		String temp;
		
		Memory.removeAll();
				
		for(i=0;i<10;i++)
		{
			temp = "MEM0" + i + ": 0";
			Memory.addItem(temp);
		}
		for(i=10;i<32;i++)
		{
			temp = "MEM" + i + ": 0";
			Memory.addItem(temp);
		}
	}
	
}
