package com.yw.util;

import java.io.*;
import java.net.*;

public class SocketHelper {

	private Socket s;
	private String ip;
	private int port;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public SocketHelper(String ip, int port){
		this.ip = ip;
		this.port = port;
	}
	
	public SocketHelper(Socket s){
		this.s = s;
		this.ip = s.getInetAddress().toString();//to be tested
		this.port = s.getPort();
		try {
			out = new ObjectOutputStream(s.getOutputStream());
			in = new ObjectInputStream(s.getInputStream());	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean Connect(){
		try {		
			s = new Socket(ip,port);
			out = new ObjectOutputStream(s.getOutputStream());
			in = new ObjectInputStream(s.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void Write(Message msg) throws IOException{

			out.writeObject(msg);

	}
	
	public boolean isConnected(){
		return s.isConnected();
	}
	
	public Message Read() throws Exception, IOException{
		Message msg = null;

			msg = (Message)in.readObject();

		return msg;
	}
	
	public void Close(){
		try {
			out.close();
			in.close();
			s.close();
			s.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
