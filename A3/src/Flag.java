package com.mycompany.a3;

import com.codename1.charts.models.*;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

// represents a flag in the game world
public class Flag extends Fixed implements IDrawable{
	private int sequenceNumber;
	private GameWorld gw;
	
	// Constructor with given sequenceNumber and gameWorld object
	Flag(int sequenceNumber, GameWorld gw){
		super(ColorUtil.LTGRAY, 100, gw);
		this.sequenceNumber = sequenceNumber;
		this.gw = gw;
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
	@Override
	public void setColor(int newColor) {
		// do nothing
	}
	
	@Override
	public void setSize(int newSize) {
		// do nothing
		// flag size cannot be changed
	}
	
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(super.getColor());
		int stringX = (int)Math.round(this.getX() - 10) + (int)Math.round(pCmpRelPrnt.getX());
		int stringY = (int)Math.round(this.getY() - 20) + (int)Math.round(pCmpRelPrnt.getY());
		int xLocation = (int)this.getLocation().getX() + (int)pCmpRelPrnt.getX();
		int yLocation = (int)this.getLocation().getY() + (int)pCmpRelPrnt.getY();
		int[] xPoints = {xLocation, xLocation - (this.getSize() / 2), xLocation + (this.getSize() / 2)};
		int[] yPoints = {yLocation, yLocation - (this.getSize()), yLocation - (this.getSize())};
		int nPoints = 3;		// 3-sided polygon (triangle)
		
		g.fillPolygon(xPoints, yPoints, nPoints);
		
		g.setColor(ColorUtil.BLACK);			// font color is black
		g.drawString(Integer.toString(this.sequenceNumber), stringX, stringY - this.getSize() / 2 );
	}
	
	@Override
	public void handleCollision(GameObject object) {
		// if collision happens between a flag and an ant
		if(this instanceof Flag && object instanceof Ant) {
			// if the new flag number is the next number and it is not the last flag
			if(((Ant)object).getLastFlagReached() + 1 == this.getSequenceNumber()){
				System.out.println("The Ant has reached the next flag!!!\n");
				((Ant)object).newFlagReached();		// update the last flag number reached
				
				// if it is the last flag
				if(((Ant)object).getLastFlagReached() == 4) {
					// print game over and total time
					System.out.println("Game over, you win! Total time: #" + super.getGW().getTimer());
					gw.endGame();		// exit game
				}
			}
			else {
				System.out.println("The flag has been passed or haven't been reached yet!!!\n");
			}
		}
	}
}
