package com.mycompany.a3;

import com.codename1.ui.Graphics; 
import com.codename1.charts.models.*;

public interface ISelectable {
	public void setSelected(boolean newSelection);
	public boolean isSelected();
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrint);
	public void draw(Graphics g, Point pCmpRelPrnt);
}
