package edu.kit.anthropomatik.isl.DialogModeling.OpenCV;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// create header file with command in bin folder:
// javah -d ..\OpenCVAdapter edu.kit.anthropomatik.isl.DialogModeling.OpenCV.OpenCVAdapter

public class OpenCVAdapter {

	static {System.loadLibrary("OpenCVAdapter");}
	
	public native void runOpenCVWindow();
	
	public native void stopOpenCVWindow();
	
	public native int getNumberOfDetectedFaces();
	
	public native int getRecognizedUserID();
	
	public native int getRecognitionConfidence();
	
	public native void storeCurrentFace(int userID);
	
	public static void main(String[] args) throws InterruptedException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		OpenCVAdapter adapter = new OpenCVAdapter();
		System.out.println("opening window...");
		adapter.runOpenCVWindow();
		for (int i = 0; i < 15; i++) {
			br.readLine();
			adapter.storeCurrentFace(adapter.getRecognizedUserID());
		}
		System.out.println("closing window...");
		adapter.stopOpenCVWindow();
		System.out.println("done");
	}
}
