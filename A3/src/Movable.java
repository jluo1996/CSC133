package com.mycompany.a3;

import com.codename1.charts.models.*;

public abstract class Movable extends GameObject{
	private int heading;
	private int speed;
	
	// Constructor with given color and game world object
	Movable(int c, GameWorld newGw){
		super(c, newGw);
	}
	
	// Constructor with given color, size and game world object
	Movable(int c, int s, GameWorld newGW){
		super(c, s, newGW);
	}
	
	// get heading
	public int getHeading() {
		return heading;
	}
	
	// get speed
	public int getSpeed() {
		return speed;
	}
	
	
	// set heading with given integer
	public void setHeading(int newHeading) {
		// handle degree greater than 360
		while(newHeading >= 360) {
			newHeading -= 360;
		}
		// handle degree less than 0
		while(newHeading < 0) {
			newHeading += 360;
		}
		
		heading = newHeading;
	}
	
	// set speed with given integer
	public void setSpeed(int newSpeed) {
		speed = newSpeed;
	}

	public void move() {
		float newX = this.getX() + (float)Math.cos(Math.toRadians(90 - this.getHeading())) * speed;
		float newY = this.getY() + (float)Math.sin(Math.toRadians(90 - this.getHeading())) * speed;
		int offset = 50;
		
		int originalX = (int)MapView.getMapViewOrigin().getX();
		int originalY = (int)MapView.getMapViewOrigin().getY();
		
		// Right side
		if(originalX + newX + offset >= MapView.getMapWidth() + originalX && (heading != 0 || heading != 180)) {
			setHeading(360 - heading);
		}
		
		// Left side
		// if it is a spider moving
		if(this instanceof Spider) {
			if(originalX + newX <= originalX + 20 && (heading != 0 || heading != 180) ) {
				setHeading(360 - heading);
			}
		}
		// if it is an Ant moving
		else {
			if(originalX + newX <= originalX && (heading != 0 || heading != 180)) {
				setHeading(360 - heading);
			}
		}
		
		// Bottom 
		if(originalY + newY + offset >= originalY + GameWorld.getGameHeight()) {
			setHeading((360 - heading + 180) % 360);
		}
		if(this instanceof Spider) {
			if(originalY + newY <= originalY + 25) {
				setHeading((360 - heading + 180) % 360);
			}
		}
		
		// Top
		else {
			if(originalY + newY <= originalY) {
				setHeading((360 - heading + 180) & 360);
			}
		}
		
		this.setLocation(new Point(newX, newY));
	}
}
