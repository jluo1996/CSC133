package com.mycompany.a1;

import com.codename1.charts.models.*;
import com.codename1.charts.util.ColorUtil;

// represents a food station in the game world
public class FoodStation extends Fixed{
	private int capacity;
	
	// Constructor
	FoodStation(){
		super();
		capacity = getSize();		// initial capacity is proportional to the size
	}
	
	// Constructor with given color
	FoodStation(int c){
		super(c);
		capacity = getSize();		// initial capacity is proportional to the size
	}
	
	// Constructor with given size, color, location
	FoodStation(int s, int c, Point l){
		super(s, c, l);
		capacity = s;		// initial capacity is proportional to the size 
	}
	
	// get capacity
	public int getCapacity() {
		return capacity;
	}
	
	// set capacity
	private void setCapacity(int newCapacity) {
		capacity = newCapacity;
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
}
