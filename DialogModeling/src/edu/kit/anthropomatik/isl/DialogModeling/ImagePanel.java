package edu.kit.anthropomatik.isl.DialogModeling;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.VideoCapture;
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
		
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (img.channels() > 1)
			type = BufferedImage.TYPE_3BYTE_BGR;
		
		int bufferSize = img.channels() * img.cols() * img.rows();
		byte[] b = new byte[bufferSize];
		img.get(0,0,b);
		
		image = new BufferedImage(img.cols(), img.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);
		
		this.repaint();
	}
	
}
