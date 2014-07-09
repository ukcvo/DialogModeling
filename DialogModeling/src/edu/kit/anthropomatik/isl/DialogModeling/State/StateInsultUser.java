package edu.kit.anthropomatik.isl.DialogModeling.State;

public class StateInsultUser extends StateAction {

	protected StateInsultUser(Main main) {
		super(main);
	}

	@Override
	public void doIt() {
		outputCurrentState();
		try {
			Thread.sleep(2000);
			main.getStateMachine().Fire(Trigger.JOB_DONE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
