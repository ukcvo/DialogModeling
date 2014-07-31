package edu.kit.anthropomatik.isl.DialogModeling.State;

import java.util.ArrayList;
import java.util.List;

import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.synthesizer.Synthesizer;

import edu.kit.anthropomatik.isl.DialogModeling.Common.CommonString;
import edu.kit.anthropomatik.isl.DialogModeling.UserModel.User;

public class StateAskForName extends StateAction {

		
	protected StateAskForName(Main main) {
		super(main);
	}

	@Override
	public void doIt() {
		List<String> userNameList= new ArrayList<String>();
		List<String> yesOrNoAnswer= new ArrayList<String>();
		String userName= "";
	
		outputCurrentState();

		try {
			while (userNameList.isEmpty()) {
				Synthesizer.synthesize("Maybe I don't know you yet. What is your name?");
				userNameList= Recognizer.recognizeAllResponses();
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
			
		for (int i=0 ; i < userNameList.size(); i++){
			try {
				while (yesOrNoAnswer.isEmpty() || yesOrNoAnswer.get(0) == null) {
					Synthesizer.synthesize("Your name is " + userNameList.get(i) +". Is that right?");
					System.out.println(userNameList.get(i));
					yesOrNoAnswer= Recognizer.recognizeAllResponses();
				}
					
				if(CommonString.isIn(yesOrNoAnswer, "yes")){
					userName= userNameList.get(i);
					break;
				} 
				yesOrNoAnswer.clear();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
