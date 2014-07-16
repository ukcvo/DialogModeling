package edu.kit.anthropomatik.isl.DialogModeling.State;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javazoom.jl.decoder.JavaLayerException;

import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.synthesizer.Synthesizer;

import edu.kit.anthropomatik.isl.DialogModeling.Common.CommonString;
import edu.kit.anthropomatik.isl.DialogModeling.UserModel.User;

public class StateSmallTalk extends StateAction {

	protected StateSmallTalk(Main main) {
		super(main);
	}

	private List<String> GoodMood= new ArrayList<String>();
	private List<String> BadMood= new ArrayList<String>();
	
	@Override
	public void doIt() {
		outputCurrentState();
		
		GoodMood.add("well");
		GoodMood.add("nice");
		GoodMood.add("fine");
		
		BadMood.add("hate");
		BadMood.add("not");
		BadMood.add("bad");
		
		Synthesizer.synthesize("How is your work going right now?");
		
		String answer= "";
		
		try {
			answer= Recognizer.recognize();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (CommonString.isIn(answer, GoodMood)){
			Synthesizer.synthesize("Oh, that's nice.");
			
		}else if (CommonString.isIn(answer, BadMood)) {
			Synthesizer.synthesize("I'm sorry to hear that");
		}else{
			Synthesizer.synthesize("Oh, well");
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
