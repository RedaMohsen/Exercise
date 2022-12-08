package com.toters.exercise.helper;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import com.bluelinelabs.conductor.Controller;


public class PermissionsHelper {
    private Controller controller;

    public PermissionsHelper(Controller controller) {
        this.controller = controller;
    }

    public Boolean hasCameraPermission() {
        return hasPermission(Manifest.permission.CAMERA);
    }

    public Boolean hasWritePermission() {
        return hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void requestCameraPermission(int requestCode) {
        requestPermission(requestCode, Manifest.permission.CAMERA);
    }

    public void requestWritePermission(int requestCode) {
        requestPermission(requestCode, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private Boolean hasPermission(String permission) {
        if (controller.getActivity() != null) {
            return ContextCompat.checkSelfPermission(controller.getActivity(), permission) == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    private void requestPermission(int requestCode, String permission) {
        controller.requestPermissions(new String[]{permission}, requestCode);
    }

    public static Boolean isGranted(int[] grantResults) {
        return grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

    public boolean hasAudioPermission() {
        return hasPermission(Manifest.permission.RECORD_AUDIO);
    }

    public void requestAudioPermission(int requestCode) {
        requestPermission(requestCode, Manifest.permission.RECORD_AUDIO);
    }


    public boolean hasLocationPermission() {
        return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION) && hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    public void requestLocationPermission(int requestCode) {
        requestPermission(requestCode, Manifest.permission.ACCESS_FINE_LOCATION);
    }
}
