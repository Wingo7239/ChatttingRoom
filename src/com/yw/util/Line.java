package com.yw.util;

import java.awt.Color;
import java.io.Serializable;

public class Line implements Serializable{
	int startx, starty, endx, endy;
	Color color;
	
	public Line(){}
	public Line(int sx, int sy, int ex, int ey,Color color){
		setStartX(sx);
		setStartY(sy);
		setEndX(ex);
		setEndY(ey);
		setColor(color);
		
	}
	public void setStartX(int sx){
		startx = sx;
	}
	public void setStartY(int sy){
		starty = sy;
	}
	public void setEndX(int ex){
		endx = ex;
	}
	public void setEndY(int ey){
		endy = ey;
	}
	public void setColor(Color c){
		color = c;
	}
	public int getStartX(){
		return startx;
	}
	public int getStartY(){
		return starty;
	}
	public int getEndX(){
		return endx;
	}
	public int getEndY(){
		return endy;
	}
	public Color getColor(){
		return color;
	}
}
