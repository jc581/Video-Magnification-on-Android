package org.dihi.proto.opencv.processvideo;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.io.File;

import timber.log.Timber;

/**
 * Created by mikerevoir on 7/3/17.
 */

public class VideoProcessor {
    private static final VideoProcessor ourInstance = new VideoProcessor();

    public static VideoProcessor getInstance() {
        return ourInstance;
    }

    private VideoProcessor() {
    }

    public void process(String sourceFilename) {
        File file = new File(sourceFilename);
        if (file.exists()) {
            Timber.d("%s exists", sourceFilename);
            VideoCapture videoCapture = new VideoCapture(sourceFilename);
            if (!videoCapture.isOpened()) {
                Timber.wtf("failed to open VideoCapture from %s", sourceFilename);
            } else {
                Mat mat = new Mat();
                int i = 0;
                while (videoCapture.read(mat)) {
                    i++;
                }
                Timber.d("video has %d frames", i);
            }
        } else {
            Timber.e("%s does NOT exist", sourceFilename);
        }
    }
}
