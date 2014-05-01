package edu.kit.anthropomatik.isl.DialogModeling;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private BufferedImage image; // the current image to display
	
	private VideoCapture camera;
	
	private CascadeClassifier faceDetector;
	
	public ImagePanel(VideoCapture camera) {
		super();
		this.camera = camera;
		this.image = new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR);
		this.faceDetector = new CascadeClassifier("./resources/haarcascade_frontalface_alt_tree.xml");
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// get new frame
		Mat frame = new Mat();
		camera.read(frame);
		Mat original = frame.clone(); // clone is mandatory!
		
		// detect faces
		MatOfRect detectedFaces = new MatOfRect();
		faceDetector.detectMultiScale(original, detectedFaces);
		
		// illustrate faces
		for (Rect rect : detectedFaces.toArray()) {
			Core.rectangle(original, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0,255,0));
		}
		
		displayImage(original);
		
		g.drawImage(image, 0, 0, null);
	}
	
	private void displayImage(Mat img) {
		
		Imgproc.resize(img, img, new Size(640, 480));
	    MatOfByte matOfByte = new MatOfByte();
	    Highgui.imencode(".jpg", img, matOfByte);
	    byte[] byteArray = matOfByte.toArray();
	    
	    try {
	    	
	        InputStream in = new ByteArrayInputStream(byteArray);
	        image = ImageIO.read(in);
	        this.repaint();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
}
