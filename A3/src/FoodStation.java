package com.mycompany.a3;

import com.codename1.charts.models.*;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import java.util.Random;

// represents a food station in the game world
public class FoodStation extends Fixed implements IDrawable{
	private int capacity;
	private GameWorld gw;
	
	// Constructor with given game world object
	// Color is gray
	FoodStation(GameWorld newGW){
		super(ColorUtil.CYAN, newGW);
		final int MIN_SIZE = 50;			// minimum size
		final int MAX_SIZE = 100;			// maximum size
		super.setSize(new Random().nextInt(MAX_SIZE - MIN_SIZE) + MIN_SIZE);
		this.capacity = this.getSize();		// capacity is equal to its size
		this.gw = newGW;
	}
	
	// get capacity
	public int getCapacity() {
		return capacity;
	}
	
	// set capacity
	private void setCapacity(int newCapacity) {
		capacity = newCapacity;
	}
	
	// check whether the station still has food
	public boolean checkCapacity() {
		if(capacity == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	// empty out the capacity
	public void emptyCapacity() {
		setCapacity(0);		// capacity = 0
		int lightGreen = ColorUtil.GREEN;
		setColor(lightGreen);		// color change to light green
	}
	
	// override the toString method
	public String toString() {
		// initiation
		String foodStationInfo = "FoodStation: ";
		
		// location
		foodStationInfo += "loc=" + Math.round(((getLocation().getX())*10.0)/10.0) + "," + Math.round(((getLocation().getY())*10.0)/10.0);
		
		// color
		foodStationInfo += " color=[" + ColorUtil.red(getColor()) + "," + ColorUtil.green(getColor()) + "," + ColorUtil.blue(getColor()) + "] ";

		// size
		foodStationInfo += " size=" + getSize();
		
		// capacity
		foodStationInfo += " capacity=" + getCapacity();
		
		return foodStationInfo;
	}

	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(super.getColor());
		int stringX = (int)Math.round(this.getX() + 15) + (int)Math.round(pCmpRelPrnt.getX());
		int stringY = (int)Math.round(this.getY() + 10) + (int)Math.round(pCmpRelPrnt.getY());
		int xLocation = (int)this.getLocation().getX() + (int)pCmpRelPrnt.getX();
		int yLocation = (int)this.getLocation().getY() + (int)pCmpRelPrnt.getY();
		
		if(super.isSelected()) {
			g.drawRect(xLocation, yLocation, this.getSize(), this.getSize());		// its size is the length of the square
		}
		else {
			g.fillRect(xLocation, yLocation, this.getSize(), this.getSize());		// its size is the length of the square
		}
		
		g.setColor(ColorUtil.BLACK);			// font color is black
		g.drawString(Integer.toString(this.capacity), stringX, stringY);
	}

	@Override
	public void handleCollision(GameObject object) {
		System.out.println("The collision between the Ant with a food station has occured!!!\n");
		// if collision happens between a food station and an Ant
		if(this instanceof FoodStation && object instanceof Ant) {
			int tempFood = this.getCapacity();		// get the capacity of the food station
			// if this station still has food
			if(tempFood > 0) {
				// let the Ant consume the food
				((Ant)object).addFoodLevel(tempFood);
				//empty out the food station
				this.emptyCapacity();
				// and fade the color to light green
				this.setColor(ColorUtil.rgb(125, 255, 125));
				// add a new food station
				super.getGW().addFoodStation();		// add a new food station to the game world
			}
		}
	}
}
