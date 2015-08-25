package com.yw.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class Loggin extends JDialog{

	public Loggin(){
		super();
		this.setTitle("Loggin");
//		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent we) {

				System.exit(0);
			}
		});		
		JTextField t = new JTextField();
		t.setSize(800, t.getHeight());
		t.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Client client = new Client(t.getText());
				client.addWindowListener(new WindowAdapter() {

					public void windowClosing(WindowEvent we) {
						if (client.isConnected) {
							client.disconnect();
						}
						System.exit(0);
					}
				});
				Loggin.this.dispose();
			}
		});
		JLabel jl = new JLabel("Username:");
		JPanel jp = new JPanel();
		jp.setBorder(new EmptyBorder(5,10,5,10));
		jp.setLayout(new BorderLayout());
		jp.add(jl,BorderLayout.WEST);
		jp.add(t,BorderLayout.CENTER);
		this.add(jp);
	}
	
	public static void main(String[] args){
		Loggin lg = new Loggin();
		lg.setSize(300, 70);
		lg.setLocationRelativeTo(null);
		lg.setVisible(true);
	}
}
