package com.yw.util;

import java.io.Serializable;
import java.util.ArrayList;

public class Message extends DataObject implements Serializable{
	
//	Message Type
//	0:     Normal message
//	1:     Drawing
//	2      Enter 
//	3:     Leave
	
	private String username;
	private ArrayList<String> target;
	private int type;
	private ArrayList<Line> drawing;
	
	public Message(String name, ArrayList<String> target, int type){
		super();
		this.setTarget(target);
		this.setUsername(name);
		this.setType(type);
		this.drawing = null;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<String> getTarget() {
		return target;
	}

	public void setTarget(ArrayList<String> target) {
		this.target = target;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ArrayList<Line> getDrawing() {
		return drawing;
	}

	public void setDrawing(ArrayList<Line> drawing) {
		this.drawing = drawing;
	}


	

}
