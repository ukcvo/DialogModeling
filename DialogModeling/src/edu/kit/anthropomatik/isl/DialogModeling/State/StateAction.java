package edu.kit.anthropomatik.isl.DialogModeling.State;

import org.customsoft.stateless4j.delegates.Action;

public abstract class StateAction implements Action {

	protected Main main;
	
	protected StateAction(Main main) {
		this.main = main;
	}
	
	protected void outputCurrentState() {
		System.out.println("Current State: " +main.getStateMachine().getState());
	}
}
