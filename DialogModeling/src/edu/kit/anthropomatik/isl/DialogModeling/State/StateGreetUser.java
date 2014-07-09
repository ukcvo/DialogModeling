package edu.kit.anthropomatik.isl.DialogModeling.State;

import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;

import com.darkprograms.speech.synthesizer.Synthesizer;

public class StateGreetUser extends StateAction {

	protected StateGreetUser(Main main) {
		super(main);
	}

	@Override
	public void doIt() {
		outputCurrentState();

		try {
			Synthesizer.synthesize("Hello"+ main.getCurrentUser().getName() +"!");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JavaLayerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			Thread.sleep(2000);
			main.getStateMachine().Fire(Trigger.USER_GREETS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
