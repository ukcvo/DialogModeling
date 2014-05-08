package edu.kit.anthropomatik.isl.DialogModeling.OpenCV;

import java.awt.image.BufferedImage;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_highgui;
import org.bytedeco.javacpp.opencv_highgui.CvCapture;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;

import edu.kit.anthropomatik.isl.DialogModeling.Display.ImagePanel;

public class FaceDetectorAdapter {

	private ImagePanel videoStreamPanel; 	//display the video stream
	private ImagePanel facePanel;			//display the detected face
	
	private CvCapture camera;
	private CascadeClassifier faceDetector;	
	
	public FaceDetectorAdapter(ImagePanel videoStreamPanel, ImagePanel facePanel) {
		this.videoStreamPanel = videoStreamPanel;
		this.facePanel = facePanel;

		// grab the camera
		this.camera = opencv_highgui.cvCreateCameraCapture(0);
		// this detector seems to be the most stable one
		this.faceDetector = new CascadeClassifier("./resources/haarcascade_frontalface_alt_tree.xml");
	}
	
	public void detectFaces() {
		
		// get new frame from camera
		IplImage original = opencv_highgui.cvQueryFrame(camera);
		Mat matOfOriginal = new Mat(original);
		
		// detect faces
		Rect face = new Rect();
		faceDetector.detectMultiScale(matOfOriginal, face);
		// TODO Lucas: use other detector?
		// TODO Lucas: CvHaarClassifierCascade cascade = new CvHaarClassifierCascade(cvLoad(CASCADE_FILE));

		if (face.area() > 0) {	// we detected a face
			
			// cut it out			
			IplImage faceImage = IplImage.create(original.cvSize(), original.depth(), original.nChannels());
			opencv_core.cvCopy(original, faceImage);
			opencv_core.cvSetImageROI(faceImage, opencv_core.cvRect(face.x(), face.y(), face.width(), face.height()));
			
			// now resize it to 100x100px
			IplImage faceCropped = IplImage.create(100, 100, original.depth(), original.nChannels());
			opencv_imgproc.cvResize(faceImage, faceCropped);
			
			// and finally display it
			BufferedImage faceFrame = new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR);
			faceCropped.copyTo(faceFrame);
			facePanel.setImage(faceFrame);
		}
		
		// put a rectangle into the video stream to show detected face
		opencv_core.cvRectangle(original, opencv_core.cvPoint(face.x(), face.y()), opencv_core.cvPoint(face.x() + face.width(), face.y() + face.height()), opencv_core.cvScalar(0, 255, 0, 0));
		
		// always display video stream
		BufferedImage videoStreamFrame = new BufferedImage(640, 480, BufferedImage.TYPE_3BYTE_BGR);
		original.copyTo(videoStreamFrame); //matToImage(original);
		videoStreamPanel.setImage(videoStreamFrame);
		
	}
	
	// commented out for now, but might be useful in the near future; TODO Lucas: Reuse or delete
	// returns the largest Rect in the given MatOfRect
//	private Rect getLargestFace(MatOfRect facesList) {
//		Rect result = new Rect();
//		for(Rect rect : facesList.toArray()) {
//			if (rect.area() > result.area()) {
//				result = rect;
//			}
//		}
//		return result;
//	}
}
