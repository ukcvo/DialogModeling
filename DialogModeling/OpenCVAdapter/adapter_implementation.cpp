#include "adapter_implementation.hpp"

#include "opencv2/core/core.hpp"
#include "opencv2/contrib/contrib.hpp"
#include "opencv2/highgui/highgui.hpp"

#include <iostream>
#include <thread>

using namespace std;
using namespace cv;

thread *windowThread;
bool stopOpenCVLoop;
Mat currentFace;

void startOpenCVWindow() {
	windowThread = new thread(displayOpenCVWindow);
}

void closeOpenCVWindow() {
	stopOpenCVLoop = true;
	windowThread->join();
}

void storeCurrentFace() {
	static int i = 0;
	ostringstream buf;
	
	buf << "img" << i++ << ".jpg";
	imwrite(buf.str(), currentFace);
	cout << _CPP_DEBUG_PREFIX << "stored " << buf.str() << endl;
}

void displayOpenCVWindow() {
	cout << _CPP_DEBUG_PREFIX << "creating openCV window" << endl;
	
	vector<Mat> images;
	vector<int> labels;

	CascadeClassifier haar_cascade;
	string fn_haar = ".\\resources\\haarcascade_frontalface_alt_tree.xml";
	haar_cascade.load(fn_haar);
	
	int deviceId = 0;
	VideoCapture cap(deviceId);
	
	if (!cap.isOpened()) {
		cerr << "Capture Device ID " << deviceId << "cannot be opened." << endl;
		return;
	}

	stopOpenCVLoop = false;

	Mat frame;
	while(!stopOpenCVLoop) {
		cap >> frame;
		Mat original = frame.clone();
		
		Mat gray;
		cvtColor(original, gray, CV_BGR2GRAY);
		
		vector< Rect_<int> > faces;
		haar_cascade.detectMultiScale(gray, faces);
		
		numberOfDetectedFaces = faces.size();
		Rect largestFace = *(new Rect(0, 0, 0, 0));

		for (int i = 0; i < faces.size(); i++) {
			Rect face_i = faces[i];
			
			// highlight face
			rectangle(original, face_i, CV_RGB(0, 255, 0), 1);

			if (face_i.area() > largestFace.area())
				largestFace = face_i;
		}
		
		Mat face = gray(largestFace);
		Mat face_resized;
		cv::resize(face, face_resized, Size(100, 100), 1.0, 1.0, INTER_CUBIC);

		currentFace = face_resized;

		imshow("OpenCV Face Detection", original);
		
		// exit on Esc
		char key = (char)waitKey(20);
		//if (key == 27)
		//	break;
	}

	return;
}