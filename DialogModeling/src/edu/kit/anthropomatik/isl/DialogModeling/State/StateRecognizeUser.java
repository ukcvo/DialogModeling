package edu.kit.anthropomatik.isl.DialogModeling.State;

import org.customsoft.stateless4j.StateMachine;

public class StateRecognizeUser extends StateAction {

	protected StateRecognizeUser(StateMachine<State, Trigger> stateMachine) {
		super(stateMachine);
	}

	@Override
	public void doIt() {
		outputCurrentState();
		//TODO copy-past recognizeUser

	}

}
