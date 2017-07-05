**opencv-videocapture** provides a sample Android app that uses [OpenCV](http://opencv.org/platforms/android/) to process a video file.

## Usage

**opencv-videocapture** is an extremely bare bones, do nothing app. It actually 
does nothing other than open a video file with a [`VideoCapture`](http://docs.opencv.org/java/2.4.2/index.html?org/opencv/highgui/VideoCapture.html).
The video file, located in the assets folder, must be an AVI file using the MJPEG 
codec. Theoretically, other formats may be supported by using FFmpeg, which can
be obtained through [JavaCV](https://github.com/bytedeco/javacv).

## Dependencies

This app is built using Gradle and support Android 5.0 (Lollipop, API 21) and higher.

**opencv-videocapture** requires OpenCV for Android 3.0.0. To install OpenCV for Android, download and unpack the correct version from [https://sourceforge.net/projects/opencvlibrary/files/opencv-android/](https://sourceforge.net/projects/opencvlibrary/files/opencv-android/).

## Building

The latest version of this app can be cloned using:

```git@gitlab.oit.duke.edu:mrevoir/opencv-videocapture.git```

After cloning the repo, you must make the following changes.

* In `app/build.gradle`:
	* Change `jniLibs.srcDirs` on line 22 to reflect the path to where you installed OpenCV.
	
* In `app/CMakeLists.txt`:
	* Change `include_directories` on line 9, and `set_target_properties` on line 11 to reflect the path to where you installed OpenCV.
