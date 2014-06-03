package edu.kit.anthropomatik.isl.DialogModeling.UserModel;

import java.io.Serializable;

/**
 * Models a project a user is currently working on.
 * 
 * @author Lucas
 */
public class UserProject implements Serializable{

	private static final long serialVersionUID = 1L;

	private String projectName;
	
	private UserProjectState projectState;

	public UserProject() {}
	
	public UserProject(String projectName) {
		this.projectName = projectName;
		this.projectState = UserProjectState.NEW;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public UserProjectState getProjectState() {
		return projectState;
	}

	public void setProjectState(UserProjectState projectState) {
		this.projectState = projectState;
	}
	
	@Override
	public boolean equals(Object other) {
		return this.toString().equals(other.toString());
	}
	
	@Override
	public String toString() {
		return String.format("p[%s %s]", this.projectName, this.projectState);
	}
}
