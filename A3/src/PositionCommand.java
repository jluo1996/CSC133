package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.Button;

public class PositionCommand extends Command{
	private GameWorld gw;
	
	PositionCommand(GameWorld newGW){
		super("Position");
		this.gw = newGW;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// can only be invoked when the game is played
		if(gw.getPause()) {
			gw.turnOnPostion();
		}
	}
}
