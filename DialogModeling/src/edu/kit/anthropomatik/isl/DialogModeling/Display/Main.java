package edu.kit.anthropomatik.isl.DialogModeling.Display;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_highgui.VideoCapture;

import edu.kit.anthropomatik.isl.DialogModeling.OpenCV.FaceDetectorAdapter;

public class Main {

	private static JFrame window; 				// main window
	private static JPanel panel;
	private static ImagePanel videoStreamImage;	// will display our video stream
	private static ImagePanel faceImage; 		// will display the currently selected face
	
	public static void main(String[] args) {
		
		// load OpenCV library
//		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Loader.load(org.bytedeco.javacpp.opencv_core.class); 		
		
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
		panel = new JPanel();
		
		videoStreamImage = new ImagePanel();
		videoStreamImage.setPreferredSize(new Dimension(640, 480));
		panel.add(videoStreamImage);
		
		faceImage = new ImagePanel();
		faceImage.setPreferredSize(new Dimension(100,100));
		panel.add(faceImage);
		
		window.add(panel);
		window.pack();
		window.setVisible(true);
		
		// now do the face detection
		FaceDetectorAdapter faceDetector = new FaceDetectorAdapter(videoStreamImage, faceImage, camera);
		while(true) {
			faceDetector.detectFaces();
		}
	}
}
