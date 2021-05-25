package com.mycompany.a1;

import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import java.lang.String;

public class Game extends Form{
	private GameWorld gw;
	private boolean exitConfirm = false;		// exit flag
	
	public Game() {
		gw = new GameWorld();
		gw.init();
		play();
	}
	
	private void play() {
		Label myLabel = new Label("Enter a Command:");
		this.addComponent(myLabel);
		final TextField myTextField = new TextField();
		this.addComponent(myTextField);
		this.show();
		
		myTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String sCommand=myTextField.getText().toString();
				myTextField.clear(); 
				
				//if(sCommand.length() != 0) {
				if(exitConfirm){
					switch(sCommand.charAt(0)) {
						case 'y':
							gw.endGame();
							break;
						case 'n':
							exitConfirm = false;		// disable exit flag
							System.out.println("Game Continue!");
							break;
						default:
							System.out.println("\nInvalid Input! Please Try Again!");
							break;
					}	// switch
				}		// if
				//}
				else {
					switch (sCommand.charAt(0)) {
						case 'a':
							gw.increaseSpeed();
							break;
						case 'b':
							gw.decreaseSpeed();
							break;
						case 'l':
							gw.headLeft();
							break;
						case 'r':
							gw.headRight();
							break;
						// case 1-9
						case '1':
							gw.flagCollision(1);
							break;
						case '2':
							gw.flagCollision(2);
							break;
						case '3':
							gw.flagCollision(3);
							break;
						case '4':
							gw.flagCollision(4);
							break;
						case '5':
							gw.flagCollision(5);
							break;
						case '6':
							gw.flagCollision(6);
							break;
						case '7':
							gw.flagCollision(7);
							break;
						case '8':
							gw.flagCollision(8);
							break;
						case '9':
							gw.flagCollision(9);
							break;
						case 'f':
							gw.foodStationCollision();
							break;
						case 'g':
							gw.spiderCollision();
							break;
						case 't':
							gw.clockTick();
							break;
						case 'd':
							gw.displayStatus();
							break;
						case 'm':
							gw.printMap();
							break;
						case 'x':
							// need to enforce the user to input y or n
							System.out.println("Are you sure to exit the game?");
							exitConfirm = true;		// exit flag is set to true
							break;
						default:
								System.out.println("\nInvalid Input! Please Try Again!");
								break;
					}	// switch
				} // else
			}	// actionPerformed
		}	// new ActionListener()
		);	// addActionListener
	}	// play()
}