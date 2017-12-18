package com.github.evan.common_utils.utils;

import android.hardware.Camera;

/**
 * Created by Evan on 2017/12/17.
 */

public class CameraUtil {

    public static int getNumberOfCamera() {
        return Camera.getNumberOfCameras();
    }

    public static int getBackgroundCameraId() {
        int numberOfCameras = Camera.getNumberOfCameras();
        if (numberOfCameras >= 2) {
            for (int i = 0; i < numberOfCameras; i++) {
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(i, cameraInfo);
                int facing = cameraInfo.facing;
                if(facing == Camera.CameraInfo.CAMERA_FACING_BACK){
                    return i;
                }
            }
        }

        return -1;
    }

    public static int getFrontCameraId() {
        int numberOfCameras = Camera.getNumberOfCameras();
        if (numberOfCameras >= 2) {
            for (int i = 0; i < numberOfCameras; i++) {
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                Camera.getCameraInfo(i, cameraInfo);
                int facing = cameraInfo.facing;
                if(facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                    return i;
                }
            }
        }

        return -1;
    }

    public static Camera openCamera(int id) throws RuntimeException{
        return Camera.open(id);
    }
}
