package edu.kit.anthropomatik.isl.DialogModeling.State;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javazoom.jl.player.Player;

import org.customsoft.stateless4j.StateMachine;
import org.customsoft.stateless4j.delegates.Action;

import com.darkprograms.speech.synthesiser.Synthesiser;

import edu.kit.anthropomatik.isl.DialogModeling.Input.ConsoleInput;
import edu.kit.anthropomatik.isl.DialogModeling.Input.IInput;
import edu.kit.anthropomatik.isl.DialogModeling.OpenCV.OpenCVAdapter;
import edu.kit.anthropomatik.isl.DialogModeling.Output.ConsoleOutput;
import edu.kit.anthropomatik.isl.DialogModeling.Output.IOutput;
import edu.kit.anthropomatik.isl.DialogModeling.UserModel.SerializationHelper;
import edu.kit.anthropomatik.isl.DialogModeling.UserModel.User;

public class Main {

	private static final String USER_FILE_NAME = "./resources/users.usr";

	private List<User> users;
	
	private StateMachine<State, Trigger> robotInteraction;
	
	private OpenCVAdapter openCVAdapter;
	
	private User currentUser;
	
	private IOutput output;
	
	private IInput input;
	
	public Main() {
		Action callMultiModalInputCheck = new Action() {
			@Override
			public void doIt() {
				outputCurrentState();
				checkMultimodalInput();
			}
		};
		
		Action callSelfTalk = new Action() {
			@Override
			public void doIt() {
				outputCurrentState();
				doSelfTalk();
			}
		};
		
		Action callUserRecognition = new Action() {
			@Override
			public void doIt() {
				outputCurrentState();
				recognizeUser();
			}
		};
		
		Action callNameRecognition = new Action() {
			@Override
			public void doIt() {
				outputCurrentState();
				recognizeName();
			}
		};
		
		Action callUserInteraction = new Action() {
			@Override
			public void doIt() {
				outputCurrentState();
				interactWithUser();
			}
		};
		
		Action callThisAndThat = new Action() {
			@Override
			public void doIt() {
				outputCurrentState();
				doThisAndThat();
			}
		};
		
		users = SerializationHelper.loadUsers(USER_FILE_NAME);
		openCVAdapter = new OpenCVAdapter();
		
		// TODO: replace this with our actual speech I/O
		input = new ConsoleInput();
		output = new ConsoleOutput();
		
		robotInteraction = new StateMachine<State, Trigger>(State.INIT);

		try {
			robotInteraction.Configure(State.INIT)
			.Permit(Trigger.INITIALIZED, State.IDLE);
			
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
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void outputCurrentState() {
		System.out.println("Current State: " +robotInteraction.getState());
	}
	
	public void shutDown() {
		openCVAdapter.stopOpenCVWindow();
		SerializationHelper.storeUsers(users, USER_FILE_NAME);
	}
	
	public void run() {
		openCVAdapter.runOpenCVWindow();
		try {
			robotInteraction.Fire(Trigger.INITIALIZED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void recognizeName() {
		// TODO Auto-generated method stub
		while (true) { }
	}

	protected void doSelfTalk() {
		// TODO Auto-generated method stub
		
	}

	protected void doThisAndThat() {
		// TODO Auto-generated method stub
		
	}

	protected void recognizeUser() {
		
		int userID = openCVAdapter.getSmoothedUserID();
		
		if (userID >= 0){ 
			currentUser = users.get(userID);
			System.out.println("Recognized user: " + currentUser.toString());
		} else {
			currentUser = null;
		}
		
		try {
			if (currentUser != null) {
				robotInteraction.Fire(Trigger.USER_RECOGNIZED);
			} else {
				robotInteraction.Fire(Trigger.USER_NOT_RECOGNIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	protected void checkMultimodalInput() {
		
		currentUser = null;
		
		boolean faceDetected = false;
		
		while (!faceDetected) {
			faceDetected = (openCVAdapter.getSmoothedNumberOfDetectedFaces() > 0.5);
		}
		
		try {
			robotInteraction.Fire(Trigger.FACE_DETECTED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void interactWithUser() {
		// TODO Auto-generated method stub
		Synthesiser synth = new Synthesiser(Synthesiser.LANG_UK_ENGLISH);
		
		InputStream data;
		try {
			data = synth.getMP3Data("Hello " + currentUser.getName() + "!");

			//TODO Use any Java MP3 Implementation to play back the AudioFile from the InputStream.
			
			Player player = new Player(data);
			player.play();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(true){}
	}

	public static void main(String[] args) {
		
		Main myMain = new Main();
		myMain.run();
		myMain.shutDown();
	}
}
