package edu.kit.anthropomatik.isl.DialogModeling.OpenCV;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
	
	// computes avg over 10 frames
	public double getSmoothedNumberOfDetectedFaces() {
		
		double result = 0;
		
		for(int i = 0; i < 10; i++) {
			result += getNumberOfDetectedFaces()/10.0;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	// gets most popular user id over 10 frames
	public int getSmoothedUserID() {
		
		List<Integer> recognizedIDs = new ArrayList<Integer>();
		int largestID = -1;
		for (int i = 0; i < 10; i++) {
			int id = getRecognizedUserID();
			recognizedIDs.add(id);
			if (id > largestID)
				largestID = id;
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		int[] counts = new int[largestID+2];
		for (int id : recognizedIDs) {
			counts[id+1]++;
		}
		int majorityDecision = 0;
		for (int i = 0; i < counts.length; i++) {
			if(counts[i] > majorityDecision)
				majorityDecision = i;
		}
		
		return (majorityDecision -1);
	}
	
	
	public static void main(String[] args) throws InterruptedException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		OpenCVAdapter adapter = new OpenCVAdapter();
		System.out.println("opening window...");
		adapter.runOpenCVWindow();
		for (int i = 0; i < 15; i++) {
			int id = Integer.parseInt(br.readLine());
			adapter.storeCurrentFace(id);
		}
		System.out.println("closing window...");
		adapter.stopOpenCVWindow();
		System.out.println("done");
	}
}
