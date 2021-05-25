package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class HelpCommand extends Command {
	public HelpCommand() {
		super("Help");		// display "Help"
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String info = "A: Acceleration\n"
				+ "B: Brake\n"
				+ "L: Turn 5 degrees to the left\n"
				+ "R: Turn 5 degrees to the right\n"
				+ "F: Collide with a food station\n"
				+ "G: Collide with a spider\n"
				+ "T: Tick the world clock\n";
		Dialog.show("Help", info, "OK", null);			// title = "Help", body = info, "OK" button
	}
}
