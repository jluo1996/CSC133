package com.mycompany.a3;

import java.util.Random;
import java.util.ArrayList;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Display;
import com.codename1.ui.Graphics;
import java.util.Observable;
import java.util.Iterator;
import com.codename1.charts.models.*;

public class GameWorld extends Observable {
	private int lifeCount;		
	final private int blackColor = ColorUtil.BLACK;
	final private int greenColor = ColorUtil.GREEN;
	final private int redColor = ColorUtil.rgb(255, 0, 0);		// red color
	final private int blueColor = ColorUtil.BLUE;
	private int timer;			// keep track of the clock
	private GameObjectCollection gameObjectCollection;	// a collection of game objects
	private boolean soundOn;		// sound status
	private int flagCount;		// count how many flags exist
	private int foodStationCount;	// count how many food stations exist
	private int spiderCount;	// count how many spiders exist
	private boolean gameOver;		// game over status
	private static int gameHeight = 1000;
	private static int gameWidth = 1000;
	Random rand = new Random();
	
	private boolean position;
	private boolean pause;
	
	// Sounds
	private Sound antSound;
	private Sound spiderSound;
	private Sound foodStationSound;
	private Sound flagSound;
	private Sound lifeSound;
	private BGSound backgroundSound;

	
	// initial game objects/setup
	public void init() {
		this.timer = 0;
		this.lifeCount = 3;		// begins with 3 lives
		pause = false;
		this.soundOn = false;
		gameObjectCollection = new GameObjectCollection();		// collection of game objects
		flagCount = 0;			// flag count initiation
		foodStationCount = 0;	// food station count initiation
		spiderCount = 0;		// spider count initiation
		gameOver = false;		// game begins
		addGameObjects();		// generate game objects in the game
		this.setChanged();
		this.notifyObservers(this);
	}
	
	// Adding game objects to the world
	public void addGameObjects() {
		int newFlagCount = 4;	// 4 flags		// changeable later	
		int newFoodStationCount = 2;		// 2 food stations		// changeable later
		int newSpiderCount = 2;			// 2 spiders		// changeable later
		
		// Add new ant to the game collection
		gameObjectCollection.add(Ant.getAnt(this));
		
		// add Flag objects into the collection
		for(int i = 0; i < newFlagCount; i++) {
			gameObjectCollection.add(new Flag(i, this));	// flags are blue
			flagCount++;
		}
		
		// add Spider objects into the collection
		for(int i = 0; i < newSpiderCount; i++) {
			gameObjectCollection.add(new Spider(this));		// spiders are black
			spiderCount++;
		}
		
		// add FoodStation objects into the collection
		for(int i = 0; i < newFoodStationCount; i++) {
			gameObjectCollection.add(new FoodStation(this));		// food stations are green
			foodStationCount++;
		}
	}
	
	// add new randomly-specified size and location food station
	public void addFoodStation() {
		gameObjectCollection.add(new FoodStation(this));		// food stations are green
		foodStationCount++;
	}
	
	/* Methods for command input */
	
	// press 'a' for acceleration
	public void increaseSpeed() {
		IIterator itr = gameObjectCollection.getIterator();
		while(itr.hasNext()) {
			GameObject tempObject = itr.getNext();
			if(tempObject instanceof Ant) {
				((Ant)tempObject).accelerate();			// call accelerate() for the Ant
				break;			// exit the loop since there's only one Ant
			}			
		}
		
		this.setChanged();
		this.notifyObservers(this);
	}
	
	// press 'b' for brake
	public void decreaseSpeed() {
		IIterator itr = gameObjectCollection.getIterator();
		while(itr.hasNext()) {
			GameObject tempObject = itr.getNext();
			if(tempObject instanceof Ant) {
				((Ant)tempObject).brake();		// call brake() for the Ant
				break;			// exit the loop since there's only one Ant
			}
		}
		
		this.setChanged();
		this.notifyObservers(this);
	}
	
	// press 'l' to change ant's heading by 5 degrees to the left
	public void headLeft() {
		IIterator itr = gameObjectCollection.getIterator();
		while(itr.hasNext()) {
			GameObject tempObject = itr.getNext();
			if(tempObject instanceof Ant) {
				((Ant)tempObject).turnLeft();			// call turnLeft() for the Ant
				break;			// exit the loop since there's only one Ant
			}
		}

		this.setChanged();
		this.notifyObservers(this);
	}
	
	// press 'r' to change ant's heading by 5 degrees to the right
	public void headRight() {
		IIterator itr = gameObjectCollection.getIterator();
		while(itr.hasNext()) {
			GameObject tempObject = itr.getNext();
			if(tempObject instanceof Ant){
				((Ant)tempObject).turnRight();	// call turnRight() for the Ant
				break;			// exit the loop since there's only one Ant
			}
		}
		
		this.setChanged();
		this.notifyObservers(this);
	}
	
	// press 'a number between 1-9'
	// pretend the ant has collided with the flag number x (1-9)
	public void flagCollision(int x) {
		IIterator itr = gameObjectCollection.getIterator();
		while(itr.hasNext()) {
			GameObject tempObject = itr.getNext();
			if(tempObject instanceof Ant) {
				if(((Ant)tempObject).getLastFlagReached() == (x - 1)) {
					System.out.println("The Ant has reached the next flag!!!\n");
					((Ant)tempObject).newFlagReached();  		// lastFlagReached++
					
					// if we reach the last flag
					if(((Ant)tempObject).getLastFlagReached() == flagCount) {
						// print game over and total time
						System.out.println("Game over, you win! Total time: #" + timer);
						endGame();		// exit game
					}
				}
				else {
					System.out.println("The flag has been passed or haven't been reached yet!!!\n");
				}
				break;			// exit the loop since there's only one Ant
			}
			
		}
		
		this.setChanged();
		this.notifyObservers(this);
	}
	
	// press 'f'
	// Pretend the Ant has collided with a food station
	public void foodStationCollision() {
		System.out.println("The collision between the Ant with a food station has occured!!!\n");
		IIterator itr = gameObjectCollection.getIterator();
		while(itr.hasNext()) {
			GameObject tempObject = itr.getNext();		// tempObject contain the Ant
			if(tempObject instanceof Ant) {
				IIterator itr2 = gameObjectCollection.getIterator();
				while(itr2.hasNext()) {
					GameObject tempObject2 = itr2.getNext();		// tempObject2 contain a FoodStation
					if(tempObject2 instanceof FoodStation) {
						// get the capacity of the food station
						int tempFood = ((FoodStation)tempObject2).getCapacity();
						
						// if the food station has food
						if(tempFood > 0) {
							// let the Ant consume the food
							((Ant)tempObject).addFoodLevel(tempFood);
							//empty out the food station
							((FoodStation)tempObject2).emptyCapacity();
							// and fade the color to light green
							((FoodStation)tempObject2).setColor(ColorUtil.rgb(125, 255, 125));
							// add a new food station
							addFoodStation();
							
							break;	// stop searching for a non-empty food station
						}
					}
				}
				break;	// since there's only one Ant
			}
		}
		
		this.setChanged();
		this.notifyObservers(this);
	}
	
	// press 'g'
	// Pretend a spider has gotten to the same location
	// and collided with the Ant
	public void spiderCollision() {
		System.out.println("The collision between the Ant with a spider has occured!!!\n");
		IIterator itr = gameObjectCollection.getIterator();
		while(itr.hasNext()) {
			GameObject tempObject = itr.getNext();			// tempObject is the Ant
			if(tempObject instanceof Ant) {
				((Ant)tempObject).lostHealth();			// the Ant lost 1 health
			
				((Ant)tempObject).checkHealthLevel();		// update health level
				// check if Ant is dead
				if(((Ant)tempObject).checkDead()) {
					decLifeCount();
					break;
				}
				
				int temp = 10 - ((Ant)tempObject).getHealthLevel();
				int newColor = ColorUtil.rgb(255, temp, temp);		// faded red by adding more blue and green to it
				((Ant)tempObject).setColor(newColor);			// fade Ant's color
				
				break;	// since there's only one Ant	
			}
		}
		
		this.setChanged();
		this.notifyObservers(this);
	}
	
	// press 't' to tick the world clock
	// In charge of updating Ant and Spider's status
	public void clockTick() {
		System.out.println("Clock has ticked!!!\n");
		incTimer();	// game clock is incremented by one
		
		IIterator itr = gameObjectCollection.getIterator();
		while(itr.hasNext()) {
			GameObject tempObject = itr.getNext();		
			// if it is an spider
			if(tempObject instanceof Spider) {
				((Spider)tempObject).move();			// update its location
			}
			
			// if it's an Ant
			else if(tempObject instanceof Ant) {
				((Ant)tempObject).dropFoodLevel();			// auto drop Ant's food level
				((Ant)tempObject).checkFoodLevel();			// check its food level
				((Ant)tempObject).checkHealthLevel();		// check its health level
				
				// if the Ant is not dead
				if(!((Ant)tempObject).checkDead()) {
					((Ant) tempObject).move();		// update Ant's location
				}
				// if the Ant is dead
				else {
					decLifeCount();		// lose 1 life
				}
			}
		}
		
		checkCollision();
		if(getSoundOn()) {
			backgroundSound.play();
		}
		this.setChanged();
		this.notifyObservers(this);
	}

	// To switch position button
	public void turnOnPostion() {
		if(position == true) {
			position = false;
		}
		else {
			position = true;
		}
	}
	
	// get game height
	public static int getGameHeight() {
		return gameHeight;
	}
	
	// set game height
	public static void setGameHeight(int newHeight) {
		gameHeight = newHeight;
	}
	
	// get game width
	public static int getGameWidth() {
		return gameWidth;
	}
	
	// set game width
	public static void setGameWidth(int newWidth) {
		gameWidth = newWidth;
	}
	
	// get life count
	public int getLifeCount() {
		return lifeCount;
	}
	
	// set life count
	private void setLifeCount(int newLifeCount) {
		lifeCount = newLifeCount;
		this.setChanged();
		this.notifyObservers(this);
	}
	
	// decrement life count
	public void decLifeCount() {
		lifeCount--;
		System.out.println("You has lost 1 life!!!");
		// check if any lives left
		if(getLifeCount() == 0) {
			System.out.println("Game over, you failed!!!");
			endGame();
		}
		init();	// re-initialize the game world
	}
	
	//get timer
	public int getTimer() {
		return timer;
	}
	
	// increment timer
	private void incTimer() {
		timer++;
		this.setChanged();
		this.notifyObservers(this);
	}
	
	// end game
	public void endGame() {
		gameOver = true;
		Display.getInstance().exitApplication();		// close the app
	}
	
	public GameObjectCollection getCollection() {
		return gameObjectCollection;
	}
	
	// get sound status
	public boolean getSoundOn() {
		return soundOn;
	}
	
	// set sound status
	public void setSoundOn(boolean s) {
		this.soundOn = s;
		this.setChanged();
		this.notifyObservers(this);
	}
	
	// get pause status
	public boolean getPause() {
		return pause;
	}
	
	// set pause status
	public void setPause(boolean newStatus) {
		pause = newStatus;
	}
	
	// get flag count
	public int getFlagCount() {
		return flagCount;
	}
	
	// handle fixed object (selectable) 
	// clickPoint is the point user clicks, original point is the original point of the object
	public void click(Point clickPoint, Point originalPoint) {
		IIterator tempObject = gameObjectCollection.getIterator();
		while(tempObject.hasNext()) {
			// if this game object is a Fixed object
			if(tempObject.getNext() instanceof Fixed) {
				Fixed temp = (Fixed)tempObject.getCurrent();
				// if this fixed object is selected
				if(position && temp.isSelected()) {
					float newX = clickPoint.getX() - originalPoint.getX();
					float newY = clickPoint.getY() - originalPoint.getY();
					Point tempPoint = new Point(newX, newY);
					temp.setLocation(tempPoint);
					position = false;
					temp.setSelected(false);		// dis-select the object
				}
				else {
					if(temp.contains(clickPoint, originalPoint)) {
						temp.setSelected(true);
					}
					else {
						temp.setSelected(false);
					}
				}
			}
		}
		
		this.setChanged();
		this.notifyObservers(this);
	}

	// create sound for all objects including background
	public void createSound() {
		antSound = new Sound("ant.wav");
		spiderSound = new Sound("spider.wav");
		foodStationSound = new Sound("foodStation.wav");
		flagSound = new Sound("flag.wav");
		lifeSound = new Sound("life.wav");
		backgroundSound = new BGSound("background2.wav");
	}
	
	// Turn on background sound
	public void turnOffBackgroundSound() {
		backgroundSound.pause();
	}
	
	// Turn on background sound
	public void turnOnBackgroundSound() {
		// if sound is selected on
		if(getSoundOn()) {
			backgroundSound.play();		// play it
		}
	}
		
	// check collisions
	public void checkCollision() {
		IIterator iter = gameObjectCollection.getIterator();
		while(iter.hasNext()) {
			GameObject tempObject = iter.getNext();
			IIterator iter2 = gameObjectCollection.getIterator();
			while(iter2.hasNext()) {
				GameObject tempObject2 = iter2.getNext();
				if(tempObject != tempObject2) {
					if(tempObject.collidesWith(tempObject2)) {
						// if the sound setting is on
						if(getSoundOn()) {
							// if the collision is between Ant and spider
							if(tempObject instanceof Spider && tempObject2 instanceof Ant) {
								spiderSound.play();		// play spider collision sounds
							}
							// if the collision is between Ant and flag
							else if(tempObject instanceof Flag && tempObject instanceof Ant) {
								flagSound.play();		// play flag collision sound
							}
							// if the collision is between Ant and food station
							else if(tempObject instanceof FoodStation && tempObject2 instanceof Ant) {
								foodStationSound.play();		// play food station collision sound
							}
						}
						tempObject.handleCollision(tempObject2);
					}
				}
			}
		}
	}

}