package com.mycompany.a2;

import com.codename1.charts.models.*;

// represents a fixed game object
public abstract class Fixed extends GameObject{
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
	
	public void setLocation() {
		// override setLocation method
		// fixed objects cannot move
	}
	
	// override move() method
	public void move() {
		// do nothing
		// fixed object cannot move
	}
	
}
