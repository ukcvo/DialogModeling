#ifndef _ADDAPTER_IMPLEMENTATION_H
#define _ADDAPTER_IMPLEMENTATION_H

#ifndef _CPP_DEBUG_PREFIX
#define _CPP_DEBUG_PREFIX "> C++: "
#endif

#ifdef __cplusplus
extern "C" {
#endif
	long numberOfDetectedFaces;

	void startOpenCVWindow();

	void closeOpenCVWindow();

	void displayOpenCVWindow();

	void storeCurrentFace();
#ifdef __cplusplus
}
#endif

#endif