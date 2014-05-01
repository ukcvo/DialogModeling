package edu.kit.anthropomatik.isl.DialogModeling;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private BufferedImage image;
	
	private VideoCapture camera;
	
	public ImagePanel(VideoCapture camera) {
		super();
		this.camera = camera;
		image = new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR);
	}
	
	public void setImage(BufferedImage img) {
		this.image = img;
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Mat frame = new Mat();
		camera.read(frame);
		Mat original = frame.clone(); // clone is mandatory!
		
		// TODO: Lucas --> face detection code
		
		// display result
		showResult(original);
		System.out.println("beep");
		
		g.drawImage(image, 0, 0, null);
	}
	
	private void showResult(Mat img) {
		Imgproc.resize(img, img, new Size(640, 480));
	    MatOfByte matOfByte = new MatOfByte();
	    Highgui.imencode(".jpg", img, matOfByte);
	    byte[] byteArray = matOfByte.toArray();
	    BufferedImage bufImage = null;
	    try {
	        InputStream in = new ByteArrayInputStream(byteArray);
	        bufImage = ImageIO.read(in);
	        setImage(bufImage);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
}
