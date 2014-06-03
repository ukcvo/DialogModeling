package edu.kit.anthropomatik.isl.DialogModeling.UserModel;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SerializationHelper {

	private SerializationHelper() {} // private constructor to prevent instantiation
	
	public static boolean storeUsers(List<User> users, String fileName) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			XMLEncoder xmlEncoder = new XMLEncoder(bos);
			xmlEncoder.writeObject(users);			
			xmlEncoder.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<User> loadUsers(String fileName) {
		try {
			User.setIdCtr(0);
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bis = new BufferedInputStream(fis);
			XMLDecoder xmlDecoder = new XMLDecoder(bis);
			List<User> users = (List<User>) xmlDecoder.readObject();
			xmlDecoder.close();
			User.setIdCtr(users.size());
			return users;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<User>();
		}
	}
}
