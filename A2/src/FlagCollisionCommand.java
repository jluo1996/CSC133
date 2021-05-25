package com.mycompany.a2;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.*;

public class FlagCollisionCommand extends Command {
	private GameWorld gw;
	
	public FlagCollisionCommand(GameWorld gw) {
		super("Collide with Flag");			// Display "Collide with Flag"
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int flagCount = gw.getFlagCount();			// get total flag count
		
		Command okCommand = new Command("OK");		// "OK" button
		TextField myTF = new TextField();			// textfiled for user input
		Dialog.show("Enter flag number", myTF, okCommand);			// title = "Enter flag number", body = textfield, "OK" button
		String text = myTF.getText().toString();			// get user input	
		try {
			int temp = Integer.parseInt(text);		// convert user input into integer
			// if user input is valid (within the range)
			if(temp > 0 && temp <= flagCount) {
				gw.flagCollision(temp);
			}
			// if user input is out of range
			else {
				String info = "Please enter a number between 1-" + flagCount;
				Dialog.show("Error", info, "OK", null);
			} 
		} catch(NumberFormatException e1) {	// if user input non-integer
			String info = "Please enter a number!";
			Dialog.show("Error", info, "OK", null);
		}
	}
}
