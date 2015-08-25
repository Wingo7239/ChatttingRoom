package com.yw.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import com.yw.util.Line;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener{
	ArrayList<Line> linelist;
	int lastX, lastY;
	Client container;
	public Color cc;
	
	public DrawPanel(){}
	public DrawPanel(Client container){
		setBackground(Color.WHITE);
		linelist = new ArrayList<Line>();
		addMouseMotionListener(this);
		addMouseListener(this);
		cc = new Color(0);
		this.container = container;
	}
	public void mouseMoved(MouseEvent me){
	}
	public void mouseDragged(MouseEvent me){
		int endX = me.getX();
		int endY = me.getY();
		Line line = new Line(lastX, lastY, endX, endY,cc);
		linelist.add(line);
		lastX = endX;
		lastY = endY;
		repaint();
	}
	public void mouseEntered(MouseEvent me){}
	public void mouseExited(MouseEvent me){}
	public void mousePressed(MouseEvent me){
		lastX = me.getX();
		lastY = me.getY();
	}
	public void mouseReleased(MouseEvent me){}
	public void mouseClicked(MouseEvent me){}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Iterator<Line> it = linelist.iterator();
		while(it.hasNext()){
			Line current = it.next();
			g.setColor(current.getColor());
			g.drawLine(current.getStartX(), current.getStartY(),
					current.getEndX(), current.getEndY());
		}
		//LineMessage lm = new LineMessage();
		//lm.setMessage(linelist);
		//container.sendMessage(lm);
//		g.drawString("Painted on JPanel",100,100);
	}
	public Dimension getPreferredSize(){
		return new Dimension(310,100);
	}
	public Dimension getMinimumSize(){
		return new Dimension(310,100);
	}
	
	public void refresh(ArrayList<Line> list){
		this.linelist = list;
		repaint();
	}
}

