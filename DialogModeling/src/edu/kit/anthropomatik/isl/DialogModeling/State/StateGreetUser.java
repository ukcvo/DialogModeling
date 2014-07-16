package edu.kit.anthropomatik.isl.DialogModeling.State;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javazoom.jl.decoder.JavaLayerException;

import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.synthesizer.Synthesizer;

import edu.kit.anthropomatik.isl.DialogModeling.Common.CommonString;

public class StateGreetUser extends StateAction {

	protected StateGreetUser(Main main) {
		super(main);
	}

	private List<String> GoodMood= new ArrayList<String>();
	private List<String> BadMood= new ArrayList<String>();
	
	@Override
	public void doIt() {
		String answer= "";
		
		GoodMood.add("well");
		GoodMood.add("nice");
		GoodMood.add("fine");
		
		BadMood.add("hate");
		BadMood.add("not");
		BadMood.add("bad");
		outputCurrentState();

		Synthesizer.synthesize("Hello"+ main.getCurrentUser().getName() +"! How are you?");
		
		try {
			answer= Recognizer.recognize();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (CommonString.isIn(answer, main.getCurrentUser().getName())){
			Synthesizer.synthesize("Oh, it seems that I got your name wrong");
			try {
				Thread.sleep(2000);
				main.getStateMachine().Fire(Trigger.USER_NOT_RECOGNIZED);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if (CommonString.isIn(answer, GoodMood)) {
			Synthesizer.synthesize("Good for you");
		}else{
			Synthesizer.synthesize("Alright");
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
