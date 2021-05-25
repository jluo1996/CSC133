package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class BreakCommand extends Command {
	private GameWorld gw;
	
	public BreakCommand(GameWorld gw) {
		super("Break");		// display "Break"
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gw.decreaseSpeed();
	}
}
