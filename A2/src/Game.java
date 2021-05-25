package com.mycompany.a2;

import com.codename1.ui.Form;
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

public class Game extends Form {
	final int height = 1000;		// map height
	final int width = 1000;			// map width
	
	private GameWorld gw;
	private MapView mv;
	private ScoreView sv;
	
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
	}
	
	
	// Title bar has title, side menu and right 
	private void setUpTitleBar() {
		Toolbar tb = new Toolbar();
		this.setToolbar(tb);
		tb.setTitle("ThePath Game");		// set title name
		
		// sound
		CheckBox checkBoxCommand = new CheckBox();		// create a check box
		checkBoxCommand.getAllStyles().setBgTransparency(255);
		checkBoxCommand.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		SoundCommand sCommand = new SoundCommand(gw);
		checkBoxCommand.setCommand(sCommand);			// listen for SoundCommand
		tb.addComponentToSideMenu(checkBoxCommand);			// add check box to side menu
		
		// acceleration 
		AccelerateCommand aCommand = new AccelerateCommand(gw);
		tb.addCommandToSideMenu(aCommand);
		
		// About page
		AboutCommand abCommand = new AboutCommand();
		tb.addCommandToSideMenu(abCommand);
		
		// Exit
		ExitCommand qCommand = new ExitCommand();
		tb.addCommandToSideMenu(qCommand);
		
		// Help
		HelpCommand hCommand = new HelpCommand();
		tb.addCommandToRightBar(hCommand);
	}
	
	// East/right region
	private void setUpEast() {
		Container eastContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));			// vertical
		eastContainer.getAllStyles().setBorder(Border.createLineBorder(1, ColorUtil.rgb(0, 0, 0)));		// black border
		
		// Break button
		BreakCommand bCommand = new BreakCommand(gw);
		Button breakButton = new Button(bCommand);		// breakButton perform bCommand
		breakButton = topButton(breakButton);		// decorate button
		eastContainer.add(breakButton);
		addKeyListener('b', bCommand);		// when user press b, bCommadn performed
		
		// Right button
		RightTurnCommand rCommand = new RightTurnCommand(gw);
		Button rightButton = new Button(rCommand);
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
		AccelerateCommand aCommand = new AccelerateCommand(gw);
		Button accButton = new Button(aCommand);
		accButton = topButton(accButton);
		westContainer.add(accButton);
		addKeyListener('a', aCommand);		// press 'a' to accelerate
		
		// Left button
		LeftTurnCommand ltCommand = new LeftTurnCommand(gw);
		Button ltButton = new Button(ltCommand);
		ltButton = bottomButton(ltButton);
		westContainer.add(ltButton);
		addKeyListener('l', ltCommand);
		
		this.add(BorderLayout.WEST, westContainer);
	}
	
	// South/bottom region
	private void setUpSouth() {
		Container southContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));		// horizontal
		southContainer.getAllStyles().setBorder(Border.createLineBorder(ColorUtil.rgb(0, 0, 0)));
		
		// Collide with Flag button
		FlagCollisionCommand fcCommand = new FlagCollisionCommand(gw);
		Button fcButton = new Button(fcCommand);
		fcButton.getAllStyles().setMarginLeft(520);
		fcButton = lowButton(fcButton);
		southContainer.add(fcButton);
		
		// Collide with spider button
		SpiderCollisionCommand scCommand = new SpiderCollisionCommand(gw);
		Button scButton = new Button(scCommand);
		scButton = lowButton(scButton);
		southContainer.add(scButton);
		addKeyListener('g', scCommand);		// press 'g' to perform spider collision
		
		// Collide with food station button
		FoodStationCollisionCommand fscCommand = new FoodStationCollisionCommand(gw);
		Button fscButton = new Button(fscCommand);
		fscButton = lowButton(fscButton);
		southContainer.add(fscButton);
		addKeyListener('f', fscCommand);		// press 'f' to collide with food station
		
		// Tick button
		TickCommand tCommand = new TickCommand(gw);
		Button tButton = new Button(tCommand);
		tButton = lowButton(tButton);
		southContainer.add(tButton);
		addKeyListener('t', tCommand);		// press 't' to tick the clock
		
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
}