package com.mycompany.a3;

import com.codename1.charts.models.*;
import com.codename1.charts.util.ColorUtil;
import java.util.Random;
import com.codename1.ui.Graphics;

public class Spider extends Movable implements IDrawable{
	Random random = new Random();
	
	// Constructor with game world object
	// Color is blue, random heading, random size, random speed
	public Spider(GameWorld newGW) {
		super(ColorUtil.BLACK, newGW);
		final int MIN_SIZE = 50;
		final int MAX_SIZE = 100;
		super.setSize(new Random().nextInt(MAX_SIZE - MIN_SIZE) + MIN_SIZE);
		this.setHeading(random.nextInt(360));		// random heading
		this.setSpeed(new Random().nextInt(10 - 5) + 3);
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
	
	
	public void setSize(int s) {
		// do nothing
		// size cannot be changed
	}
	
	// override, spider cannot change color
	public void setColor(int newColor) {
		// do nothing
	}
	
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(ColorUtil.BLACK);
		int x = (int)this.getX() + (int)pCmpRelPrnt.getX();
		int y = (int)this.getY() + (int)pCmpRelPrnt.getY();
		int[] xPoints = {x, x - (this.getSize() / 2), x + (this.getSize() / 2)};
		int[] yPoints = {y, y - (this.getSize()), y - (this.getSize())};
		int nPoints = 3;		// 3-sided polygon (triangle)
		
		g.drawPolygon(xPoints, yPoints, nPoints);
	}
	
	@Override
	public void handleCollision(GameObject object) {
		if(this instanceof Spider && object instanceof Ant) {
			System.out.println("The collision between the Ant with a spider has occured!!!\n");
			// if health is zero
			((Ant)object).lostHealth();		// the Ant lost 1 health
			((Ant)object).checkHealthLevel();		// update health level
			// if the Ant is dead
			if(((Ant)object).checkDead()) {
				this.getGW().decLifeCount();
				return; 
			}
			
			int temp = 10 - ((Ant)object).getHealthLevel();
			((Ant)object).setColor(ColorUtil.rgb(255, temp, temp));			// faded red by adding more blue and green to it
		}
		
	}
}
