package com.mycompany.a3;

import com.codename1.ui.Form;
import com.codename1.ui.util.UITimer;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import java.lang.String;
import com.codename1.ui.Form;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox; 
import com.codename1.ui.Container;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.charts.models.*;

public class Game extends Form implements Runnable{
	final int height = 1000;		// map height
	final int width = 1000;			// map width
	private final int GAME_TICK = 20;			// 20ms 
	
	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
	private UITimer timer;
	
	// Commands and buttons
	private CheckBox checkBoxCommand;
	private SoundCommand sCommand;
	private AccelerateCommand aCommand;
	private AboutCommand abCommand;
	private ExitCommand qCommand;
	private HelpCommand hCommand;
	private Button accButton;
	private LeftTurnCommand ltCommand;
	private Button ltButton;
	private BreakCommand bCommand;
	private Button breakButton;
	private RightTurnCommand rCommand;
	private Button rightButton;
	private PauseCommand pauseCommand;
	private Button pauseButton;
	private PositionCommand positionCommand;
	private Button positionButton;
	
	
	public Game() {
		this.setLayout(new BorderLayout());		// form uses border layout
		
		gw = new GameWorld();
		mv = new MapView();
		sv = new ScoreView();
		gw.addObserver(mv);		// register MapView to gameWorld
		gw.addObserver(sv);		// register ScoreView to gameWorld
		
		mv.setHeight(height);		// set MapView height
		mv.setWidth(width);			// set MapView width
		
		this.add(BorderLayout.NORTH, sv);		// ScoreView placed at the north region
		this.add(BorderLayout.CENTER, mv);		// MapView placed at the center
		
		// set up Command keys
		setUpTitleBar();		// generate title bar area
		setUpEast();			// generate east region
		setUpWest();			// generate west region
		setUpSouth();			// generate south region
		
		gw.init();
		this.show();
		gw.createSound();
		revalidate();
		timer = new UITimer(this);
		timer.schedule(GAME_TICK, true, this);
		 
		// Set up GUI
		System.out.println("Game GUI Setup Completed with the following stats :");
		System.out.println("Game World size : "+ GameWorld.getGameHeight() + ","+GameWorld.getGameWidth());
		System.out.println("Form Content pane size : " + this.getWidth() + "," + this.getHeight());
		System.out.println("MapView size : " + mv.getWidth() + "," + mv.getHeight());
		System.out.println("MapView Origin : " + mv.getX() + "," + mv.getY());
		
		GameWorld.setGameWidth(mv.getWidth());
		GameWorld.setGameHeight(mv.getHeight());
		
		mv.setMapViewOrigin(new Point(mv.getX(), mv.getY()));
		MapView.setMapHeight(mv.getHeight());
		MapView.setMapWidth(mv.getWidth());
	}
	
	
	// Title bar has title, side menu and right 
	private void setUpTitleBar() {
		Toolbar tb = new Toolbar();
		this.setToolbar(tb);
		tb.setTitle("ThePath Game");		// set title name
		
		// sound
		checkBoxCommand = new CheckBox();		// create a check box
		checkBoxCommand.getAllStyles().setBgTransparency(255);
		checkBoxCommand.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		sCommand = new SoundCommand(gw);
		checkBoxCommand.setCommand(sCommand);			// listen for SoundCommand
		tb.addComponentToSideMenu(checkBoxCommand);			// add check box to side menu
		
		// acceleration 
		aCommand = new AccelerateCommand(gw);
		tb.addCommandToSideMenu(aCommand);
		
		// About page
		abCommand = new AboutCommand();
		tb.addCommandToSideMenu(abCommand);
		
		// Exit
		qCommand = new ExitCommand();
		tb.addCommandToSideMenu(qCommand);
		
		// Help
		hCommand = new HelpCommand();
		tb.addCommandToRightBar(hCommand);
	}
	
	// East/right region
	private void setUpEast() {
		Container eastContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));			// vertical
		eastContainer.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.rgb(0, 0, 0)));		// black border
		
		// Break button
		bCommand = new BreakCommand(gw);
		breakButton = new Button(bCommand);		// breakButton perform bCommand
		breakButton = topButton(breakButton);		// decorate button
		eastContainer.add(breakButton);
		addKeyListener('b', bCommand);		// when user press b, bCommadn performed
		
		// Right button
		rCommand = new RightTurnCommand(gw);
		rightButton = new Button(rCommand);
		rightButton = bottomButton(rightButton);		// decorate button
		eastContainer.add(rightButton);
		addKeyListener('r', rCommand);			// press 'r' to perform rCommand
		
		this.add(BorderLayout.EAST, eastContainer);		// add this container to east region
	}
	
	// West/left region
	private void setUpWest() {
		Container westContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));		// vertical
		westContainer.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.rgb(0, 0, 0)));
		
		// Accelerate button
		aCommand = new AccelerateCommand(gw);
		accButton = new Button(aCommand);
		accButton = topButton(accButton);
		westContainer.add(accButton);
		addKeyListener('a', aCommand);		// press 'a' to accelerate
		
		// Left button
		ltCommand = new LeftTurnCommand(gw);
		ltButton = new Button(ltCommand);
		ltButton = bottomButton(ltButton);
		westContainer.add(ltButton);
		addKeyListener('l', ltCommand);
		
		this.add(BorderLayout.WEST, westContainer);
	}
	
	// South/bottom region
	private void setUpSouth() {
		Container southContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));		// horizontal
		southContainer.getAllStyles().setBorder(Border.createLineBorder(ColorUtil.rgb(0, 0, 0)));
		
		// Pause Command
		pauseButton = new Button("Pause");
		pauseButton = lowButton(pauseButton);
		pauseButton.getAllStyles().setMarginLeft(845);
		pauseCommand = new PauseCommand(gw, this, pauseButton);
		pauseButton.setCommand(pauseCommand);
		southContainer.add(pauseButton);
		
		// Position Button
		positionCommand = new PositionCommand(gw);
		positionButton = new Button(positionCommand);
		positionButton = lowButton(positionButton);
		southContainer.add(positionButton);
		
		this.add(BorderLayout.SOUTH, southContainer);
	}
	
	
	// Decoration for top buttons on the side
	private Button topButton(Button b) {
		b.getAllStyles().setBgTransparency(255);
		b.getUnselectedStyle().setBgColor(ColorUtil.BLUE);			// blue color when unselected
		b.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));	
		b.getAllStyles().setBorder(Border.createLineBorder(5, ColorUtil.rgb(0, 0, 0)));				// black border line
		b.getAllStyles().setMarginTop(100);
		b.getAllStyles().setPadding(TOP, 5);
		b.getAllStyles().setPadding(BOTTOM, 5);
		return b;
	}
	
	// Decoration for bottom buttons on the side
	private Button bottomButton(Button b) {
		b.getAllStyles().setBgTransparency(255);
		b.getUnselectedStyle().setBgColor(ColorUtil.BLUE);			// blue color when unselected
		b.getAllStyles().setBorder(Border.createLineBorder(5, ColorUtil.rgb(0, 0, 0)));			// black border line
		b.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		b.getAllStyles().setPadding(TOP, 5);
		b.getAllStyles().setPadding(BOTTOM, 5);	
		return b;
	}
	
	// Decoration for button on the bottom
	private Button lowButton(Button b) {
		b.getAllStyles().setBgTransparency(255);
		b.getUnselectedStyle().setBgColor(ColorUtil.BLUE);
		b.getAllStyles().setBorder(Border.createLineBorder(5, ColorUtil.rgb(0, 0, 0)));		// black border line
		b.getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255));
		b.getAllStyles().setPadding(TOP, 5);
		b.getAllStyles().setPadding(BOTTOM, 5);
		return b;
	}
	
	
	// when user press pause button
	public void pressedPause() {
		// If the game is paused
		if(!gw.getPause()) {
			timer.cancel();
			removeKeyListener('a', aCommand);		// disable 'a' key function
			removeKeyListener('b', bCommand);		// disable 'b' key function
			removeKeyListener('l', ltCommand);		// disable 'l' key function
			removeKeyListener('r', rCommand);		// disable 'r' key function
			aCommand.setEnabled(false);
			bCommand.setEnabled(false);
			ltCommand.setEnabled(false);
			rCommand.setEnabled(false);
			accButton.setEnabled(false);		// disable acceleration button
			breakButton.setEnabled(false);		// disable break button
			ltButton.setEnabled(false);			// disable left turn button
			rightButton.setEnabled(false);		// disable right turn button
			positionCommand.setEnabled(true);
			positionButton.setEnabled(true);	// enable position button
			gw.turnOffBackgroundSound();		// turn off background sound
			gw.setPause(!gw.getPause());		// update pause status
		}
		// if the game is resume
		else {
			timer.schedule(GAME_TICK, true, this);
			addKeyListener('a', aCommand);
			addKeyListener('b', bCommand);
			addKeyListener('l', ltCommand);
			addKeyListener('r', rCommand);
			aCommand.setEnabled(true);
			bCommand.setEnabled(true);
			ltCommand.setEnabled(true);
			rCommand.setEnabled(true);
			accButton.setEnabled(true);		// enable acceleration button
			breakButton.setEnabled(true);		// enable break button
			ltButton.setEnabled(true);			// enable left turn button
			rightButton.setEnabled(true);		// enable right turn button
			positionCommand.setEnabled(false);
			positionButton.setEnabled(false);	// disable position button
			gw.turnOnBackgroundSound();		// turn on background sound
			gw.setPause(!gw.getPause());	// update pause status
		}
	}
	
	@Override
	public void run() {
		gw.clockTick();
	}



}