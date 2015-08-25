package com.yw.client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.yw.util.FileHelper;
import com.yw.util.Line;
import com.yw.util.Message;
import com.yw.util.SocketHelper;

public class Client extends JFrame implements Runnable {

	public final static String SERVER = "afsaccess2.njit.edu";
//	public final static String SERVER = "127.0.0.1";
	public final static int PORT = 8964;
	public String username;
	public ChatPanel cp;
	public DrawPanel dp;
	public ListPanel lp;
	public ControlPanel conp;
	public SocketHelper sh;
	public boolean isConnected;
	public JPanel container;

	public Client() {
	}

	public Client(String name) {
		this.username = name;
		this.setTitle("Chatting Room : " + name);
		init();
	}

//	public static void main(String[] args) {
//
//		Client client = new Client("" + Math.random());
//		client.addWindowListener(new WindowAdapter() {
//
//			public void windowClosing(WindowEvent we) {
//				if (client.isConnected) {
//					client.disconnect();
//				}
//				System.exit(0);
//			}
//		});
//	}

	public void init() {
		sh = new SocketHelper(SERVER, PORT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cp = new ChatPanel(this);
		dp = new DrawPanel(this);
		lp = new ListPanel(new ArrayList<String>());
		conp = new ControlPanel(this);

		container = new JPanel();
		container.setLayout(new BorderLayout());
		container.add(lp, BorderLayout.WEST);
		container.add(cp, BorderLayout.CENTER);
		container.add(dp, BorderLayout.EAST);
		container.add(conp, BorderLayout.SOUTH);
		getContentPane().add(container, BorderLayout.CENTER);

		setSize(930, 500);
		setLocationRelativeTo(null);
		setVisible(true);
		this.repaint();
	}

	public void sendMessage(String text) {
		// TODO Auto-generated method stub
		Message msg = new Message(username, lp.getSelected(), 0);
		msg.setMessage(text);
		try {
			sh.Write(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendDrawing() {
		// TODO Auto-generated method stub
		Message msg = new Message(username, lp.getSelected(), 1);
		msg.setDrawing(dp.linelist);
		try {
			sh.Write(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void connect() {
		// TODO Auto-generated method stub
		sh.Connect();
		Message msg = new Message(username, null, 2);
		try {
			sh.Write(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(this).start();
		System.out.println(username + " connected to server!");
		isConnected = true;
	}

	public void disconnect() {
		// TODO Auto-generated method stub
		Message msg = new Message(username, null, 3);
		try {
			sh.Write(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sh.Close();
		lp.Refresh(new ArrayList<String>());
		isConnected = false;
	}

	public void save() {
		// TODO Auto-generated method stub
		File fs = null;
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save");
		jfc.setCurrentDirectory(new File("."));
		jfc.setFileFilter(new javax.swing.filechooser.FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.isDirectory())
					return true;
				String name = f.getName();
				int p = name.lastIndexOf('.');
				if (p == -1)
					return false;
				String suffix = name.substring(p + 1).toLowerCase();
				return suffix.equals("ctr");
			}

			@Override
			public String getDescription() {
				return "Chatting History";
			}

		});
		int saveflag = jfc.showSaveDialog(this);
		if (saveflag == JFileChooser.APPROVE_OPTION) {
			fs = jfc.getSelectedFile();
		}
		
		FileHelper.writeObject(fs, cp.ta.getText(), dp.linelist);
	}

	public void load() {
		// TODO Auto-generated method stub
		File fs = null;
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Load");
		jfc.setCurrentDirectory(new File("."));
		jfc.setFileFilter(new javax.swing.filechooser.FileFilter() {

			@Override
			public boolean accept(File f) {
				if (f.isDirectory())
					return true;
				String name = f.getName();
				int p = name.lastIndexOf('.');
				if (p == -1)
					return false;
				String suffix = name.substring(p + 1).toLowerCase();
				return suffix.equals("ctr");
			}

			@Override
			public String getDescription() {
				return "Chatting History";
			}

		});
		int saveflag = jfc.showOpenDialog(this);
		if (saveflag == JFileChooser.APPROVE_OPTION) {
			fs = jfc.getSelectedFile();
		}
		
		FileHelper.readObject(fs, this);
	}

	public void clear() {
		// TODO Auto-generated method stub
		dp.linelist = new ArrayList<Line>();
		dp.repaint();
		cp.ta.setText("");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			Message msg = null;
			try {
				msg = sh.Read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			if (msg != null) {
				if (msg.getType() == 0) {
					cp.appendMessage(msg.getUsername(), msg.getMessage());
				} else if (msg.getType() == 1) {
					dp.linelist = msg.getDrawing();
					dp.repaint();
				} else if (msg.getType() == 2) {
					cp.decEnter(msg.getUsername());
					lp.Refresh(msg.getTarget());// Server should send the
												// current online users
				} else {
					cp.decExit(msg.getUsername());
					lp.Refresh(msg.getTarget());
				}
			}
		}
	}

}
