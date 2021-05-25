package com.mycompany.a1;

import com.codename1.charts.models.*;

public abstract class Movable extends GameObject{
	private int heading;
	private int speed;
	
	// Constructor with random heading and speed
	Movable(){
		super();
		heading = randomInt(0, 359);
		speed = randomInt(5, 10);
	}
	
	// Constructor with given color
	Movable(int c){
		super(c);
		heading = randomInt(0, 359);
		speed = randomInt(5, 10);
	}
	
	// Constructor with given size, color, location, heading, speed
	Movable(int s, int c, Point l, int h, int sp){
		super(s, c, l);
		heading = h;
		speed = s;
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
		heading = newHeading;
	}
	
	// set speed with given integer
	public void setSpeed(int newSpeed) {
		speed = newSpeed;
	}

	// tell the object to update its location based on its current heading and speed
	public void move() {
		// compute the change in X and Y
		int angle = 90 - heading;
		float deltaX = (((float)Math.cos(Math.toRadians(angle))) * speed);
		float deltaY = (((float)Math.sin(Math.toRadians(angle))) * speed);
		
		super.move(deltaX, deltaY);
	}
}
