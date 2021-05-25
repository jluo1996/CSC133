package com.mycompany.a2;

import java.util.Random;
import java.util.ArrayList;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Display;

import java.util.Observable;
import java.util.Iterator;

public class GameWorld extends Observable {
	private int lifeCount = 3;		// begins with 3 lives
	final private int blackColor = ColorUtil.BLACK;
	final private int greenColor = ColorUtil.GREEN;
	final private int redColor = ColorUtil.rgb(255, 0, 0);		// red color
	final private int blueColor = ColorUtil.BLUE;
	private int timer = 0;			// keep track of the clock
	private GameObjectCollection gameObjectCollection;	// a collection of game objects
	private boolean soundOn = false;		// sound status
	private int flagCount;		// count how many flags exist
	private int foodStationCount;	// count how many food stations exist
	private int spiderCount;	// count how many spiders exist
	private boolean gameOver;		// game over status
	Random rand = new Random();
	
	// Constructor
	public GameWorld() {
		
	}
	
	// initial game objects/setup
	public void init() {
		gameObjectCollection = new GameObjectCollection();		// collection of game objects
		//this.soundOn = false;	// default: sound off
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
		int newFlagCount = 4 + rand.nextInt(5);	// 4 flags		// changeable later
		int newFoodStationCount = 2;		// 2 food stations		// changeable later
		int newSpiderCount = 2;			// 2 spiders		// changeable later
		int randomFlagSize = rand.nextInt(40) + 10;	// flag size (10-50)
		
		// add Flag objects into the collection
		for(int i = 0; i < newFlagCount; i++) {
			gameObjectCollection.add(new Flag(randomFlagSize, blueColor, i));	// flags are blue
			flagCount++;
		}
		
		// add Spider objects into the collection
		for(int i = 0; i < newSpiderCount; i++) {
			gameObjectCollection.add(new Spider(blackColor));		// spiders are black
			spiderCount++;
		}
		
		// add FoodStation objects into the collection
		for(int i = 0; i < newFoodStationCount; i++) {
			gameObjectCollection.add(new FoodStation(greenColor));		// food stations are green
			foodStationCount++;
		}

		IIterator itr = gameObjectCollection.getIterator();
		while(itr.hasNext()) {
			GameObject tempObject = itr.getNext();
			if(tempObject instanceof Flag) {
				Ant ant = Ant.getAnt();
				ant.setColor(redColor);
				//Ant ant = new Ant(redColor);		// any is red
				ant.setLocation(tempObject.getLocation());
				// add an ant object into the collection
				gameObjectCollection.add(ant);
				break;		// no need to keep looking for the next flag since we only need the first flag
			}
		}
	}
	
	// add new randomly-specified size and location food station
	public void addFoodStation() {
		gameObjectCollection.add(new FoodStation(greenColor));		// food stations are green
		foodStationCount++;
	}
	
	/* Methods for command input */
	
	// press 'a' for acceleration
	public void increaseSpeed() {
		System.out.println("Ant speeds up!!!\n");
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
		System.out.println("Ant speeds down!!!\n");
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
		System.out.println("Ant has turned left!!!\n");
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
		System.out.println("Ant has turned Right!!!\n");
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
		System.out.println("The collision between the Ant with a flag has occured!!!");
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
			if(tempObject instanceof Spider) {
				do {
					((Spider)tempObject).changeHeading();			// update it's heading
					((Spider)tempObject).move();			// update its location
				} while(((Spider)tempObject).checkOutOfBound());			// keep going if it's out of bound
			}
			
			// if it's an Ant
			if(tempObject instanceof Ant) {
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
			
			this.setChanged();
			this.notifyObservers(this);
		}
	}

	/* press 'd' to display current game/Ant state values:
	 	1. the number of lives left
	 	2. the current clock value
	 	3. the highest flag number the ant has reached sequentially so far
	 	4. the ant's current food level
	 	5. the ant's health level	 */
	public void displayStatus() {
		System.out.println("Lives: " + getLifeCount());
		System.out.println("Time#: " + getTimer());
		
		IIterator itr = gameObjectCollection.getIterator();
		while(itr.hasNext()) {
			GameObject tempObject = itr.getNext();
			if(tempObject instanceof Ant) {
				System.out.println("Last Flag Number Reached: " + ((Ant)tempObject).getLastFlagReached());
				System.out.println("Food Level: " + ((Ant)tempObject).getFoodLevel());
				System.out.println("Health Level: " + ((Ant)tempObject).getHealthLevel() + "\n");
				break;		// since there is only one Ant
			}
		}
	}
	
	
	// press 'm' to print a world map
	public void printMap() {
		IIterator itr = gameObjectCollection.getIterator();
		while(itr.hasNext()) {
			GameObject tempObject = itr.getNext();
			if(tempObject instanceof Flag)
				System.out.println(((Flag)tempObject).toString());
			if(tempObject instanceof Ant)
				System.out.println(((Ant)tempObject).toString());
			if(tempObject instanceof Spider)
				System.out.println(((Spider)tempObject).toString());
			if(tempObject instanceof FoodStation)
				System.out.println(((FoodStation)tempObject).toString());
		}
		System.out.println("\n");
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
	private void decLifeCount() {
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
	
	// set timer
	private void setTimer(int newTime) {
		timer = newTime;
		this.setChanged();
		this.notifyObservers(this);
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
	
	// get flag count
	public int getFlagCount() {
		return flagCount;
	}
}