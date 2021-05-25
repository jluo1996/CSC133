package com.mycompany.a3;

import com.codename1.charts.models.*;
import com.codename1.charts.util.ColorUtil;
import java.util.Random;
import java.util.ArrayList;


public abstract class GameObject implements ICollider {
	private int size;
	private int color;
	private GameWorld gw;
	private Point location;
	private ArrayList<GameObject> collisions;
	
	// Constructor with given color and game world object 
	// With random generated location
	GameObject(int c, GameWorld newGW){
		this.color = c;
		location = new Point(randomInt(0, 1000), randomInt(0, 1000));	// random location
		this.gw = newGW;
		collisions = new ArrayList<GameObject>();
	}
	
	// Constructor with given color, size and a game world object
	// With random generated location
	GameObject(int c, int s, GameWorld newGW){
		this.color = c;
		this.size = s;
		this.gw = newGW;
		location = new Point(randomInt(0, 1000), randomInt(0, 1000));	// random location
		collisions = new ArrayList<GameObject>();
	}
	
	public void setX(float x) {
		location.setX(x);
	}
	
	public float getX() {
		return location.getX();
	}
	
	public void setY(float y) {
		location.setY(y);
	}
	
	public float getY() {
		return location.getY();
	}
	
	// get current size of the object
	public int getSize() {
		return size;
	}
	
	// set new size with given int
	protected void setSize(int newSize) {
		this.size = newSize;
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
	
	@Override
	public boolean collidesWith(GameObject otherObject) {
		boolean result = false;
		double thisCenterX = this.getX() + (otherObject.getSize() / 2);
		double thisCenterY = this.getY() + (otherObject.getSize() / 2);
		double otherCenterX = otherObject.getX();
		double otherCenterY = otherObject.getY();
		double dx = thisCenterX - otherCenterX;
		double dy = thisCenterY - otherCenterY;
		
		double distanceBetweenCenterSquare = (dx * dx + dy * dy);
		int thisRadius = this.getSize() / 2;
		int otherRadius = otherObject.getSize() / 2;
		int radiusSquare = (thisRadius * thisRadius + 2 * thisRadius * otherRadius + otherRadius * otherRadius);
		
		if(distanceBetweenCenterSquare <= radiusSquare) {
			result = true;
		}
		
		return result;
	}
	
	public ArrayList<GameObject> getObjects(){
		return collisions;
	}
	
	public GameWorld getGW() {
		return gw;
	}
}
