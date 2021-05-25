package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.CheckBox; 

public class SoundCommand extends Command {
	private GameWorld gw;
	
	public SoundCommand(GameWorld gw) {
		super("Sound ON/OFF");			// display "Sound ON/OFF"
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// if check box is selected
		if(((CheckBox)e.getComponent()).isSelected()) {
			gw.setSoundOn(true);		// set sound to ON
		}
		else {
			gw.setSoundOn(false);		// set sound to OFF
		}
	}
}
