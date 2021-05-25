package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class TickCommand extends Command {
	private GameWorld gw;
	
	public TickCommand(GameWorld gw) {
		super("Tick");		// display "Tick"
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gw.clockTick();
	}
}
