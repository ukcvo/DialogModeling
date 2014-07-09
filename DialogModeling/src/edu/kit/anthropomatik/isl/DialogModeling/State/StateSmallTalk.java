package edu.kit.anthropomatik.isl.DialogModeling.State;

import org.customsoft.stateless4j.StateMachine;

public class StateSmallTalk extends StateAction {

	protected StateSmallTalk(StateMachine<State, Trigger> stateMachine) {
		super(stateMachine);
	}

	@Override
	public void doIt() {
		outputCurrentState();
	}

}
