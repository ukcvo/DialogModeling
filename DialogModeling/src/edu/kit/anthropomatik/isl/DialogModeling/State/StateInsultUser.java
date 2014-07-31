package edu.kit.anthropomatik.isl.DialogModeling.State;

import java.util.ArrayList;
import java.util.List;

import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.synthesizer.Synthesizer;

import edu.kit.anthropomatik.isl.DialogModeling.Common.CommonString;

public class StateInsultUser extends StateAction {

	protected StateInsultUser(Main main) {
		super(main);
	}

	@Override
	public void doIt() {
		List<String> answer= new ArrayList<String>();
		
		outputCurrentState();
		Synthesizer.synthesize("Such a pity you can't help me. Where is the problem?");
		
		try {
			answer= Recognizer.recognizeAllResponses();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (CommonString.isIn(answer, "time")){
			Synthesizer.synthesize("Oh, I can understand that.");
						
		}else{
			Synthesizer.synthesize("I don't want to talk to you anymore.");
		}
		
		try {
			Thread.sleep(1000);
			main.getStateMachine().Fire(Trigger.JOB_DONE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
