package org.dihi.proto.opencv.processvideo;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by mikerevoir on 7/3/17.
 */

public class VideoProcessor {
    private static final VideoProcessor ourInstance = new VideoProcessor();
    private static final int LEVELS = 3;
    private static final int AMPLIFIED_FACTOR = 100;

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
                // read all frames into list from the video file
                List<Mat> frames = new ArrayList<>();
                Mat mat = new Mat();
                while (videoCapture.read(mat)) {
                    frames.add(mat);
                    mat = new Mat();
                }

                // TODO: 7/21/17  need to split each frame into RGB channels ??

                // prepare the data structure to hold all levels of Laplacian, for later temporal filtering
                // including the smallest image of Guassian pyramid, for later reconstruction
                List<List<Mat>> allLevels = new ArrayList<>(LEVELS);
                for (int i = 0; i < allLevels.size(); i++) {
                    List<Mat> level = new ArrayList<>();
                    allLevels.add(level);
                }

                // for each frame, generate its Guassian/Laplacian pyramid
                // and fill all the levels at this time
                for (Mat frame : frames) {

                    Mat temp1 = frame.clone();
                    Mat temp2 = new Mat();
                    Mat temp3 = new Mat();
                    Mat lap = new Mat();

                    // TODO: 7/24/17 need to store new Mat()
                    for (int i = 0; i < LEVELS - 1; i++) {
                        Imgproc.pyrDown(temp1, temp2);
                        Imgproc.pyrUp(temp2, temp3, temp1.size());
                        Core.subtract(temp1, temp3, lap);
                        allLevels.get(i).add(lap);

                        if (i == LEVELS - 2) {
                            allLevels.get(i + 1).add(temp2);
                        }

                        temp1 = temp2;
                    }
                }

                //
                /**********************************************************************************/
                int numberOfFrames = frames.size();

                // for each level through the time
                for (int i = 0; i < allLevels.size() - 1; i++) {

                    // for each pixel
                    // one pixel through the time is a signal
                    // this array will be reused for each pixel
                    double[][] signal = new double[3][numberOfFrames];

                    // get the size of picture on this level
                    Size size = allLevels.get(i).get(0).size();

                    // pixel coordinate
                    for (int m = 0; m < size.height; m++) {
                        for (int n = 0; n < size.width; n++) {
                            // from each frame
                            for (int j = 0; j < numberOfFrames; j++) {
                                Mat currFrame = allLevels.get(i).get(j);
                                double[] rgb = currFrame.get(m,n);
                                // put this pixel's rgb intensity into signal array
                                for (int frameIndex = 0; frameIndex < numberOfFrames; frameIndex++) {
                                    signal[0][frameIndex] = rgb[0]; // blue
                                    signal[1][frameIndex] = rgb[1]; // green
                                    signal[2][frameIndex] = rgb[2]; // red
                                }

                                // Fourier Transformation(DFT) of this signal into frequency domain

                                // band-pass filtering the signal

                                // inverse DFT the filtered signal to time domain

                                // amplify the filtered signal in time domain

                                // adding back to the original signal

                            }
                        }
                    }

                    // reconstruct the each frame based on its Laplacian pyramid and last-level Gaussian image

                    // TODO: 7/25/17 construct and output a new video file (amplified version) ??
                }


            }
        } else {
            Timber.e("%s does NOT exist", sourceFilename);
        }
    }





}
















