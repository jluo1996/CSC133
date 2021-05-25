package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class AboutCommand extends Command {
	public AboutCommand() {
		super("About");		// display "About"
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String info = "Name: Jianliang Luo\n" 
				+ "Course: CSC133-03\n"
				+ "Version: 1.0";
		Dialog.show("About", info, "OK", null);		// title = "About", body = info, "OK" button
	}
}
