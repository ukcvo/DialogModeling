#include <jni.h>
#include <stdio.h>
#include "edu_kit_anthropomatik_isl_DialogModeling_OpenCV_OpenCVAdapter.h"
#include "adapter_implementation.hpp"

JNIEXPORT void JNICALL Java_edu_kit_anthropomatik_isl_DialogModeling_OpenCV_OpenCVAdapter_runOpenCVWindow(JNIEnv *env, jobject thisObj) {
	printf("%sC processing starts...\n", _CPP_DEBUG_PREFIX);
	startOpenCVWindow();
	return;
}