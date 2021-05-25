package com.mycompany.a2;

import java.util.Observable;
import java.util.Observer;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.Container;



public class MapView extends Container implements Observer {
	private static int height;
	private static int width;
	private GameWorld gw;
	
	public MapView() {
		// create border with width = 10 and color red
		this.getAllStyles().setBorder(Border.createLineBorder(10, ColorUtil.rgb(255, 0, 0)));	
		
		MapView.height = this.getHeight();
		MapView.width = this.getWidth();
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
	
	@Override
	public void update(Observable observable, Object data) {
		gw = (GameWorld) data;
		gw.printMap();
	}
}
