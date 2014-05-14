package edu.kit.anthropomatik.isl.DialogModeling.State;

import org.customsoft.stateless4j.*;
import org.customsoft.stateless4j.delegates.Action;

public class Main {

	public enum State {
		IDLE, RECOGNIZE_USER, GREET_USER, ASK_USER_FOR_NAME, SMALLTALK
	}
	
	public enum Trigger {
		HEARED_USER, SEEN_USER, 
		USER_RECOGNIZED, USER_NOT_RECOGNIZED, 
		USER_GREETS, USER_BUSY
	}

	public static void main(String[] args) {
		Action callMultiModalInputCheck = new Action() {
			@Override
			public void doIt() {
				checkMultimodalInput();
			}
		};
		Action callUserRecognition = new Action() {
			@Override
			public void doIt() {
				recognizeUser();
			}
		};
		
		Action callUserInteraction = new Action() {
			@Override
			public void doIt() {
				interactWithUser();
			}
		};
		
		Action callThisAndThat = new Action() {
			@Override
			public void doIt() {
				doThisAndThat();
			}
		};
		
		StateMachine<State, Trigger> robotInteraction = new StateMachine<State, Trigger>(State.IDLE);

		try {
			robotInteraction.Configure(State.IDLE)
			.OnEntry(callMultiModalInputCheck)
			.Permit(Trigger.HEARED_USER, State.RECOGNIZE_USER)
			.Permit(Trigger.SEEN_USER, State.RECOGNIZE_USER);
						
			robotInteraction.Configure(State.RECOGNIZE_USER)
			.OnEntry(callUserRecognition)
			.Permit(Trigger.USER_RECOGNIZED, State.GREET_USER)
			.Permit(Trigger.USER_NOT_RECOGNIZED, State.ASK_USER_FOR_NAME);

			robotInteraction.Configure(State.GREET_USER)
			.OnEntry(callUserInteraction)
			.OnExit(callThisAndThat)
			.Permit(Trigger.USER_GREETS, State.SMALLTALK)
			.Permit(Trigger.USER_BUSY, State.IDLE);
			
			//...
			
			
			robotInteraction.Fire(Trigger.SEEN_USER);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Current State: " +robotInteraction.getState());

	}

	protected static void doThisAndThat() {
		// TODO Auto-generated method stub
		
	}

	protected static void recognizeUser() {
		// TODO Auto-generated method stub
		
	}

	protected static void checkMultimodalInput() {
		// TODO Auto-generated method stub
		
	}
	
	protected static void interactWithUser() {
		// TODO Auto-generated method stub
		
	}

}
