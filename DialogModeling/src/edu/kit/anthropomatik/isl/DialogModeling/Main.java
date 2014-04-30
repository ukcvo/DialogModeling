package edu.kit.anthropomatik.isl.DialogModeling;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class Main {

	private static JFrame window;
	private static ImagePanel image;
	
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
		image = new ImagePanel();
		window.getContentPane().add(image);
		window.pack();
		window.setVisible(true);
		
		int i = 0;
		while (true) {
			// grab next frame from camera
			Mat frame = new Mat();
			camera.read(frame);
			Mat original = frame.clone(); // clone is mandatory!
			
			// TODO: Lucas --> face detection code
			
			// display result
			showResult(original);
			System.out.println(i++);
		}
		
	}
	
	private static void showResult(Mat img) {
		Imgproc.resize(img, img, new Size(640, 480));
	    MatOfByte matOfByte = new MatOfByte();
	    Highgui.imencode(".jpg", img, matOfByte);
	    byte[] byteArray = matOfByte.toArray();
	    BufferedImage bufImage = null;
	    try {
	        InputStream in = new ByteArrayInputStream(byteArray);
	        bufImage = ImageIO.read(in);
	        image.setImage(bufImage);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
