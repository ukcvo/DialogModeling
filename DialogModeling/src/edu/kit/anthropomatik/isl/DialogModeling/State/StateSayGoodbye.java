package edu.kit.anthropomatik.isl.DialogModeling.State;

import org.customsoft.stateless4j.StateMachine;

public class StateSayGoodbye extends StateAction {

	protected StateSayGoodbye(StateMachine<State, Trigger> stateMachine) {
		super(stateMachine);
	}

	@Override
	public void doIt() {
		outputCurrentState();
	}

}
