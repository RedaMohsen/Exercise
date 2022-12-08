package com.toters.exercise.base.dialogController;


import static com.toters.exercise.constants.BundleConstants.BUNDLE_DIALOG_IS_CANCELLABLE;
import static com.toters.exercise.constants.BundleConstants.BUNDLE_DIALOG_ITEMS;
import static com.toters.exercise.constants.BundleConstants.BUNDLE_DIALOG_TITLE;

import android.app.Dialog;
import android.os.Bundle;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.toters.exercise.R;

public class SimpleOptionsDialogController extends DialogController {

    private DialogClickListener listener;

    public interface DialogClickListener {
        //        void onDialogOptionClicked(int position);
        void onDialogOptionClicked(SimpleOptionsDialogController controller, int position);
    }

    public SimpleOptionsDialogController() {
    }

    public SimpleOptionsDialogController(Bundle args, DialogClickListener listener) {
        super(args);
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog() {
        assert getActivity() != null;

        String[] items = getArgs().getStringArray(BUNDLE_DIALOG_ITEMS);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity(), R.style.DialogTheme)
                .setTitle(getArgs().getString(BUNDLE_DIALOG_TITLE))
                .setItems(items, (dialogInterface, i) -> listener.onDialogOptionClicked(this, i));
        builder.setCancelable(getArgs().getBoolean(BUNDLE_DIALOG_IS_CANCELLABLE, true));
        return builder.create();
//        assert getActivity() != null;
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle(getArgs().getString(BUNDLE_DIALOG_TITLE));
//        builder.setItems(getArgs().getStringArray(BUNDLE_DIALOG_ITEMS), (dialogInterface, i) -> listener.onDialogOptionClicked(i));
//        return builder.create();
    }


//    private DialogClickListener listener;
//
//    public interface DialogClickListener {
//        void onDialogOptionClicked(int position);
//    }
//
//    public SimpleOptionsDialogController() {
//    }
//
//    public SimpleOptionsDialogController(Bundle args, DialogClickListener listener) {
//        super(args);
//        this.listener = listener;
//    }
//
//    @Override
//    public Dialog onCreateDialog() {
//        assert getActivity() != null;
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom);
//        builder.setTitle(getArgs().getString(BundleConstants.BUNDLE_DIALOG_TITLE));
//        builder.setItems(getArgs().getStringArray(BundleConstants.BUNDLE_DIALOG_ITEMS), (dialogInterface, i) -> listener.onDialogOptionClicked(i));
//        return builder.create();
//    }
}
