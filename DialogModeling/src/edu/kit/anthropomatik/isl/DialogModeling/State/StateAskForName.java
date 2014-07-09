package edu.kit.anthropomatik.isl.DialogModeling.State;

import org.customsoft.stateless4j.StateMachine;

public class StateAskForName extends StateAction {

	protected StateAskForName(StateMachine<State, Trigger> stateMachine) {
		super(stateMachine);
	}

	@Override
	public void doIt() {
		outputCurrentState();
		//TODO copy-paste askForName()
	}

}
