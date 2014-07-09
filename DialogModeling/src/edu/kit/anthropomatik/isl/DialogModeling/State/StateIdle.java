package edu.kit.anthropomatik.isl.DialogModeling.State;

public class StateIdle extends StateAction {

	public StateIdle(Main main) {
		super(main);
	}
	@Override
	public void doIt() {
		outputCurrentState();

		main.setCurrentUser(null);
		
		boolean faceDetected = false;
		
		while (!faceDetected) {
			faceDetected = (main.getOpenCVAdapter().getSmoothedNumberOfDetectedFaces() > 0.5);
		}
		
		try {
			main.getStateMachine().Fire(Trigger.FACE_DETECTED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
