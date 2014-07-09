package edu.kit.anthropomatik.isl.DialogModeling.State;

import org.customsoft.stateless4j.StateMachine;

public class StateGreetUser extends StateAction {

	protected StateGreetUser(StateMachine<State, Trigger> stateMachine) {
		super(stateMachine);
	}

	@Override
	public void doIt() {
		outputCurrentState();

	}

}
