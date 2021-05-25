package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class AccelerateCommand extends Command {
	private GameWorld gw;
	
	public AccelerateCommand(GameWorld gw) {
		super("Accelerate");		// display "Accelerate"
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gw.increaseSpeed();		
	}
}
