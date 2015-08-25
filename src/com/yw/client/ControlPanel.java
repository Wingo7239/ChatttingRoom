package com.yw.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JPanel;

import com.yw.util.RButton;
public class ControlPanel extends JPanel {

	Client container;
	RButton b1, b2, b3, b4, b5, b6, b7, b8;

	public ControlPanel() {
	}

	public ControlPanel(Client c) {
		this.container = c;
		b1 = new RButton("Connect");
		b2 = new RButton("Save");
		b3 = new RButton("Load");
		b4 = new RButton("Send Drawing");
		b5 = new RButton("Clear");
		b6 = new RButton("Color");
		b7 = new RButton("Hide");

		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(b1.getText() == "Connect"){
					container.connect();
					b1.setText("Disconnect");
				}
				else{
					container.disconnect();
					b1.setText("Connect");
				}
			}
		});
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				container.save();
			}
		});
		b3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				container.load();
			}
		});
		b4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				container.sendDrawing();
			}
		});
		b5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				container.clear();
			}
		});
		b6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Color c = JColorChooser.showDialog(container, "choose color", new Color(0));
				container.dp.cc = c;
			}
		});
		b7.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(b7.getText() == "Hide"){
				container.container.remove(container.dp);
				container.setSize(620, 500);
				container.repaint();
				b7.setText("Show");
				}
				else{
					container.container.add(container.dp,BorderLayout.EAST);
					container.setSize(930, 500);
					container.repaint();
					b7.setText("Hide");
				}
			}
		});
		
		Font f = new Font(Font.MONOSPACED, Font.PLAIN, 12);
		
		b1.setFont(f);
		b2.setFont(f);
		b3.setFont(f);
		b4.setFont(f);
		b5.setFont(f);
		b6.setFont(f);
		b7.setFont(f);
		add(b1);
		add(b2);
		add(b3);
		add(b4);
		add(b5);
		add(b7);
		add(b6);
	}
}
