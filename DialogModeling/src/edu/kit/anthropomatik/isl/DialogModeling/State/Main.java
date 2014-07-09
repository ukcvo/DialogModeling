package edu.kit.anthropomatik.isl.DialogModeling.State;

import java.io.InputStream;
import java.util.List;

import javazoom.jl.player.Player;

import org.customsoft.stateless4j.StateMachine;
import org.customsoft.stateless4j.delegates.Action;

import com.darkprograms.speech.synthesizer.Synthesizer;

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
		
		Action callCollectUserData = new Action() {
			@Override
			public void doIt() {
				outputCurrentState();
				collectUserData();
			}
		};
		
		Action callUserGreeting = new Action() {
			@Override
			public void doIt() {
				outputCurrentState();
				greetUser();
			}
		};
		
		Action callSmallTalk = new Action() {
			@Override
			public void doIt() {
				outputCurrentState();
				doSmallTalk();
			}
		};
		
		
		Action callAskForHelp = new Action() {
			@Override
			public void doIt() {
				outputCurrentState();
				askForHelp();
			}
		};
		
		Action callInsultUser = new Action() {
			@Override
			public void doIt() {
				outputCurrentState();
				insultUser();
			}
		};
		
		Action callWaitForElevator = new Action() {
			@Override
			public void doIt() {
				outputCurrentState();
				waitForElevator();
			}
		};
		
		Action callSayGoodBye = new Action() {
			@Override
			public void doIt() {
				outputCurrentState();
				sayGoodbyeToUser();
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
			.OnEntry(callSelfTalk)
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
			.OnEntry(callCollectUserData)
			.Permit(Trigger.USER_RECOGNIZED, State.GREET_USER);
			
			robotInteraction.Configure(State.GREET_USER)
			.OnEntry(callUserGreeting)
			.Permit(Trigger.USER_GREETS, State.SMALL_TALK);
			
			robotInteraction.Configure(State.SMALL_TALK)
			.OnEntry(callSmallTalk)
			.Permit(Trigger.WANT_HELP, State.ASK_FOR_HELP);
			
			robotInteraction.Configure(State.ASK_FOR_HELP)
			.OnEntry(callAskForHelp)
			.Permit(Trigger.USER_HELPING, State.WAITING_FOR_ELEVATOR)
			.Permit(Trigger.USER_NOT_HELPING, State.INSULT_USER);
			
			robotInteraction.Configure(State.WAITING_FOR_ELEVATOR)
			.OnEntry(callWaitForElevator)
			.Permit(Trigger.JOB_DONE, State.SAY_GOODBYE);
			
			robotInteraction.Configure(State.INSULT_USER)
			.OnEntry(callInsultUser)
			.Permit(Trigger.JOB_DONE, State.SAY_GOODBYE);
			
			robotInteraction.Configure(State.SAY_GOODBYE)
			.OnEntry(callSayGoodBye)
			.Permit(Trigger.DIALOG_DONE, State.IDLE);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void sayGoodbyeToUser() {
		// TODO Auto-generated method stub
		
		try {
			Thread.sleep(2000);
			robotInteraction.Fire(Trigger.DIALOG_DONE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void waitForElevator() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(2000);
			robotInteraction.Fire(Trigger.JOB_DONE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void insultUser() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(2000);
			robotInteraction.Fire(Trigger.JOB_DONE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void askForHelp() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(2000);
			if (Math.random() > 0.5)
				robotInteraction.Fire(Trigger.USER_HELPING);
			else
				robotInteraction.Fire(Trigger.USER_NOT_HELPING);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doSmallTalk() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(2000);
			robotInteraction.Fire(Trigger.WANT_HELP);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void collectUserData() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(2000);
			robotInteraction.Fire(Trigger.USER_RECOGNIZED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void recognizeName() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(2000);
			if (Math.random() > 0.5)
				robotInteraction.Fire(Trigger.USER_RECOGNIZED);
			else
				robotInteraction.Fire(Trigger.USER_UNKNOWN);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doSelfTalk() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(2000);
			robotInteraction.Fire(Trigger.SELF_TALK_OVER);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	protected void greetUser() {
		// TODO Auto-generated method stub
		Synthesizer synth = new Synthesizer(Synthesizer.LANG_UK_ENGLISH);
		
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
		
		try {
			Thread.sleep(2000);
			robotInteraction.Fire(Trigger.USER_GREETS);
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

	public static void main(String[] args) {
		
		Main myMain = new Main();
		myMain.run();
		myMain.shutDown();
	}
}
