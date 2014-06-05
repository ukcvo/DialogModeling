package edu.kit.anthropomatik.isl.DialogModeling.OpenCV;

// create header file with command in bin folder:
// javah -d ..\OpenCVAdapter edu.kit.anthropomatik.isl.DialogModeling.OpenCV.OpenCVAdapter

public class OpenCVAdapter {

	static {System.loadLibrary("OpenCVAdapter");}
	
	private native void runOpenCVWindow();
	
	public static void main(String[] args) {
		System.out.println("calling C++ functionality...");
		new OpenCVAdapter().runOpenCVWindow();
		System.out.println("done");
	}
}
