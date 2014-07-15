package edu.kit.anthropomatik.isl.DialogModeling.State;

public class StateAskForHelp extends StateAction {

	protected StateAskForHelp(Main main) {
		super(main);
	}

	@Override
	public void doIt() {
		outputCurrentState();
		
		main.makeSnapShot(); // manually make snapshot here, independent of user being new or not
		
		try {
			Thread.sleep(2000);
			if (Math.random() > 0.5)
				main.getStateMachine().Fire(Trigger.USER_HELPING);
			else
				main.getStateMachine().Fire(Trigger.USER_NOT_HELPING);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
