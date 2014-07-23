package edu.kit.anthropomatik.isl.DialogModeling.State;

import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.synthesizer.Synthesizer;

import edu.kit.anthropomatik.isl.DialogModeling.UserModel.UserProject;

public class StateCollectUserData extends StateAction {

	protected StateCollectUserData(Main main) {
		super(main);
	}

	@Override
	public void doIt() {
		outputCurrentState();

		Synthesizer.synthesize("Hi " + main.getCurrentUser().getName() + "! What project are you working on?");
			
		String projectName="";
		
		try {
			while (projectName=="")
				projectName= Recognizer.recognize();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		UserProject project = new UserProject(projectName);
		main.getCurrentUser().setCurrentProject(project);
		main.makeSnapShot();
		main.storeUsers();
		try {
			main.getStateMachine().Fire(Trigger.USER_RECOGNIZED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
