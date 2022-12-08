package com.toters.exercise.base.dialogController;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.toters.exercise.R;


public class InternetDialogController extends DialogController {
    public InternetDialogListener internetDialogListener;

    public interface InternetDialogListener {
        void onConfigClicked();

        void onRetryClicked();
    }

    public InternetDialogController(InternetDialogListener internetDialogListener) {
        super();
        this.internetDialogListener = internetDialogListener;
    }

    public InternetDialogController(Bundle bundle) {
        super(bundle);
    }

    @Override
    public Dialog onCreateDialog() {
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.controller_dialog_no_internet, null, false);
        initView(dialogView);
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setView(dialogView).create();
        setCancelable(false);
        dialog.requestWindowFeature(STYLE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return dialog;
    }

    private void initView(View dialogView) {
        dialogView.findViewById(R.id.checkInternetButton).setOnClickListener(view -> {
            internetDialogListener.onConfigClicked();
        });
        dialogView.findViewById(R.id.repeatButton).setOnClickListener(view -> {
            internetDialogListener.onRetryClicked();
            this.dismiss();
        });
    }

}
