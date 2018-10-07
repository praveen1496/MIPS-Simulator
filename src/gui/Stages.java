package gui;
import java.awt.*;

//This class represents all the stages of the pipeline

public class Stages extends Panel
{
    // Text area to display fetch stage execution
    TextArea fetch = new TextArea(5, 10);
    
    // Text area to display decode stage execution
    TextArea decode = new TextArea(5, 10);
    
    // Text area to display execute stage execution
    TextArea execute = new TextArea(5, 10);
	
    // Text area to display Write back stage execution
    TextArea wb = new TextArea(5, 10);

    // Text area to display memory stage execution
    TextArea memory = new TextArea(5, 10);

    // Text field to display CPI of currently executing instruction		
    TextField cpi = new TextField();

    // Text field to display number of instructions
    TextField nInstr = new TextField();
	
    // Text field to display number of cycles being executed
    TextField nCycles = new TextField();
    Canvas filler = new Canvas();
	
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

	// Layout for the objects created
	Stages()
	{
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);
		
		// Filler
		buildConstraints(constraints, 0, 0, 5, 1, 100, 15);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		gridbag.setConstraints(filler, constraints);
		add(filler);
		
		// Fetch label
		buildConstraints(constraints, 0, 1, 1, 1, 20, 15);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		Label Lfetch = new Label("Fetch", Label.CENTER);
		gridbag.setConstraints(Lfetch, constraints);
		add(Lfetch);
		
		// Decode label
		buildConstraints(constraints, 1, 1, 1, 1, 20, 15);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		Label Ldecode = new Label("Decode", Label.CENTER);
		gridbag.setConstraints(Ldecode, constraints);
		add(Ldecode);
		
		//Execute label
		buildConstraints(constraints, 2, 1, 1, 1, 20, 15);
    		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		Label Lex = new Label("Execute", Label.CENTER);
		gridbag.setConstraints(Lex, constraints);
		add(Lex);
		
		// Memory label
		buildConstraints(constraints, 3, 1, 1, 1, 20, 15);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		Label Lmem = new Label("Memory", Label.CENTER);
		gridbag.setConstraints(Lmem, constraints);
		add(Lmem);
		
		// WB label
		buildConstraints(constraints, 4, 1, 1, 1, 20, 15);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		Label Lwb = new Label("Write Back", Label.CENTER);
		gridbag.setConstraints(Lwb, constraints);
		add(Lwb);
		
		// Fetch textarea
		buildConstraints(constraints, 0, 2, 1, 1, 20, 60);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		gridbag.setConstraints(fetch, constraints);
		fetch.setEditable(false);
		add(fetch);
		
		// Decode textarea
		buildConstraints(constraints, 1, 2, 1, 1, 20, 60);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		gridbag.setConstraints(decode, constraints);
		decode.setEditable(false);
		add(decode);
		
		// Execute textarea
		buildConstraints(constraints, 2, 2, 1, 1, 20, 60);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		gridbag.setConstraints(execute, constraints);
		execute.setEditable(false);
		add(execute);
		
		// Memory textarea
		buildConstraints(constraints, 3, 2, 1, 1, 20, 60);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		gridbag.setConstraints(memory, constraints);
		memory.setEditable(false);
		add(memory);
		
		// WB textarea
		buildConstraints(constraints, 4, 2, 1, 1, 20, 60);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		gridbag.setConstraints(wb, constraints);
		wb.setEditable(false);
		add(wb);
		
		Panel stats = new Panel();
		stats.setLayout(new GridLayout(1,6,10,10));
		
		Label Lni = new Label("Instructions:", Label.RIGHT);
		Label Lnc = new Label("Cycles:", Label.RIGHT);
		Label Lcpi = new Label("CPI:", Label.RIGHT);
		nInstr.setEditable(false);
		nCycles.setEditable(false);
		cpi.setEditable(false);
		
		stats.add(Lni);
		stats.add(nInstr);
		stats.add(Lnc);
		stats.add(nCycles);
		stats.add(Lcpi);
		stats.add(cpi);
		
		// Stats Panel
		buildConstraints(constraints, 0, 3, 5, 1, 100, 10);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		gridbag.setConstraints(stats, constraints);
		add(stats);
				
		validate();
	}
	
	// to clear all text areas
	public void clearAll()
	{
		fetch.setText("");
		decode.setText("");
		execute.setText("");
		memory.setText("");
		wb.setText("");
	}
	
}
