package com.mycompany.a1;

import java.util.Random;
import java.util.ArrayList;
import com.codename1.charts.util.ColorUtil;

public class GameWorld {
	private int lifeCount = 3;		// begins with 3 lives
	final private int gameWorldWidth = 1000;		// game world width is constant to 1000
	final private int gmaeWorldHeight = 1000;		// game world height is constant to 1000
	final private int blackColor = ColorUtil.BLACK;
	final private int greenColor = ColorUtil.GREEN;
	final private int redColor = ColorUtil.rgb(255, 0, 0);		// red color
	final private int blueColor = ColorUtil.BLUE;
	private int timer = 0;			// keep track of the clock
	private ArrayList<GameObject> gameObjects;		// a collection of game objects
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
		gameObjects = new ArrayList<GameObject>();		// collection of game objects
		flagCount = 0;			// flag count initiation
		foodStationCount = 0;	// food station count initiation
		spiderCount = 0;		// spider count initiation
		gameOver = false;		// game begins
		addGameObjects();		// generate game objects in the game
	}
	
	// Adding game objects to the world
	public void addGameObjects() {
		int newFlagCount = 4 + rand.nextInt(5);	// 4 flags		// changeable later
		int newFoodStationCount = 2;		// 2 food stations		// changeable later
		int newSpiderCount = 2;			// 2 spiders		// changeable later
		int randomFlagSize = rand.nextInt(40) + 10;	// flag size (10-50)
		
		// add Flag objects into the collection
		for(int i = 0; i < newFlagCount; i++) {
			gameObjects.add(new Flag(randomFlagSize, blueColor, i));	// flags are blue
			flagCount++;
		}
		
		// add an ant object into the collection
		gameObjects.add(new Ant(redColor));		// Ant is red
		// ant's initial location is same as flag#1
		gameObjects.get(flagCount).setLocation(gameObjects.get(0).getLocation());;
		
		// add Spider objects into the collection
		for(int i = 0; i < newSpiderCount; i++) {
			gameObjects.add(new Spider(blackColor));		// spiders are black
			spiderCount++;
		}
		
		// add FoodStation objects into the collection
		for(int i = 0; i < newFoodStationCount; i++) {
			gameObjects.add(new FoodStation(greenColor));		// food stations are green
			foodStationCount++;
		}
	}
	
	// add new randomly-specified size and location food station
	public void addFoodStation() {
		gameObjects.add(new FoodStation(greenColor));		// food stations are green
		foodStationCount++;
	}
	
	// Methods for command input
	
	// press 'a' for acceleration
	public void increaseSpeed() {
		System.out.println("Ant speeds up!!!\n");
		for(int i = 0; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof Ant) {
				((Ant)gameObjects.get(i)).accelerate();		// call accelerate() for the Ant
			}
		}		
	}
	
	// press 'b' for brake
	public void decreaseSpeed() {
		System.out.println("Ant speeds down!!!\n");
		for(int i = 0; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof Ant) {
				((Ant)gameObjects.get(i)).brake();		// call brake() for the Ant
			}
		}		
	}
	
	// press 'l' to change ant's heading by 5 degrees to the left
	public void headLeft() {
		System.out.println("Ant has turned left!!!\n");
		for(int i = 0; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof Ant) {
				((Ant)gameObjects.get(i)).turnLeft();		// call turnLeft() for the Ant
			}
		}		
	}
	
	// press 'r' to change ant's heading by 5 degrees to the right
	public void headRight() {
		System.out.println("Ant has turned Right!!!\n");
		for(int i = 0; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof Ant) {
				((Ant)gameObjects.get(i)).turnRight();		// call turnRight() for the Ant
			}
		}		
	}
	
	// press 'a number between 1-9'
	// pretend the ant has collided with the flag number x (1-9)
	public void flagCollision(int x) {
		System.out.println("The collision between the Ant with a flag has occured!!!");
		for(int i = 0; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof Ant) {
				if(((Ant)gameObjects.get(i)).getLastFlagReached() == (x - 1)){
					System.out.println("The Ant has reached the next flag!!!\n");
					((Ant)gameObjects.get(i)).newFlagReached();		// lastFlagReached++
					
					// if we reach the last flag
					if(((Ant)gameObjects.get(i)).getLastFlagReached() == flagCount) {
						// print game over and total time
						System.out.println("Game over, you win! Total time: #" + timer);	
						endGame();		// exit game
					}
				}
				else {
					System.out.println("The flag has been passed or haven't been reached yet!!!\n");
				}
			}
		}
	}
	
	// press 'f'
	// Pretend the Ant has collided with a food station
	public void foodStationCollision() {
		System.out.println("The collision between the Ant with a food station has occured!!!\n");
		for(int i = 0; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof Ant) {
				for(int j = 0; j < gameObjects.size(); j++) {
					if(gameObjects.get(j) instanceof FoodStation) {
						
						// get the capacity of the food station
						int tempFood = ((FoodStation)gameObjects.get(j)).getCapacity();
						
						// if the food station has food
						if(tempFood > 0) {
							// let the Ant consume the food
							((Ant)gameObjects.get(i)).addFoodLevel(tempFood);
							
							// empty out the food station
							((FoodStation)gameObjects.get(j)).emptyCapacity();	
							// and fade the color to light green
							((FoodStation)gameObjects.get(j)).setColor(ColorUtil.rgb(125, 255, 125));
							// add a new food station
							addFoodStation();
							
							return;	// stop searching for non-empty food stations
						}
					}
				}
			}
		}
	}
	
	// press 'g'
	// Pretend a spider has gotten to the same location
	// and collided with the Ant
	public void spiderCollision() {
		System.out.println("The collision between the Ant with a spider has occured!!!\n");
		for(int i = 0; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof Ant) {
				((Ant)gameObjects.get(i)).lostHealth();  	// the Ant lost 1 health
				
				((Ant)gameObjects.get(i)).checkHealthLevel();	// update health level
				// check if Ant is dead
				if(((Ant)gameObjects.get(i)).checkDead()) {
					decLifeCount();
					return;
				}
				
				int temp = 10 - ((Ant)gameObjects.get(i)).getHealthLevel();
				int newColor = ColorUtil.rgb(255, temp, temp);	// faded red by adding more blue and green to it
				((Ant)gameObjects.get(i)).setColor(newColor);		// fade Ant's color
			}
		}
		
		
	}
	
	// press 't' to tick the world clock
	// In charge of updating Ant and Spider's status
	public void clockTick() {
		System.out.println("Clock has ticked!!!\n");
		incTimer();	// game clock is incremented by one
		for(int i = 0; i < gameObjects.size(); i++) {
			// if it's a Spider
			if(gameObjects.get(i) instanceof Spider) {
				do {
					((Spider)gameObjects.get(i)).changeHeading();		// update it's heading
					((Spider)gameObjects.get(i)).move();	// update its location
				} while(((Spider)gameObjects.get(i)).checkOutOfBound());	// keep going if it's out of bound
			}
			
			// if it's an Ant
			if(gameObjects.get(i) instanceof Ant) {
				((Ant)gameObjects.get(i)).dropFoodLevel();		// auto drop Ant's food level
				((Ant)gameObjects.get(i)).checkFoodLevel();		// check its food level
				((Ant)gameObjects.get(i)).checkHealthLevel();	// check its health level\
				
				// if the Ant is not dead
				if(!((Ant)gameObjects.get(i)).checkDead()){
					for(int j = 0; j < gameObjects.size(); j++) {
						((Ant)gameObjects.get(i)).move();	// update Ant's location
					}
				}
				// if the Ant is dead
				else {
					decLifeCount();	// lose 1 life
					return;
				}
			}
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
		for(int i = 0; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof Ant) {
				System.out.println("Last Flag Number Reached: " + ((Ant)gameObjects.get(i)).getLastFlagReached());
				System.out.println("Food Level: " + ((Ant)gameObjects.get(i)).getFoodLevel());
				System.out.println("Health Level: " + ((Ant)gameObjects.get(i)).getHealthLevel() + "\n");
			}
		}
	}
	
	
	// press 'm' to print a world map
	public void printMap() {
		for(int i = 0; i < gameObjects.size(); i++) {
			if(gameObjects.get(i) instanceof Flag) {
				System.out.println(((Flag)gameObjects.get(i)).toString());
			}
			if(gameObjects.get(i) instanceof Ant) {
				System.out.println(((Ant)gameObjects.get(i)).toString());
			}
			if(gameObjects.get(i) instanceof Spider) {
				System.out.println(((Spider)gameObjects.get(i)).toString());
			}
			if(gameObjects.get(i) instanceof FoodStation) {
				System.out.println(((FoodStation)gameObjects.get(i)).toString());
			}
		}
		System.out.println("\n");
	}
	
	// press 'x' to request exit
	
	// press 'y' to confirm exit
	
	// press 'n' to decline exit
	
	// get life count
	public int getLifeCount() {
		return lifeCount;
	}
	
	// set life count
	private void setLifeCount(int newLifeCount) {
		lifeCount = newLifeCount;
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
	}
	
	// increment timer
	private void incTimer() {
		timer++;
	}
	
	// end game
	public void endGame() {
		gameOver = true;
		System.exit(0);		// exit 
	}
}
