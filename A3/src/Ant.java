package com.mycompany.a3;

import com.codename1.charts.models.*;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Ant extends Movable implements ISteerable, IDrawable{
	private int maximumSpeed; 		// max speed defined when create
	private int foodLevel;			// how hungry it is
	private int foodConsumptionRate;
	private int healthLevel;
	private int lastFlagReached;
	private boolean dead;			// keep track if the ant is dead
	
	private static Ant theAnt;
	
	// Singleton Startegy
	private Ant(GameWorld newGW) {
		super(ColorUtil.rgb(255, 0, 0), newGW);
		super.setSize(100);
		this.setSpeed(0);
		this.setHeading(0);
		this.maximumSpeed = 20;
		this.foodConsumptionRate = 1;
		this.foodLevel = 2000;
		this.healthLevel = 10;
		this.lastFlagReached = 1;			// default with flag number 1
		this.dead = false;
	}
	
	public static Ant getAnt(GameWorld newGW) {
		if(theAnt == null) {
			theAnt = new Ant(newGW);
		}
		return theAnt;
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
	
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(super.getColor());

		int xLocation = (int)this.getLocation().getX() + (int)pCmpRelPrnt.getX();
		int yLocation = (int)this.getLocation().getY() + (int)pCmpRelPrnt.getY();	
		
		g.fillArc(xLocation, yLocation, this.getSize(), this.getSize(), 0, 360);		// draw a circle with diameter == its size
		g.setColor(ColorUtil.BLACK);
	
	}
	
	@Override
	public void handleCollision(GameObject object) {
		// do nothing
	}
}
