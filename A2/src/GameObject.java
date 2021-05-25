package com.mycompany.a2;

import com.codename1.charts.models.*;
import com.codename1.charts.util.ColorUtil;
import java.util.Random;


public abstract class GameObject {
	private int size;
	private int color;
	private Point location;
	
	// Constructor with random size, color and location
	GameObject(){
		size = randomInt(10, 50);			// size range 10-50
		color = ColorUtil.rgb(randomInt(0, 255), randomInt(0, 255), randomInt(0, 255));	// random color
		location = new Point(randomInt(0, 1000), randomInt(0, 1000));	// random location
	}
	
	// Constructor with given color
	GameObject(int c){
		size = randomInt(10, 50);			// size range 10-50
		color = c;
		location = new Point(randomInt(0, 1000), randomInt(0, 1000));	// random location
	}
	
	// Constructor with given size and color
	GameObject(int s, int c){
		size = s;
		color = c;
		location = new Point(randomInt(0, 1000), randomInt(0, 1000));	// random location
	}
	
	// constructor with given size, color and location
	GameObject(int s, int c, Point l){
		size = s;
		color = c;
		location = l;
	}
	
	// get current size of the object
	public int getSize() {
		return size;
	}
	
	// get current location of the object
	public Point getLocation() {
		return location;
	}
	
	// get current color of the object
	public int getColor() {
		return color;
	}
	
	// set new location with given point
	public void setLocation(Point newLocation) {
		location = newLocation;
	};
	
	// set new color with given color
	public void setColor(int newColor) {
		color = newColor;
	};
	
	// generate a random integer from min to max
	public int randomInt(int min, int max) {
		Random ran = new Random();
		return min + ran.nextInt(max);
	}
	
	// move the object with given X and Y in direction
	public void move(float deltaX, float deltaY) {
		// update new x and y
		float newX = location.getX() + deltaX;
		float newY = location.getY() + deltaY;
		
		// store new x and y
		Point newP = new Point(newX, newY);
		setLocation(newP);
	}
}
