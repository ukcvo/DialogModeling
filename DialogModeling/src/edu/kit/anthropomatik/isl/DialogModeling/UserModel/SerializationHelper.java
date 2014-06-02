package edu.kit.anthropomatik.isl.DialogModeling.UserModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SerializationHelper {

	private SerializationHelper() {} // private constructor to prevent instantiation
	
	public static boolean storeUsers(List<User> users, String fileName) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(users);			
			oos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<User> loadUsers(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			List<User> users = (List<User>) ois.readObject();
			ois.close();
			return users;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<User>();
		}
	}
}
