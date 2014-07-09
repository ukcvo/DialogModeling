package edu.kit.anthropomatik.isl.DialogModeling.State;

public class StateCollectUserData extends StateAction {

	protected StateCollectUserData(Main main) {
		super(main);
	}

	@Override
	public void doIt() {
		outputCurrentState();

		try {
			Thread.sleep(2000);
			main.getStateMachine().Fire(Trigger.USER_RECOGNIZED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
