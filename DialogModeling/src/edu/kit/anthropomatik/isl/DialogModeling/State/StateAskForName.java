package edu.kit.anthropomatik.isl.DialogModeling.State;

import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.synthesizer.Synthesizer;

import edu.kit.anthropomatik.isl.DialogModeling.UserModel.User;

public class StateAskForName extends StateAction {

	protected StateAskForName(Main main) {
		super(main);
	}

	@Override
	public void doIt() {
		outputCurrentState();

		Synthesizer.synthesize("Maybe I don't know you yet. What is your name?");
		
		
		String userName="";
		
		try {
			while (userName=="")
				userName= Recognizer.recognize();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		main.setCurrentUser(null);
		
		for (User usr : main.getUsers()) {
			if (userName.equalsIgnoreCase(usr.getName())) {
				main.setCurrentUser(usr);
				break; // take first one
			}
		}
		
		try {
			if (main.getCurrentUser() != null)
				main.getStateMachine().Fire(Trigger.USER_RECOGNIZED);
			else {
				User usr = new User(userName, null);
				main.getUsers().add(usr);
				main.setCurrentUser(usr);
				main.setNewUser(true);
				main.getStateMachine().Fire(Trigger.USER_UNKNOWN);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
