package edu.kit.anthropomatik.isl.DialogModeling.State;

public class StateSelfTalk extends StateAction {

	protected StateSelfTalk(Main main) {
		super(main);
	}

	@Override
	public void doIt() {
		outputCurrentState();

		try {
			Thread.sleep(2000);
			main.getStateMachine().Fire(Trigger.SELF_TALK_OVER);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
