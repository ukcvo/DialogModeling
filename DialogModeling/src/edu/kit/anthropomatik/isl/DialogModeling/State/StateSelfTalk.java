package edu.kit.anthropomatik.isl.DialogModeling.State;

import org.customsoft.stateless4j.StateMachine;

public class StateSelfTalk extends StateAction {

	protected StateSelfTalk(StateMachine<State, Trigger> stateMachine) {
		super(stateMachine);
	}

	@Override
	public void doIt() {
		outputCurrentState();
		// TODO copy-paste doSelfTalk()

	}

}
