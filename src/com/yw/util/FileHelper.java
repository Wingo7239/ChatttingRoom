package com.yw.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.yw.client.Client;

public class FileHelper {

	public static void writeObject(File file, String chat,ArrayList<Line> drawing){
		try {
		FileOutputStream outStream;
		outStream = new FileOutputStream(file);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				outStream);

			objectOutputStream.writeObject(chat);
			objectOutputStream.writeObject(drawing);
			System.out.println("successful");
			outStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void readObject(File file, Client client){
		String chat = "";
		ArrayList<Line> drawing = new ArrayList<Line>();
		try {
			FileInputStream freader;
			freader = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(freader);
			chat = (String) objectInputStream.readObject();
			drawing = (ArrayList<Line>) objectInputStream.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		client.cp.refresh(chat);
		client.dp.refresh(drawing);

	}
}
