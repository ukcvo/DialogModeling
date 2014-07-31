package edu.kit.anthropomatik.isl.DialogModeling.State;

import java.util.ArrayList;
import java.util.List;

import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.synthesizer.Synthesizer;

import edu.kit.anthropomatik.isl.DialogModeling.Common.CommonString;

public class StateAskForHelp extends StateAction {

	protected StateAskForHelp(Main main) {
		super(main);
	}

	@Override
	public void doIt() {
		outputCurrentState();
		
		main.makeSnapShot(); // manually make snapshot here, independent of user being new or not
		main.getCurrentUser().updateLastTimeSeen();
		
		List<String> positiveAnswers= new ArrayList<String>();
		List<String> negativeAnswers= new ArrayList<String>();
		
		positiveAnswers.add("yes");
		positiveAnswers.add("sure");
		positiveAnswers.add("okay");
		
		negativeAnswers.add("no");
		negativeAnswers.add("nope");
		negativeAnswers.add("not");
		
		while(true) {
			Synthesizer.synthesize("By the way, could you help me with the elevator, please?");
			
			String answer= "";
			
			try {
				answer= Recognizer.recognize();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try{
				if (CommonString.isIn(answer, positiveAnswers)){
					main.getStateMachine().Fire(Trigger.USER_HELPING);
					break;
				}else if (CommonString.isIn(answer, negativeAnswers)) {
					main.getStateMachine().Fire(Trigger.USER_NOT_HELPING);
					break;
				}else{
					Synthesizer.synthesize("I'm sorry, I didn't get that.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
