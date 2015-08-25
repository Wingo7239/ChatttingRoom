package com.yw.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;

import com.yw.util.*;

public class Server {

	public static ArrayList<Handle> ThreadPool;

	public Server() {
		try {
			ServerSocket ss = new ServerSocket(8964);
			ThreadPool = new ArrayList<Handle>();
			System.out.println("Server start!");
			while (true) {
				Socket s = ss.accept();
				Handle h = new Handle(s);
				Message msg = null;
				try {
					msg = h.sHelper.Read();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(msg.getType() == 2){
					h.username = msg.getUsername();
				}
				ThreadPool.add(h);
				h.Send(msg);
				h.start();
				System.out.println("Server got connection!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Server server = new Server();

	}

}

class Handle extends Thread {
	public SocketHelper sHelper;
	public String username;

	public Handle(Socket s) {
		this.sHelper = new SocketHelper(s);
	}

	public synchronized void Send(Message msg) {
		if (msg.getTarget() == null) {// notify all
			if(msg.getType() > 1){
				Iterator<Handle> it = Server.ThreadPool.iterator();
				ArrayList<String> name = new ArrayList<>();
				while (it.hasNext()) {
					Handle current = it.next();
					name.add(current.getUsername());
				}
				msg.setTarget(name);
			}
			Iterator<Handle> it = Server.ThreadPool.iterator();
			while (it.hasNext()) {
				Handle current = it.next();
				try {
					current.sHelper.Write(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {// private message
			Iterator<Handle> it = Server.ThreadPool.iterator();
			while (it.hasNext()) {
				Handle current = it.next();
				Iterator<String> name = msg.getTarget().iterator();
				while(name.hasNext()){
					String n = name.next();
					System.out.println("MSG : "+n);
					System.out.println("Server : "+current.getName());
					if(n.equals(current.getUsername())){
						try {
							current.sHelper.Write(msg);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
		}
	}

	public void run() {
		while (true) {
			Message msg = null;
			try {
				msg = sHelper.Read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(msg.getMessage());
			Send(msg);
			if (msg.getType() == 3) {
				// print client quit info
				Server.ThreadPool.remove(this);
				sHelper.Close();
				break;
			}
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}