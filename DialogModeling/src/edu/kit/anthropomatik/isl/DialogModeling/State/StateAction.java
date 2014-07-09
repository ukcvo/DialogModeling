package edu.kit.anthropomatik.isl.DialogModeling.State;

import org.customsoft.stateless4j.StateMachine;
import org.customsoft.stateless4j.delegates.Action;

public abstract class StateAction implements Action {

	protected StateMachine<State, Trigger> stateMachine;
	protected Main main;
	
	protected StateAction(StateMachine<State, Trigger> stateMachine) {
		this.stateMachine = stateMachine;
	}
	
	protected void outputCurrentState() {
		System.out.println("Current State: " +stateMachine.getState());
	}
}
