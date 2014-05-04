package edu.kit.anthropomatik.isl.DialogModeling.OpenCV;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import edu.kit.anthropomatik.isl.DialogModeling.Display.ImagePanel;

public class FaceDetectorAdapter {

	private ImagePanel videoStreamPanel; 	//display the video stream
	private ImagePanel facePanel;			//display the detected face
	
	private VideoCapture camera;			
	private CascadeClassifier faceDetector;	
	
	public FaceDetectorAdapter(ImagePanel videoStreamPanel, ImagePanel facePanel, VideoCapture camera) {
		this.videoStreamPanel = videoStreamPanel;
		this.facePanel = facePanel;
		this.camera = camera;
		
		// this detector seems to be the most stable one
		this.faceDetector = new CascadeClassifier("./resources/haarcascade_frontalface_alt_tree.xml");
	}
	
	public void detectFaces() {
		
		// get new frame from camera
		Mat frame = new Mat();
		camera.read(frame);
		Mat original = frame.clone(); // clone is mandatory!
		
		// detect faces
		Mat face = new Mat();
		MatOfRect faces = new MatOfRect();
		faceDetector.detectMultiScale(original, faces);

		// indicate detected faces in video stream
		for (Rect rect : faces.toArray())
			Core.rectangle(original, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0,255,0));

		if (!faces.empty()) {
			// pick largest detected face and display it
			face = original.submat(getLargestFace(faces));
			Imgproc.resize(face, face, new Size(100,100));
			BufferedImage faceFrame = matToImage(face);
			facePanel.setImage(faceFrame);
		}
		
		// always display video stream
		BufferedImage videoStreamFrame = matToImage(original);
		videoStreamPanel.setImage(videoStreamFrame);
		
	}
	
	// returns the largest Rect in the given MatOfRect
	private Rect getLargestFace(MatOfRect facesList) {
		Rect result = new Rect();
		for(Rect rect : facesList.toArray()) {
			if (rect.area() > result.area()) {
				result = rect;
			}
		}
		return result;
	}
	
	// converts the OpenCV Mat type into a BufferedImage
	private BufferedImage matToImage(Mat mat) {
		
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (mat.channels() > 1)
			type = BufferedImage.TYPE_3BYTE_BGR;
		
		int bufferSize = mat.channels() * mat.cols() * mat.rows();
		byte[] b = new byte[bufferSize];
		mat.get(0,0,b);
		
		BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);

		return image;
	}
}
