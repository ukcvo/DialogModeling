package edu.kit.anthropomatik.isl.DialogModeling.State;

public class StateWaitForElevator extends StateAction {

	protected StateWaitForElevator(Main main) {
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
