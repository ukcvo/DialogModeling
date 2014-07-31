package edu.kit.anthropomatik.isl.DialogModeling.State;

import com.darkprograms.speech.synthesizer.Synthesizer;

import edu.kit.anthropomatik.isl.DialogModeling.UserModel.SerializationHelper;

public class StateSayGoodbye extends StateAction {

	protected StateSayGoodbye(Main main) {
		super(main);
	}

	@Override
	public void doIt() {
		outputCurrentState();
		
		Synthesizer.synthesize("Goodbye, " + main.getCurrentUser().getName());
		
		try {
			Thread.sleep(2000);
			main.storeUsers();
			main.getStateMachine().Fire(Trigger.DIALOG_DONE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
