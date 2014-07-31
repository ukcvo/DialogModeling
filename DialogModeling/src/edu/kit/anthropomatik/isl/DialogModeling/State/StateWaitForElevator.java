package edu.kit.anthropomatik.isl.DialogModeling.State;

import java.util.ArrayList;
import java.util.List;

import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.synthesizer.Synthesizer;

import edu.kit.anthropomatik.isl.DialogModeling.Common.CommonString;

public class StateWaitForElevator extends StateAction {

	protected StateWaitForElevator(Main main) {
		super(main);
	}

	@Override
	public void doIt() {
		List<String> answer= new ArrayList<String>();
		
		outputCurrentState();
		Synthesizer.synthesize("Can you please push the button from the elevator for me?");
		
		try {
			answer= Recognizer.recognizeAllResponses();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (CommonString.isIn(answer, "working")){
			Synthesizer.synthesize("Why is everything at this institute not working as supposed to? ");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Synthesizer.synthesize("Thanks for trying to help me.");
		}else {
			Synthesizer.synthesize("Thank you very much. Have a nice day.");
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
