package com.mycompany.a2;

import com.codename1.charts.models.*;
import com.codename1.charts.util.ColorUtil;

public class Spider extends Movable{
	// Constructor
	Spider(){
		super();
	}
	
	// Constructor with given color
	Spider(int c){
		super(c);
	}
	
	// Constructor
	Spider(int s, int c, Point l){
		super();
	}

	// add/subtract random small values (0-5) to its heading
	public void changeHeading() {
		setHeading(randomInt(0,5));
	}
	
	// check if spider location is out of bounce
	public boolean checkOutOfBound() {
		// if spider's location is outside of (1000,1000)
		if(getLocation().getX() > 1000 || getLocation().getY() > 1000) {
			return true;
		}
		return false;
	}
	
	// override the toString method
	public String toString() {
		// initiation
		String spiderInfo = "Spider: ";
		
		// location
		spiderInfo += "loc=" + Math.round(((getLocation().getX())*10.0)/10.0) + "," + Math.round(((getLocation().getY())*10.0)/10.0);
		
		// color
		spiderInfo += " color=[" + ColorUtil.red(getColor()) + "," + ColorUtil.green(getColor()) + "," + ColorUtil.blue(getColor()) + "] ";
			
		// heading
		spiderInfo += "heading=" + getHeading();
		
		// speed
		spiderInfo += " speed=" + getSpeed();
				
		// size
		spiderInfo += " size=" + getSize();
		
		return spiderInfo;
	}
	
	// override, spider cannot change color
	public void setColor(int newColor) {
		// do nothing
	}
}
