package edu.kit.anthropomatik.isl.DialogModeling.UserModel;

import java.io.Serializable;
import java.util.Date;

/**
 * Models a user.
 * 
 * @author Lucas
 */
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	private static int idCtr = 0; // used to make sure that we create unique IDs
	
	private int id;
	
	private String name;
	
	private UserProject currentProject;
	
	private Date lastTimeSeen;
	
	public User(String name, UserProject currentProject) {
		this.id = idCtr++;
		
		this.name = name;
		this.currentProject = currentProject;
		updateLastTimeSeen();
	}
	
	public UserProject getCurrentProject() {
		return currentProject;
	}

	public void setCurrentProject(UserProject currentProject) {
		this.currentProject = currentProject;
	}

	public Date getLastTimeSeen() {
		return lastTimeSeen;
	}

	public void updateLastTimeSeen() {
		this.lastTimeSeen = new Date();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
