package com.toters.exercise.base.dialogController;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.toters.exercise.constants.BundleConstants;


public class SimpleDialogController extends DialogController {
    public static final String TAG_SIMPLE_DIALOG = "simple Tag";
    private DialogClickListener listener;

    public interface DialogClickListener {
        void onPositiveDialogButtonClicked(int dialogId);

        void onDialogDismissed(int dialogId);
    }

    public SimpleDialogController() {
    }

    public SimpleDialogController(Bundle args, DialogClickListener listener) {
        super(args);
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog() {
        assert getActivity() != null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getArgs().getString(BundleConstants.BUNDLE_DIALOG_TITLE));
        builder.setMessage(getArgs().getString(BundleConstants.BUNDLE_DIALOG_BODY));
        builder.setPositiveButton(getArgs().getString(BundleConstants.BUNDLE_DIALOG_POSITIVE_BUTTON), (dialogInterface, i) -> listener.onPositiveDialogButtonClicked(getArgs().getInt(BundleConstants.BUNDLE_DIALOG_ID)));
        builder.setNegativeButton(getArgs().getString(BundleConstants.BUNDLE_DIALOG_NEGATIVE_BUTTON), (dialogInterface, i) -> listener.onDialogDismissed(getArgs().getInt(BundleConstants.BUNDLE_DIALOG_ID)));
        return builder.create();
    }



}
