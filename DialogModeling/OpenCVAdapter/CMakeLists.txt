PROJECT(
#INSERT: Projektname bzw. executable name
OpenCVAdapter
)

CMAKE_MINIMUM_REQUIRED(VERSION 2.8.0)
CMAKE_POLICY(VERSION 2.6)

set(SOURCES
#INSERT: Alle .cpp Dateien
edu_kit_anthropomatik_isl_DialogModeling_OpenCV_OpenCVAdapter.c
adapter_implementation.cpp
)

set(HEADERS
#INSERT: Alle .h Dateien
edu_kit_anthropomatik_isl_DialogModeling_OpenCV_OpenCVAdapter.h
adapter_implementation.hpp
)

source_group(main FILES ${SOURCES} ${HEADERS})

add_library(${PROJECT_NAME} SHARED
#add_executable(${PROJECT_NAME}
    ${SOURCES}
	${HEADERS}
)

find_package(OpenCV REQUIRED)
target_link_libraries(${PROJECT_NAME} ${OpenCV_LIBS})

find_package(JNI REQUIRED)
#target_link_libraries(${PROJECT_NAME} ${JNI_LIBRARIES})
include_directories(${JNI_INCLUDE_DIRS})