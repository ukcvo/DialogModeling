package edu.kit.anthropomatik.isl.DialogModeling.OpenCV;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// create header file with command in bin folder:
// javah -d ..\OpenCVAdapter edu.kit.anthropomatik.isl.DialogModeling.OpenCV.OpenCVAdapter

public class OpenCVAdapter {

	static {System.loadLibrary("OpenCVAdapter");}
	
	private native void runOpenCVWindow();
	
	private native void stopOpenCVWindow();
	
	private native int getNumberOfDetectedFaces();
	
	private native void storeCurrentFace(int userID);
	
	public static void main(String[] args) throws InterruptedException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		OpenCVAdapter adapter = new OpenCVAdapter();
		System.out.println("opening window...");
		adapter.runOpenCVWindow();
		
		for (int i = 0; i < 15; i++) {
			br.readLine();
			adapter.storeCurrentFace(2);
		}
		System.out.println("closing window...");
		adapter.stopOpenCVWindow();
		System.out.println("done");
	}
}
