#include "adapter_implementation.hpp"

#include "opencv2/core/core.hpp"
#include "opencv2/contrib/contrib.hpp"
#include "opencv2/highgui/highgui.hpp"

#include <iostream>
#include <thread>
#include <windows.h>
#include <fstream>
#include <sstream>
using namespace std;
using namespace cv;

#define IMAGE_INDEX_FILE_PATH "./resources/images/imageIndex.csv" 

thread *windowThread;
bool stopOpenCVLoop;
Mat currentFace;
Ptr<FaceRecognizer> faceRecognizer;
vector<Mat> images;
vector<int> labels;

void append_csv(const string& filename, string pathToFile, int label, char separator = ';') {
	ofstream outfile;
	outfile.open(filename, ofstream::out | ofstream::app);
	if (!outfile) {
		string error_message = "No valid input file was given, please check the given filename.";
		CV_Error(CV_StsBadArg, error_message);
	}

	outfile << endl << pathToFile << separator << label;
	outfile.close();
}

void read_csv(const string& filename, vector<Mat>& images, vector<int>& labels, char separator = ';') {
	ifstream file(filename.c_str(), ifstream::in);
	if (!file) {
		string error_message = "No valid input file was given, please check the given filename.";
		CV_Error(CV_StsBadArg, error_message);
	}
	string line, path, classlabel;
	while (getline(file, line)) {
		stringstream liness(line);
		getline(liness, path, separator);
		getline(liness, classlabel);
		if (!path.empty() && !classlabel.empty()) {
			images.push_back(imread(path, 0));
			labels.push_back(atoi(classlabel.c_str()));
		}
	}
	file.close();
}

void trainRecognizer() {
	//faceRecognizer = createEigenFaceRecognizer(80, 4600); // possible alternative
	faceRecognizer = createLBPHFaceRecognizer(1, 8, 8, 8, 100); // worked best so far
	faceRecognizer->train(images, labels);
}

void startOpenCVWindow() {
	recognizedUserID = -1;
	recognitionConfidence = 20000;
	stopOpenCVLoop = false;
	windowThread = new thread(displayOpenCVWindow);
}

void closeOpenCVWindow() {
	stopOpenCVLoop = true;
	windowThread->join();
}

void storeCurrentFace(long userID) {
	ostringstream buf;
	
	buf << "./resources/images/usr_" << userID;
	CreateDirectory(buf.str().c_str(), NULL);

	buf <<"/" << time(0) << ".jpg";
	imwrite(buf.str(), currentFace);
	cout << _CPP_DEBUG_PREFIX << "stored " << buf.str() << endl;

	append_csv(IMAGE_INDEX_FILE_PATH, buf.str(), userID);

	images.push_back(currentFace);
	labels.push_back(userID);
	trainRecognizer();
}

void displayOpenCVWindow() {
	cout << _CPP_DEBUG_PREFIX << "creating openCV window" << endl;
	
	CascadeClassifier haar_cascade;
	string fn_haar = "./resources/haarcascade_frontalface_alt_tree.xml";
	haar_cascade.load(fn_haar);
	
	try {
		read_csv(IMAGE_INDEX_FILE_PATH, images, labels);
	}
	catch (Exception& e) {
		cerr << "Error opening file \"" << IMAGE_INDEX_FILE_PATH << "\". Reason: " << e.msg << endl;
		// nothing more we can do
		exit(1);
	}
	
	trainRecognizer();
	
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
		
		numberOfDetectedFaces = (long) faces.size();
		Rect largestFace = *(new Rect(0, 0, 0, 0));

		for (int i = 0; i < faces.size(); i++) {
			Rect face_i = faces[i];
			
			// highlight face
			rectangle(original, face_i, CV_RGB(0, 255, 0), 1);

			if (face_i.area() > largestFace.area())
				largestFace = face_i;
		}
		
		if (largestFace.area() > 0) {
			Mat face = gray(largestFace);
			Mat face_resized;
			resize(face, face_resized, Size(100, 100), 1.0, 1.0, INTER_CUBIC);
						
			int prediction;
			double confidence;
			faceRecognizer->predict(face_resized, prediction, confidence);
			
			currentFace = face_resized;
			recognizedUserID = prediction;
			recognitionConfidence = (long) confidence;

			string box_text = format("Prediction = %d, Confidence = %f", prediction, confidence);
			int pos_x = max(largestFace.tl().x - 10, 0);
			int pos_y = max(largestFace.tl().y - 10, 0);
			putText(original, box_text, Point(pos_x, pos_y), FONT_HERSHEY_PLAIN, 1.0, CV_RGB(0, 255, 0), 2);
		}
		else {
			recognizedUserID = -1;
			recognitionConfidence = 20000;
			currentFace = NULL;
		}

		imshow("OpenCV Face Detection", original);
		
		// exit on Esc
		char key = (char)waitKey(20);
		//if (key == 27)
		//	break;
	}

	return;
}