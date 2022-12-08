package com.toters.exercise.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;

import com.toters.exercise.R;


public class DialogUtils {

    public static AlertDialog createLoadingDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog loadingDialog = builder.setCancelable(false).create();
        Window window = loadingDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(Gravity.CENTER);
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        }

        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setView(activity.getLayoutInflater().inflate(R.layout.layout_loading, null));
        loadingDialog.setCanceledOnTouchOutside(false);
        return loadingDialog;
    }


    public static AlertDialog createLogoutDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog loadingDialog = builder.setCancelable(false).create();
        Window window = loadingDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(Gravity.CENTER);
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }

        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setCanceledOnTouchOutside(false);
        return loadingDialog;
    }

    public static AlertDialog createInfoDialog(Activity activity, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog loadingDialog = builder.setCancelable(false).create();
        loadingDialog.setMessage(message);
        Window window = loadingDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(Gravity.CENTER);
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }

        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setButton(Dialog.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadingDialog.dismiss();
            }
        });
        loadingDialog.setCanceledOnTouchOutside(false);
        return loadingDialog;
    }

//    public static AlertDialog Picker(Activity activity) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        AlertDialog loadingDialog = builder.setCancelable(false).create();
//        Window window = loadingDialog.getWindow();
//        if (window != null) {
//            window.setBackgroundDrawableResource(android.R.color.transparent);
//            window.setGravity(Gravity.CENTER);
//            window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        }
//
//        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        loadingDialog.setView(activity.getLayoutInflater().inflate(R.layout.controller_dialog_photo_picker, null));
//        loadingDialog.setCanceledOnTouchOutside(false);
//        return loadingDialog;
//    }




//
//    public static AlertDialog createDownloadDialog(Activity activity) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        AlertDialog loadingDialog = builder.setCancelable(false).create();
//        Window window = loadingDialog.getWindow();
//        if (window != null) {
//            window.setBackgroundDrawableResource(android.R.color.transparent);
//            window.setGravity(Gravity.CENTER);
//            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        }
//
//        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        loadingDialog.setView(activity.getLayoutInflater().inflate(R.layout.layout_download_loading, null));
//        loadingDialog.setCanceledOnTouchOutside(false);
//        return loadingDialog;
//    }
}
