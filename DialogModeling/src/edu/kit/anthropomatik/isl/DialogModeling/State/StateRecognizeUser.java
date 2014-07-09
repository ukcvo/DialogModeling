package edu.kit.anthropomatik.isl.DialogModeling.State;

public class StateRecognizeUser extends StateAction {

	protected StateRecognizeUser(Main main) {
		super(main);
	}

	@Override
	public void doIt() {
		outputCurrentState();

		int userID = main.getOpenCVAdapter().getSmoothedUserID();
		
		if (userID >= 0){ 
			main.setCurrentUser(main.getUsers().get(userID));
			System.out.println("Recognized user: " + main.getCurrentUser().toString());
		} else {
			main.setCurrentUser(null);
		}
		
		try {
			if (main.getCurrentUser() != null) {
				main.getStateMachine().Fire(Trigger.USER_RECOGNIZED);
			} else {
				main.getStateMachine().Fire(Trigger.USER_NOT_RECOGNIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
