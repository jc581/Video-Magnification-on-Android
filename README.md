**opencv-videocapture** provides a sample Android app that uses [OpenCV](http://opencv.org/platforms/android/) to process a video file.

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
