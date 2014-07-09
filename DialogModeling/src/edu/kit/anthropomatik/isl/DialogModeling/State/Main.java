package edu.kit.anthropomatik.isl.DialogModeling.State;

import java.util.List;

import org.customsoft.stateless4j.StateMachine;
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
	
	private StateMachine<State, Trigger> stateMachine;
	
	private OpenCVAdapter openCVAdapter;
	
	private User currentUser;
	
	private IOutput output;
	
	private IInput input;
	
	public Main() {
		
		this.users = SerializationHelper.loadUsers(USER_FILE_NAME);
		this.openCVAdapter = new OpenCVAdapter();
		
		// TODO: replace this with our actual speech I/O
		input = new ConsoleInput();
		output = new ConsoleOutput();
		
		stateMachine = new StateMachine<State, Trigger>(State.INIT);

		try {
			stateMachine.Configure(State.INIT)
			.Permit(Trigger.INITIALIZED, State.IDLE);
			
			stateMachine.Configure(State.IDLE)
			.OnEntry(new StateIdle(this))
			.Permit(Trigger.NO_FACE_DETECTED, State.SELF_TALK)
			.Permit(Trigger.FACE_DETECTED, State.RECOGNIZE_USER);
			
			stateMachine.Configure(State.SELF_TALK)
			.OnEntry(new StateSelfTalk(this))
			.Permit(Trigger.SELF_TALK_OVER, State.IDLE);
						
			stateMachine.Configure(State.RECOGNIZE_USER)
			.OnEntry(new StateRecognizeUser(this))
			.Permit(Trigger.USER_RECOGNIZED, State.GREET_USER)
			.Permit(Trigger.USER_NOT_RECOGNIZED, State.ASK_FOR_NAME);

			stateMachine.Configure(State.ASK_FOR_NAME)
			.OnEntry(new StateAskForName(this))
			.Permit(Trigger.USER_RECOGNIZED, State.GREET_USER)
			.Permit(Trigger.USER_UNKNOWN, State.COLLECT_USER_DATA);
			
			stateMachine.Configure(State.COLLECT_USER_DATA)
			.OnEntry(new StateCollectUserData(this))
			.Permit(Trigger.USER_RECOGNIZED, State.GREET_USER);
			
			stateMachine.Configure(State.GREET_USER)
			.OnEntry(new StateGreetUser(this))
			.Permit(Trigger.USER_GREETS, State.SMALL_TALK);
			
			stateMachine.Configure(State.SMALL_TALK)
			.OnEntry(new StateSmallTalk(this))
			.Permit(Trigger.WANT_HELP, State.ASK_FOR_HELP);
			
			stateMachine.Configure(State.ASK_FOR_HELP)
			.OnEntry(new StateAskForHelp(this))
			.Permit(Trigger.USER_HELPING, State.WAITING_FOR_ELEVATOR)
			.Permit(Trigger.USER_NOT_HELPING, State.INSULT_USER);
			
			stateMachine.Configure(State.WAITING_FOR_ELEVATOR)
			.OnEntry(new StateWaitForElevator(this))
			.Permit(Trigger.JOB_DONE, State.SAY_GOODBYE);
			
			stateMachine.Configure(State.INSULT_USER)
			.OnEntry(new StateInsultUser(this))
			.Permit(Trigger.JOB_DONE, State.SAY_GOODBYE);
			
			stateMachine.Configure(State.SAY_GOODBYE)
			.OnEntry(new StateSayGoodbye(this))
			.Permit(Trigger.DIALOG_DONE, State.IDLE);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void run() {
		getOpenCVAdapter().runOpenCVWindow();
		try {
			stateMachine.Fire(Trigger.INITIALIZED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void shutDown() {
		getOpenCVAdapter().stopOpenCVWindow();
		SerializationHelper.storeUsers(getUsers(), USER_FILE_NAME);
	}
	
	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public OpenCVAdapter getOpenCVAdapter() {
		return openCVAdapter;
	}

	public List<User> getUsers() {
		return users;
	}

	public StateMachine<State, Trigger> getStateMachine() {
		return stateMachine;
	}	
	
	public static void main(String[] args) {
		
		Main myMain = new Main();
		myMain.run();
		myMain.shutDown();
	}
}
