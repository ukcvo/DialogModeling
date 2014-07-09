package edu.kit.anthropomatik.isl.DialogModeling.State;

import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;

import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.synthesizer.Synthesizer;

public class StateSmallTalk extends StateAction {

	protected StateSmallTalk(Main main) {
		super(main);
	}

	@Override
	public void doIt() {
		outputCurrentState();
		
		try {
			Synthesizer.synthesize("How is your work going right now?");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JavaLayerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String answer= "";
		
		try {
			answer= Recognizer.recognize();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (answer.equals("well")){
			try {
				Synthesizer.synthesize("Oh, that's nice.");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JavaLayerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else {
			try {
				Synthesizer.synthesize("Oh, well");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JavaLayerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		try {
			Thread.sleep(2000);
			main.getStateMachine().Fire(Trigger.WANT_HELP);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
