package com.mycompany.a2;

import com.codename1.charts.models.*;
import com.codename1.charts.util.ColorUtil;

// represents a flag in the game world
public class Flag extends Fixed{
	private int sequenceNumber;
	
	// Constructor with initial squenceNumber = 0
	Flag(){
		super();
		// sequence number is 0
		setSequenceNumber(0);
	}
	
	// Constructor with given color
	Flag(int c, int seq){
		super(c);
		setSequenceNumber(seq);
	}
	
	// Constructor with given size and color
	Flag(int s, int c, int seq){
		super(s, c);
		setSequenceNumber(seq);
	}
	
	// Constructor with given size, color, location with initial squenceNumber = 0
	Flag(int s, int c, Point l){
		super(s, c, l);
		// sequence number is 0
		setSequenceNumber(0);
	}
	
	// get sequence number
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	
	// set sequence number to the newest count number
	// internal method
	private void setSequenceNumber(int newSequenceNumber) {
		sequenceNumber = newSequenceNumber;
	}
	
	// override the toString method
	public String toString() {
		// initiation
		String flagInfo = "Flag: ";
		
		// location
		flagInfo += "loc=" + Math.round(((getLocation().getX())*10.0)/10.0) + "," + Math.round(((getLocation().getY())*10.0)/10.0);
		
		// color
		flagInfo += " color=[" + ColorUtil.red(getColor()) + "," + ColorUtil.green(getColor()) + "," + ColorUtil.blue(getColor()) + "] ";
				
		// size
		flagInfo += " size=" + getSize();
		
		// sequence number
		flagInfo += " seqNum=" + getSequenceNumber();
		
		return flagInfo;
	}
	
	// override, flags cannot change color
	public void setColor(int newColor) {
		// do nothing
	}
}
