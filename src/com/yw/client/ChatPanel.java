package com.yw.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import com.yw.util.Message;

public class ChatPanel extends JPanel{
	JTextField tf;
	JTextArea ta;
	Client container;
	
	public ChatPanel(){
	}
	public ChatPanel(Client container){
		this.container = container;
		setLayout(new BorderLayout());
		tf = new JTextField();
		ta = new JTextArea();
		ta.setBackground(new Color(0xf9f9f9));
		ta.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		ta.setEditable(false);
		tf.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				String text = tf.getText();
				if (text != "") {
					tf.setText("");
					container.sendMessage(text);
//					ta.append(container.username + " : "+text + "\n");
				}
			}
		});
		
		JPanel jp = new JPanel();
		jp.setLayout(new FlowLayout());
		tf.setPreferredSize(new Dimension(50,80));
		jp.add(tf);
		add(tf, BorderLayout.SOUTH);
		add(ta, BorderLayout.CENTER);
//		setBorder(new LineBorder(new Color(0xdddddd), 5));
		setBorder(new EtchedBorder(HEIGHT));
	
	}
	public void appendMessage(String name,String message){
		ta.append(name + " : " + message + "\n");
	}
	public void decEnter(String name){
		ta.append(name + " entered the Chatting Room.\n");
	}
	public void decExit(String name){
		ta.append(name + " left the Chatting Room.\n");
	}
	public void refresh(String msg){
		ta.setText(msg);
	}
	public Dimension getPreferredSize(){
		return new Dimension(450,100);
	}
	public Dimension getMinimumSize(){
		return new Dimension(450,100);
	}
	
}
