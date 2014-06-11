package edu.kit.anthropomatik.isl.DialogModeling.OpenCV;

// create header file with command in bin folder:
// javah -d ..\OpenCVAdapter edu.kit.anthropomatik.isl.DialogModeling.OpenCV.OpenCVAdapter

public class OpenCVAdapter {

	static {System.loadLibrary("OpenCVAdapter");}
	
	private native void runOpenCVWindow();
	
	private native void stopOpenCVWindow();
	
	private native int getNumberOfDetectedFaces();
	
	
	public static void main(String[] args) throws InterruptedException {
		OpenCVAdapter adapter = new OpenCVAdapter();
		System.out.println("opening window...");
		adapter.runOpenCVWindow();
		for (int i = 0; i < 10; i++) {
			Thread.sleep(1000);
			System.out.println(adapter.getNumberOfDetectedFaces());
		}
		System.out.println("closing window...");
		adapter.stopOpenCVWindow();
		System.out.println("done");
	}
}
