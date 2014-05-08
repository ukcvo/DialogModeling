package edu.kit.anthropomatik.isl.DialogModeling.OpenCV;

import java.awt.image.BufferedImage;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_highgui;
import org.bytedeco.javacpp.opencv_highgui.CvCapture;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;

import edu.kit.anthropomatik.isl.DialogModeling.Display.ImagePanel;

public class FaceDetectorAdapter {

	private ImagePanel videoStreamPanel; 	//display the video stream
	private ImagePanel facePanel;			//display the detected face
	
	private CvCapture camera;				
	private CvHaarClassifierCascade faceDetector;
	private CvMemStorage storage;
	
	public FaceDetectorAdapter(ImagePanel videoStreamPanel, ImagePanel facePanel) {
		this.videoStreamPanel = videoStreamPanel;
		this.facePanel = facePanel;

		// grab the camera
		this.camera = opencv_highgui.cvCreateCameraCapture(0);
		// this detector seems to be the most stable one
		this.faceDetector = new CvHaarClassifierCascade(opencv_core.cvLoad("./resources/haarcascade_frontalface_alt_tree.xml"));
		this.storage = CvMemStorage.create();
	}
	
	public void detectFaces() {
		
		// get new frame from camera
		IplImage original = opencv_highgui.cvQueryFrame(camera);
		
		// detect faces
		Rect face = new Rect();
		CvSeq myFaces = opencv_objdetect.cvHaarDetectObjects(original, faceDetector, storage, 1.1, 1, 0);
		
		for (int i = 0; i < myFaces.total(); i++) {
			Rect r = new Rect(opencv_core.cvGetSeqElem(myFaces, i));
			if (r.area() > face.area()) // pick largest face
				face = r;
			// put a rectangle into the video stream to highlight detected face
			opencv_core.cvRectangle(original, opencv_core.cvPoint(r.x(), r.y()), opencv_core.cvPoint(r.x() + r.width(), r.y() + r.height()), opencv_core.cvScalar(0, 255, 0, 0));
		}
		
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
		
		// always display video stream
		BufferedImage videoStreamFrame = new BufferedImage(640, 480, BufferedImage.TYPE_3BYTE_BGR);
		original.copyTo(videoStreamFrame); //matToImage(original);
		videoStreamPanel.setImage(videoStreamFrame);
		
	}
}
