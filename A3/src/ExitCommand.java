package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;

public class ExitCommand extends Command {
	public ExitCommand() {
		super("Exit");		// display "Exit"
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Command okCommand = new Command("OK");		// "OK" button
		Command cancelCommand = new Command("Cancel");		// "Cancel" button
		Command[] cmds = new Command[] {okCommand, cancelCommand};
		Command c = Dialog.show("Quit", "Do you want to exit?", cmds);		// tile = "Quit", body = "Do you want to exit", "OK" button and "Cancel" button
		// if "OK" is selected
		if(c == okCommand) {
			Display.getInstance().exitApplication();		// close the app
		}
	}
}
