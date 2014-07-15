package edu.kit.anthropomatik.isl.DialogModeling.State;

public class ActionMakeSnapshot extends StateAction {

	public ActionMakeSnapshot(Main main) {
		super(main);
	}
	
	@Override
	public void doIt() {
		if (main.isNewUser())
			main.makeSnapShot();
	}

}
