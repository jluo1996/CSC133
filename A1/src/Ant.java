package com.mycompany.a1;

import com.codename1.charts.models.*;
import com.codename1.charts.util.ColorUtil;

public class Ant extends Movable implements ISteerable{
	private int maximumSpeed; 		// max speed defined when create
	private int foodLevel;			// how hungry it is
	private int foodConsumptionRate;
	private int healthLevel;
	private int lastFlagReached;
	private boolean dead;			// keep track if the ant is dead
	
	// Constructor
	Ant(){
		super();
		setHeading(0);		// initialize heading to zero
		maximumSpeed = 50;			// max speed is 50 
		foodLevel = 30; 			// food level is 30 initially
		foodConsumptionRate = 2;	// spend 2 units of food each clock 
		healthLevel = 10;			// health is 10 initially
		lastFlagReached = 1;		// begin at flag#1
		dead = false;				// new ant is not dead
	}
	
	// Constructor with given color
	Ant(int c){
		super(c);
		maximumSpeed = 50;			// max speed is 50 
		foodLevel = 30; 			// food level is 30 initially
		foodConsumptionRate = 2;	// spend 2 units of food each clock 
		healthLevel = 10;			// health is 10 initially
		lastFlagReached = 1;		// begin at flag#1
		dead = false;				// new ant is not dead
	}
	
	// Constructor with give size, color, location
	// Initially heading is 0 and speed is 10
	// An object begins with 50 max speed, 30 food level, 
	// reduce 2 food each clock, 10 lives, begin at flag#1
	Ant(int s, int c, Point l){
		super(s, c, l, 0, 10);		// initially heading is 0 and speed is 10
		maximumSpeed = 50;			// max speed is 50 
		foodLevel = 30; 			// food level is 30 initially
		foodConsumptionRate = 2;	// spend 2 units of food each clock 
		healthLevel = 10;			// health is 10 initially
		lastFlagReached = 1;		// begin at flag#1
		dead = false;				// new ant is not dead
	}
	
	// get max speed
	public int getMaxSpeed() {
		return maximumSpeed;
	}
	
	// get food level
	public int getFoodLevel() {
		return foodLevel;
	}
	
	// get food consumption rate
	public int getFoodConsumptionRate() {
		return foodConsumptionRate;
	}
	
	// get health level
	public int getHealthLevel() {
		return healthLevel;
	}
	
	// get last flag reached
	public int getLastFlagReached() {
		return lastFlagReached;
	}
	
	// add food level
	public void addFoodLevel(int moreFood) {
		foodLevel += moreFood;
	}
	
	// drop food level
	public void dropFoodLevel() {
		foodLevel -= foodConsumptionRate;
	}
	
	// lose 1 health and adjust the speed according to health level
	public void lostHealth() {
		healthLevel--;
		
		// if current speed is greater than max speed times health level divide by 10
		while(getSpeed() > (getMaxSpeed() * getHealthLevel() / 10)){
			brake();
		}
	}
	
	// update flag reached
	public void newFlagReached() {
		lastFlagReached++;
	}
	
	// check whether food level is zero
	// if so, reduce speed to zero
	public void checkFoodLevel() {
		// if food level is zero, set speed to zero
		if(getFoodLevel() <= 0 ) {
			setSpeed(0);
			dead = true;		// ant dies
		}
	}
	
	// check whether health level is zero
	// if so, reduce speed to zero
	public void checkHealthLevel() {
		// if health level is zero, set speed to zero
		if(getHealthLevel() <= 0) {
			setSpeed(0);
			dead = true;		// ant dies
		}
	}
	
	// attempt to accelerate
	// add 1 unit per acceleration
	public void accelerate() {
		// check first to see if the ant is dead
		if(!dead) {		// if the ant is not dead
			// if current speed is less than max speed times health level divide by 10
			if(getSpeed() < (getMaxSpeed() * getHealthLevel() / 10)) {	
													// allow acceleration
				setSpeed(getSpeed() + 1);			// speed up 1 unit
			}
		}
		else {
			System.out.println("Cannot accelerate at this point!");
		}
	}
	
	// attempt to brake
	// reduce 1 unit per brake
	public void brake() {
		// check first to see if the ant is dead
		if(!dead) {		// if the ant is not dead
			if(getSpeed() > 0) {		// if current speed is greater than 0
										// allow brake
				setSpeed(getSpeed() - 1);	// reduce speed by 1 unit 				
			}
		}
	}
	
	// check if the ant is dead
	public boolean checkDead() {
		return dead;
	}
	
	
	// override the ToString method
	public String toString() {
		// initiation
		String antInfo = "Ant: ";
		
		// location
		antInfo += "loc=" + Math.round(((getLocation().getX())*10.0)/10.0) + "," + Math.round(((getLocation().getY())*10.0)/10.0);
		
		// color
		antInfo += " color=[" + ColorUtil.red(getColor()) + "," + ColorUtil.green(getColor()) + "," + ColorUtil.blue(getColor()) + "] ";
	
		// heading
		antInfo += "heading=" + getHeading();
		
		// speed
		antInfo += " speed=" + getSpeed();
		
		// size
		antInfo += " size=" + getSize();
		
		// maxSpeed
		antInfo += " maxSpeed=" + getMaxSpeed();
		
		// foodConsumptionRate
		antInfo += " foodConsumptionRate=" + getFoodConsumptionRate();
		
		return antInfo;
	}
	
	// Interface functions
	
	// turn left by 5 degrees
	public void turnLeft() {
		setHeading(getHeading() - 5);
	}
	
	// turn right by 5 degrees
	public void turnRight() {
		setHeading(getHeading() + 5);
	}
}
