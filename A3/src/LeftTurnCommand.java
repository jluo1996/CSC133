package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class LeftTurnCommand extends Command {
	private GameWorld gw;
	
	public LeftTurnCommand(GameWorld gw) {
		super("Left");			// display "Left"
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		gw.headLeft();
		System.out.println("The director has been turned left!!!");
	}
}
