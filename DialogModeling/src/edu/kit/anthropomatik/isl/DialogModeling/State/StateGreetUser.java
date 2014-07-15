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

		Synthesizer.synthesize("Hello"+ main.getCurrentUser().getName() +"!");
				
		try {
			Thread.sleep(2000);
			main.getStateMachine().Fire(Trigger.USER_GREETS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
