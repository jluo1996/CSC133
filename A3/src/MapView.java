package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.charts.models.*;


public class MapView extends Container implements Observer {
	private static int height;
	private static int width;
	static private Point mapViewOrigin;
	private GameWorld gw;
	
	public MapView() {
		MapView.height = this.getHeight();
		MapView.width = this.getWidth();
		// create border with width = 10 and color red
		this.getAllStyles().setBorder(Border.createLineBorder(10, ColorUtil.rgb(255, 0, 0)));	
		this.setLayout(new BorderLayout());
		this.getAllStyles().setBgColor(ColorUtil.WHITE);
		this.getAllStyles().setBgTransparency(255);
		
		// Container is 1000*1000
		this.setWidth(1000);
		this.setHeight(1000);
	}
	
	public static void setMapHeight(int h) {
		MapView.height = h;
	}
	
	public static int getMapHeight() {
		return height;
	}
	
	public static void setMapWidth(int w) {
		MapView.width = w;
	}
	
	public static int getMapWidth() {
		return width;
	}
	
	public static Point getMapViewOrigin() {
		return mapViewOrigin;
	}
	
	public void setMapViewOrigin(Point p) {
		MapView.mapViewOrigin = p;
	}
	
	@Override
	public void update(Observable observable, Object data) {
		gw = (GameWorld) data;
		this.repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Point pCmpRelPrnt = new Point(this.getX(), this.getY());		// get MapView origin
		IIterator itr = gw.getCollection().getIterator();
		while(itr.hasNext()) {
			GameObject tempObject = itr.getNext();
			if(tempObject instanceof IDrawable) {
				((IDrawable) tempObject).draw(g, pCmpRelPrnt);		// update all drawable object
			}
		}
	}
	
	@Override
	public void pointerPressed(int x, int y) {
		Point clickPoint = new Point(x - getParent().getAbsoluteX(), y - getParent().getAbsoluteY());
		Point originalPoint = new Point(getX(), getY());
		// if the game is paused
		if(gw.getPause()) {
			gw.click(clickPoint, originalPoint);
		}
	}
}
