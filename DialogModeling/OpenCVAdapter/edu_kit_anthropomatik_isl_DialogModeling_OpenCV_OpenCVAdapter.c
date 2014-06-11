#include <jni.h>
#include <stdio.h>
#include "edu_kit_anthropomatik_isl_DialogModeling_OpenCV_OpenCVAdapter.h"
#include "adapter_implementation.hpp"

JNIEXPORT void JNICALL Java_edu_kit_anthropomatik_isl_DialogModeling_OpenCV_OpenCVAdapter_runOpenCVWindow(JNIEnv *env, jobject thisObj) {
	printf("%sC processing starts...\n", _CPP_DEBUG_PREFIX);
	startOpenCVWindow();
	return;
}

JNIEXPORT void JNICALL Java_edu_kit_anthropomatik_isl_DialogModeling_OpenCV_OpenCVAdapter_stopOpenCVWindow(JNIEnv *env, jobject thisObj) {
	printf("%sclosing OpenCV window...\n", _CPP_DEBUG_PREFIX);
	closeOpenCVWindow();
	return;
}

JNIEXPORT jint JNICALL Java_edu_kit_anthropomatik_isl_DialogModeling_OpenCV_OpenCVAdapter_getNumberOfDetectedFaces(JNIEnv *env, jobject thisObj) {
	return numberOfDetectedFaces;
}
