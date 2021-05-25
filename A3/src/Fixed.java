package com.mycompany.a3;

import com.codename1.ui.Graphics;
import com.codename1.charts.models.*;

// represents a fixed game object
public abstract class Fixed extends GameObject implements ISelectable{
	private boolean selected;		// indicate whether this object is selected
	
	// Constructor with given color and a game world object
	Fixed(int c, GameWorld newGW){
		super(c, newGW);
	}
	
	// Constructor with given color, size and a game world object
	Fixed(int c, int s, GameWorld newGW){
		super(c, s, newGW);
	}
			
	/*
	// Constructor with random size, color, location
	Fixed(){
		super();
	}
	
	// Constructor with given color
	Fixed(int c){
		super(c);
	}
	
	// Constructor with given size and color
	Fixed(int s, int c){
		super(s, c);
	}
	
	// Constructor with given size, color, location
	Fixed(int s, int c, Point l){
		super(s, c, l);
	}
	
	*/
	
	@Override 
	// allow to change its location
	public void setLocation(Point newPoint) {
		// only allow to change location when selected
		if(selected) {
			super.setLocation(newPoint);
		}
	}
	
	public void setSelected(boolean newSelection) {
		selected = newSelection;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		int radius = super.getSize() / 2;
		int left = (int)Math.round(super.getX() - radius + pCmpRelPrnt.getX());
		int right = left + super.getSize();
		int top = (int)Math.round(super.getY() - radius + pCmpRelPrnt.getY());
		int bottom = top + super.getSize();
		boolean checkedLR = pPtrRelPrnt.getX() > left && pPtrRelPrnt.getX() < right;		// check left/right overlap
		boolean checkedTB = pPtrRelPrnt.getY() > top && pPtrRelPrnt.getY() < bottom;		// check top/bottom overlap
		
		return checkedLR && checkedTB;
	}
	
	// override move() method
	public void move() {
		// do nothing
		// fixed object cannot move
	}
	
}
