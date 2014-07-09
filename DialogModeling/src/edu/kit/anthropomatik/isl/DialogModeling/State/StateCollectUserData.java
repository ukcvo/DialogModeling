package edu.kit.anthropomatik.isl.DialogModeling.State;

import org.customsoft.stateless4j.StateMachine;

public class StateCollectUserData extends StateAction {

	protected StateCollectUserData(StateMachine<State, Trigger> stateMachine) {
		super(stateMachine);
	}

	@Override
	public void doIt() {
		outputCurrentState();
		// TODO copy-paste collectUserData()
	}

}
