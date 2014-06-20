package edu.kit.anthropomatik.isl.DialogModeling.State;

import java.util.List;

import org.customsoft.stateless4j.*;
import org.customsoft.stateless4j.delegates.Action;

import edu.kit.anthropomatik.isl.DialogModeling.UserModel.SerializationHelper;
import edu.kit.anthropomatik.isl.DialogModeling.UserModel.User;

public class Main {

	private static final String USER_FILE_NAME = "./resources/users.usr";

	public enum State {
		IDLE, RECOGNIZE_USER, SELF_TALK, GREET_USER, ASK_FOR_NAME, SMALL_TALK, 
		COLLECT_USER_DATA, ASK_FOR_HELP, INSULT_USER, WAITING_FOR_ELEVATOR,
		SAY_GOODBYE
	}
	
	public enum Trigger {
		NO_FACE_DETECTED, FACE_DETECTED, USER_RECOGNIZED, USER_NOT_RECOGNIZED,
		USER_UNKNOWN, NOT_UNDERSTOOD, USER_GREETS, WANT_HELP, USER_HELPING, USER_NOT_HELPING,
		JOB_DONE, DIALOG_DONE, SELF_TALK_OVER
	}

	public static void main(String[] args) {
		Action callMultiModalInputCheck = new Action() {
			@Override
			public void doIt() {
				checkMultimodalInput();
			}
		};
		
		Action callSelfTalk = new Action() {
			@Override
			public void doIt() {
				doSelfTalk();
			}
		};
		
		Action callUserRecognition = new Action() {
			@Override
			public void doIt() {
				recognizeUser();
			}
		};
		
		Action callNameRecognition = new Action() {
			@Override
			public void doIt() {
				recognizeName();
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
		
		List<User> users = SerializationHelper.loadUsers(USER_FILE_NAME);
		
		StateMachine<State, Trigger> robotInteraction = new StateMachine<State, Trigger>(State.IDLE);

		try {
			robotInteraction.Configure(State.IDLE)
			.OnEntry(callMultiModalInputCheck)
			.Permit(Trigger.NO_FACE_DETECTED, State.SELF_TALK)
			.Permit(Trigger.FACE_DETECTED, State.RECOGNIZE_USER);
			
			robotInteraction.Configure(State.SELF_TALK)
			.OnEntry(callMultiModalInputCheck)
			.Permit(Trigger.SELF_TALK_OVER, State.IDLE);
						
			robotInteraction.Configure(State.RECOGNIZE_USER)
			.OnEntry(callUserRecognition)
			.Permit(Trigger.USER_RECOGNIZED, State.GREET_USER)
			.Permit(Trigger.USER_NOT_RECOGNIZED, State.ASK_FOR_NAME);

			robotInteraction.Configure(State.ASK_FOR_NAME)
			.OnEntry(callNameRecognition)
			.Permit(Trigger.USER_RECOGNIZED, State.GREET_USER)
			.Permit(Trigger.USER_UNKNOWN, State.COLLECT_USER_DATA);
			
			robotInteraction.Configure(State.COLLECT_USER_DATA)
			.OnEntry(callNameRecognition)
			.Permit(Trigger.USER_RECOGNIZED, State.GREET_USER);
			
			robotInteraction.Configure(State.GREET_USER)
			.OnEntry(callUserInteraction)
			.Permit(Trigger.USER_GREETS, State.SMALL_TALK);
			
			robotInteraction.Configure(State.SMALL_TALK)
			.OnEntry(callNameRecognition)
			.Permit(Trigger.WANT_HELP, State.ASK_FOR_HELP);
			
			robotInteraction.Configure(State.ASK_FOR_HELP)
			.OnEntry(callNameRecognition)
			.Permit(Trigger.USER_HELPING, State.WAITING_FOR_ELEVATOR)
			.Permit(Trigger.USER_NOT_HELPING, State.SAY_GOODBYE);
			
			robotInteraction.Configure(State.WAITING_FOR_ELEVATOR)
			.OnEntry(callNameRecognition)
			.Permit(Trigger.JOB_DONE, State.SAY_GOODBYE);
			
			robotInteraction.Configure(State.INSULT_USER)
			.OnEntry(callNameRecognition)
			.Permit(Trigger.JOB_DONE, State.SAY_GOODBYE);
			
			robotInteraction.Configure(State.SAY_GOODBYE)
			.OnEntry(callNameRecognition)
			.Permit(Trigger.DIALOG_DONE, State.IDLE);
			
			robotInteraction.Fire(Trigger.FACE_DETECTED);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Current State: " +robotInteraction.getState());

		SerializationHelper.storeUsers(users, USER_FILE_NAME);
	}

	protected static void recognizeName() {
		// TODO Auto-generated method stub
		
	}

	protected static void doSelfTalk() {
		// TODO Auto-generated method stub
		
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
