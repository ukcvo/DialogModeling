package edu.kit.anthropomatik.isl.DialogModeling.State;

import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.synthesizer.Synthesizer;

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
			userName= Recognizer.recognize();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			Thread.sleep(2000);
			if (Math.random() > 0.5)
				main.getStateMachine().Fire(Trigger.USER_RECOGNIZED);
			else
				main.getStateMachine().Fire(Trigger.USER_UNKNOWN);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
