package edu.kit.anthropomatik.isl.DialogModeling;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.highgui.VideoCapture;

public class Main {

	private static JFrame window; 		// main window
	private static ImagePanel image;	// will display our video stream
	
	public static void main(String[] args) {
		
		// load OpenCV library
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		// grab the camera
		VideoCapture camera = new VideoCapture(0);
		if (!camera.isOpened()) {
			System.err.println("Error! No default video device found...");
			return;
		}
		
		// set up the display window
		window = new JFrame("DialogModeling");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setMinimumSize(new Dimension(640, 480));
		image = new ImagePanel(camera); 
		window.getContentPane().add(image);
		window.pack();
		window.setVisible(true);
	}
}
